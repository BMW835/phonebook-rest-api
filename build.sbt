name := """phonebook-rest-api"""
organization := "com.company"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.10"

libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.company.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.company.binders._"

libraryDependencies ++= Seq(
  //"org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0-M2" % Test,
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.slick" %% "slick" % "2.1.0"//,
  //"org.slf4j" % "slf4j-nop" % "1.6.4"
)

libraryDependencies ++= Seq(
  jdbc,
  //cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" %
    Test,
  "com.netflix.rxjava" % "rxjava-scala" % "0.20.7"
)
