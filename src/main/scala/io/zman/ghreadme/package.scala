package io.zman

/**
 * A SBT plugin that extends sbt-site by converting github-style
 * README files into Jekyll documents included in the site.
 */
package object ghreadme {

  /**
   * A single element of YAML front-matter.
   */
  type ReadmeSetting = (String, String)

  /**
   * Factory for readme settings.
   */
  object ReadmeSetting {

    /** Creates a readme setting. */
    def apply(key: String, value: String): ReadmeSetting = key -> value

  }

  /**
   * A collection of YAML front-matter.
   */
  type ReadmeSettings = Seq[ReadmeSetting]

  /**
   * Factory for collections of readme settings.
   */
  object ReadmeSettings {

    /** Creates a collection of readme settings. */
    def apply(settings: ReadmeSetting*): ReadmeSettings = settings

  }

  /**
   * A mapping for a README file including the YAML front-matter to embed.
   */
  type ReadmeMapping = (String, String, ReadmeSettings)

  /**
   * Factory for readme mappings.
   */
  object ReadmeMapping {

    /** Creates a readme mapping. */
    def apply(source: String, destination: String, settings: ReadmeSettings = ReadmeSettings()): ReadmeMapping =
      (source, destination, settings)

  }

  /**
   * A collection of readme file mappings.
   */
  type ReadmeMappings = Seq[ReadmeMapping]

  /**
   * Factory for collections of readme file mappings.
   */
  object ReadmeMappings {

    /** Creates a collection of readme file mappings. */
    def apply(mappings: ReadmeMapping*): ReadmeMappings = mappings

  }

}