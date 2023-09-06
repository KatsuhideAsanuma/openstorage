name := """openstorage"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.26",
  "org.redisson" % "redisson" % "3.16.4"
)
// build.sbtにAkkaの依存関係を追加
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.x",
  "com.typesafe.akka" %% "akka-stream" % "2.6.x"
)
