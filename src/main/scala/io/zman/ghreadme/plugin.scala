package io.zman.ghreadme

import scala.language.implicitConversions
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
        case (path, settings) =>
          val source = inputFilePath(path)
          val destination = outputFilePath(path)
          val output = cache / destination
          output.getParentFile.mkdirs()
          IO.write(output, frontMatter(settings))
          IO.append(output, IO.read(file(source)))
          output -> destination
      }
    }

    /** Returns the path for an input file. */
    private def inputFilePath(basePath: String) = basePath match {
      case README => "README.md"
      case directory => directory + "/README.md"
    }

    /** Returns the path for an output file. */
    private def outputFilePath(basePath: String) = basePath match {
      case README => "index.md"
      case directory => directory + "/index.md"
    }

    /** Generates a string containing the specified YAML front-matter. */
    private def frontMatter(frontMatter: ReadmeSettings) =
      frontMatter map { case (key, value) => s"$key: $value" } mkString ("---\n", "\n", "\n---\n")

  }

}