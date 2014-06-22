package io.zman.ghreadme

import sbt._
import Keys._
import com.typesafe.sbt.SbtSite.SiteKeys.siteMappings

/**
 * Implementation of the sbt-ghreadme plugin.
 */
object SbtGhReadme extends Plugin {

  /** The keys exposed by this plugin. */
  object GhReadmeKeys extends GhReadmeDsl {
    import ghreadme._

    /** The collection of README file mappings that will be included in the generated site. */
    val readmeMappings = TaskKey[ReadmeMappings]("readme-mappings")

  }

  object ghreadme {
    import GhReadmeKeys._

    /** Settings that enable the sbt-ghreadme plugin. */
    val settings = Seq(
      readmeMappings := Seq.empty,
      siteMappings <++= (streams, readmeMappings) map generateSiteMappings)

    /** Generates the annotated files and collects them as site mappings. */
    private def generateSiteMappings(streams: TaskStreams, mappings: ReadmeMappings) = {
      val cache = streams.cacheDirectory / "ghreadme"
      mappings map {
        case (source, destination, settings) =>
          val generated = cache / destination
          generated.getParentFile.mkdirs()
          IO.write(generated, frontMatter(settings))
          IO.append(generated, IO.read(file(source)))
          generated -> destination
      }
    }

    /** Generates a string containing the specified YAML front-matter. */
    private def frontMatter(frontMatter: ReadmeSettings) =
      frontMatter map { case (key, value) => s"$key: $value" } mkString ("---\n", "\n", "\n---\n")

  }

}