val appName = "searchApp"

scalaVersion := "2.12.7"
version := "1.0-SNAPSHOT"

val elastic4sVersion = "6.5.1"

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.3",
  "dev.zio"   %% "zio"  % "1.0.0-RC9-4",
  "dev.zio"   %% "zio-interop-cats"  % "1.3.1.0-RC3",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion
)


