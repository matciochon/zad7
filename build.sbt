name := "zad9"
organization := "MC"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.13"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test

// Dodanie zależności Scalafmt
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

// Automatyczne formatowanie kodu przed każdym kompilowaniem
scalafmtOnCompile := true
