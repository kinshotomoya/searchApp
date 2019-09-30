scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"      % "0.8.2",
  "org.tpolecat" %% "doobie-hikari"    % "0.8.2",
  "net.debasishg" %% "redisclient" % "3.10",
  "com.typesafe" % "config" % "1.3.4",
  "mysql" % "mysql-connector-java" % "5.1.24",
  "com.typesafe.akka" %% "akka-actor" % "2.5.6"
)