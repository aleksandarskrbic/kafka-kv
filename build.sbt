import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val `embedded-kafka` = (project in file("modules/embedded-kafka"))
  .settings(
    name := "embedded-kafka",
    libraryDependencies += "io.github.embeddedkafka" %% "embedded-kafka" % "2.8.1"
  )

lazy val `kafka-admin` = (project in file("modules/kafka-admin"))
  .settings(
    name := "kafka-admin",
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.8.1",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.11",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test,
    libraryDependencies += "io.github.embeddedkafka" %% "embedded-kafka" % "2.8.1" % Test
  )

lazy val root = (project in file("."))
  .settings(
    name := "kafka-kv"
  )
