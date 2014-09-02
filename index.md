---
title: sbt-ghreadme
headline: readme files in your sbt-site
layout: home
---
A [SBT](http://www.scala-sbt.org/) plugin that extends [sbt-site](https://github.com/sbt/sbt-site) by prepending YAML front matter to your github-style README files and including them in the generated site. This allows you to use the same documentation in multiple contexts without any manual duplication.

This plugin is aimed at projects that:

 - Use SBT and sbt-site to generate their project web site.

 - Use GitHub-style README files for documentation.

 - Want to use their GitHub-style READMEs as content on their site.

This plugin provides a minimal DSL that is used to describe what README files are to be included in your site as well as the YAML front-matter to associate with each file.

[source](https://github.com/zmanio/sbt-ghreadme) - [documentation](http://zman.io/sbt-ghreadme/api/#io.zman.ghreadme.package) - [changelog](changelog/)

[![Build Status](https://travis-ci.org/zmanio/rummage.png?branch=master)](https://travis-ci.org/zmanio/rummage)

## Getting Started

Prerequisites:

 - JVM 1.5+

 - Scala 2.10.4+

 - SBT 0.13.2+

To use sbt-ghreadme plugin in your project add the following line to your ```project/plugins.sbt``` file:

```scala
addSbtPlugin("io.zman" % "sbt-ghreadme" % "1.1")
```

## Configuring the Plugin

First off, add the following to the top of your ```build.sbt``` file:

```scala
import GhReadmeKeys._
```

Somewhere below that include the plugin settings (I usually put it after the [sbt-site](https://github.com/sbt/sbt-site) configuration).

```scala
ghreadme.settings
```

The last step is to specify what README files the plugin will process and associate YAML front-matter with those files. By default this plugin will not process any README files, you must specify each file to process individually.

### Configuring README Mappings

An example of the common use case where the ```README.md``` file in the root of your project is used as the index page of your site:

```scala
readmeMappings += {
  "." --- Seq(
    "title"   -> "my cool project",
    "layout"  -> "page"
  )
}
```

In the above example we specify the ```README.md``` file project's root directory simply by naming the directory (```"."```). Since only the source file is specified, the destination file will be named ```index.md``` and placed in the root of the generated site.

README files may be mapped to locations in the generated site that are different from their source locations. For example:

```scala
readmeMappings += {
  "." -> "project-info" --- Seq(
    "title"   -> "my cool project",
    "layout"  -> "page"
  )
}
```

In this example the ```README.md``` file in the root of the project will be mapped to ```project-info/index.md``` in the generated site.

### Mapping Arbitrary Files

This plugin can also be used to map arbitrarily-named markdown files from your project to your generated site. For example:

```scala
readmeMappings += {
  "docs/main.md" -> "site-docs/index.md" --- Seq(
    "title"   -> "my cool project",
    "layout"  -> "page"
  )
}
```

This example will map the ```docs/main.md``` file in your project to the ```site-docs/index.md``` file in your generated site. While this behavior is allowed, the next section describes why this usage is discouraged.

### Linking Between READMEs

