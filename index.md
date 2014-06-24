---
title: sbt-ghreadme
tagline: github readmes for sites
layout: page
cover: cover.jpg
---
The [sbt-ghreadme](http://zman.io/sbt-ghreadme/) project is a SBT plugin that extends [sbt-site](https://github.com/sbt/sbt-site) by converting github-style README files into Jekyll documents to be included in the site.

[Change Log](changelog/)

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

