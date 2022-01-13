name := """scala-api"""
organization := "com.freestar"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.7"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += ws
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.0"
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "x.x.x" % "test"
)
libraryDependencies += "de.leanovate.play-mockws" %% "play-mockws" % "2.8.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.freestar.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.freestar.binders._"
