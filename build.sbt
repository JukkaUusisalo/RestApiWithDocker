name := """RestApiWithDocker"""
organization := "org.jukka"
version := "1.0-SNAPSHOT"

swaggerDomainNameSpaces := Seq("models")

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.13"

libraryDependencies += guice
libraryDependencies += "org.webjars" % "swagger-ui" % "3.43.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
