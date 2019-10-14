name := "webApp"
 
version := "1.0" 
      
lazy val `webapp` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

val elastic4sVersion = "5.6.10"
val circeVersion = "0.11.1"
libraryDependencies ++= Seq(
  "dev.zio"   %% "zio"  % "1.0.0-RC9-4",
  "dev.zio"   %% "zio-interop-cats"  % "1.3.1.0-RC3",
  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" %elastic4sVersion, // elasticsearchからの返り値をJsonにパースするために必要。circeだけで良さそうやけど、ESからの戻りだけは、jacksonを使う
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9",
  "io.circe"                     %% "circe-core"              % circeVersion,
  "io.circe"                     %% "circe-generic"           % circeVersion,
  "io.circe"                     %% "circe-parser"            % circeVersion,
  "io.circe"                     %% "circe-java8"             % circeVersion,
  "com.dripower"                 %% "play-circe"              % "2711.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.0" % "test"
)

libraryDependencies += "org.mockito" % "mockito-core" % "2.28.2" % "test" // mockでテストをするため
