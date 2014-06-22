package io.zman.ghreadme

import scala.language.implicitConversions

/**
 * Interface that provides a DSL for describing README file mapping and configuration.
 */
trait GhReadmeDsl {
  import GhReadmeDsl._

  /** Converts a raw string into a readme mapping. */
  implicit def stringToReadmeMapping(path: String): ReadmeMapping =
    stringsToReadmeMapping(path -> generateDestination(path))

  /** Converts a raw string into a rich readme mapping. */
  implicit def stringToRichReadmeMapping(path: String): RichReadmeMapping =
    new RichReadmeMapping(stringToReadmeMapping(path))

  /** Converts a pair of raw strings into a readme mapping. */
  implicit def stringsToReadmeMapping(paths: (String, String)): ReadmeMapping =
    ReadmeMapping(normalizeSource(paths._1), normalizeDestination(paths._2))

  /** Converts a pair of raw strings into a rich readme mapping. */
  implicit def stringsToRichReadmeMapping(paths: (String, String)): RichReadmeMapping =
    new RichReadmeMapping(stringsToReadmeMapping(paths))

  /** Converts a readme mapping into a rich readme mapping. */
  implicit def readmeMappingToRichReadmeMapping(self: ReadmeMapping): RichReadmeMapping =
    new RichReadmeMapping(self)

}

/**
 * Types that drive the README DSL.
 */
object GhReadmeDsl {

  /** The common name of readme files. */
  val README_md = "README.md"

  /** The common name of index files. */
  val index_md = "index.md"

  /** The extension for markdown files. */
  val mdExtension = ".md"

  /** Normalizes a source path, appending "README.md" if needed. */
  def normalizeSource(source: String): String = source match {
    case source if source endsWith mdExtension => source
    case source if source endsWith "/" => source + README_md
    case source => source + "/" + README_md
  }

  /** Normalizes a source path, appending "index.md" if needed. */
  def normalizeDestination(destination: String): String = destination match {
    case destination if destination endsWith mdExtension => destination
    case destination if destination endsWith "/" => destination + index_md
    case destination => destination + "/" + index_md
  }

  /** Generates a destination path from a source path. */
  def generateDestination(source: String): String = source match {
    case README_md => index_md
    case source if source endsWith README_md => source.substring(0, source.length - README_md.length) + index_md
    case source => source
  }

  /**
   * A wrapper around readme mappings that allow for the addition of front-matter.
   */
  final class RichReadmeMapping(val self: ReadmeMapping) extends AnyVal {

    /** Adds a new collection of settings to the underlying mapping. */
    def ---(settings: ReadmeSettings): ReadmeMapping = self.copy(_3 = self._3 ++ settings)
    
  }

}