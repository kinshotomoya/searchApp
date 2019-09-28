val appName = "searchApp"

scalaVersion := "2.12.7"
version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.3",
  "dev.zio"   %% "zio"  % "1.0.0-RC9-4",
  "dev.zio"   %% "zio-interop-cats"  % "1.3.1.0-RC3",
)


