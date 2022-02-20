import sbt.Keys.libraryDependencies
import Dependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"

lazy val `embedded-kafka` = (project in file("modules/embedded-kafka"))
  .settings(
    name := "embedded-kafka",
    libraryDependencies ++= Libraries.embeddedKafka
  )

lazy val `kafka-admin` = (project in file("modules/kafka-admin"))
  .settings(
    name := "kafka-admin",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )

lazy val `kafka-kv-client` = (project in file("modules/kafka-kv-client"))
  .settings(
    name := "kafka-kv-client",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )


lazy val `kafka-kv-server` = (project in file("modules/kafka-kv-server"))
  .settings(
    name := "kafka-kv-server",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )


lazy val root = (project in file("."))
  .settings(name := "kafka-kv")
