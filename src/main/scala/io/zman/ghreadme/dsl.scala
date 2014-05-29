package io.zman.ghreadme

import scala.language.implicitConversions

/**
 * Interface that provides a DSL for describing README file mapping and configuration.
 */
trait GhReadmeDsl {
  import GhReadmeDsl._

  /** Converts a raw string into a readme mapping. */
  implicit def stringToReadmeMapping(path: String): ReadmeMapping = ReadmeMapping(path)

  /** Converts a raw string into a rich readme mapping. */
  implicit def stringToRichReadmeMapping(path: String): RichReadmeMapping = new RichReadmeMapping(path)

  /** Converts a readme mapping into a rich readme mapping. */
  implicit def readmeMappingToRichReadmeMapping(self: ReadmeMapping): RichReadmeMapping = new RichReadmeMapping(self)
  
  /** An alias for the root README file. */
  val README = "."

}

/**
 * Types that drive the README DSL.
 */
object GhReadmeDsl {

  /**
   * A wrapper around readme mappings that allow for the addition of front-matter.
   */
  final class RichReadmeMapping(val self: ReadmeMapping) extends AnyVal {
    
    /** Adds a pre-existing collection of settings to the underlying mapping. */
    def +++(settings: ReadmeSettings): ReadmeMapping = self.copy(_2 = self._2 ++ settings)
    
    /** Adds a new collection of settings to the underlying mapping. */
    def ---(settings: ReadmeSetting*): ReadmeMapping = +++(settings)
    
    /** Creates a list where the underlying mapping follows the supplied mapping. */
    def ::(that: ReadmeMapping): List[ReadmeMapping] = that :: self :: Nil
  }

}