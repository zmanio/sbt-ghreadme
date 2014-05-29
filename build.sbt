import SiteKeys._
import GhPagesKeys._
import bintray.{ Keys => bintrayKeys }
import bintrayKeys._

//
// Basic project information.
//

sbtPlugin := true

name := "sbt-ghreadme"

version := "1.0"

sbtVersion in Global := "0.13.2" 

scalaVersion in Global := "2.10.4" 

description := "A SBT plugin that extends sbt-site by converting github-style README files into Jekyll documents included in the site."

homepage := Some(url("http://zman.io/sbt-ghreadme/"))

startYear := Some(2014)

organization := "io.zman"

organizationName := "zman.io"

organizationHomepage := Some(url("http://zman.io/"))

scalacOptions := Seq("-deprecation", "-unchecked")

licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

resolvers += Resolver.url("scalasbt", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
     
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "0.7.2")

//
// Documentation site generation.
//

site.settings

includeFilter in makeSite := "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.md" | "*.yml"

site.includeScaladoc("api")

ghpages.settings

ghpagesNoJekyll := false

git.remoteRepo := "git@github.com:zmanio/sbt-ghreadme.git"

//
// Publishing to Bintray
//

publishMavenStyle := false

bintrayPublishSettings

bintrayKeys.repository in bintray := "sbt-plugins"

bintrayOrganization in bintray := None
