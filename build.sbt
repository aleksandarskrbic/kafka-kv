import Dependencies._
import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"

lazy val `embedded-kafka` = (project in file("modules/embedded-kafka"))
  .settings(name := "embedded-kafka", libraryDependencies ++= Libraries.embeddedKafka)

lazy val kafkaAdmin = (project in file("modules/kafka-admin"))
  .settings(
    name := "kafka-admin",
    libraryDependencies ++=
      Libraries.kafkaClients ++
      Libraries.logback ++
      Libraries.scalaLogging ++
      Libraries.scalactic ++
      Libraries.scalatest.map(_ % Test) ++
      Libraries.embeddedKafka.map(_ % Test))
  .dependsOn(kafkaKvCommon)

lazy val kafkaKvCommon = (project in file("modules/kafka-kv-common"))
  .settings(name := "kafka-kv-common", libraryDependencies ++= Libraries.kafkaClients)

lazy val kafkaKvProtocol = (project in file("modules/kafka-kv-protocol")).settings(
  name := "kafka-kv-protocol",
  libraryDependencies ++=
    Libraries.circe ++
    Libraries.scalactic ++
    Libraries.scalatest.map(_ % Test))

lazy val kafkaKvServer = (project in file("modules/kafka-kv-server"))
  .settings(
    name := "kafka-kv-server",
    libraryDependencies ++=
      Libraries.kafkaClients ++
      Libraries.logback ++
      Libraries.scalaLogging ++
      Libraries.akkaActors ++
      Libraries.akkaStreams ++
      Libraries.alpakkaKafka ++
      Libraries.tapirCirce ++
      Libraries.tapirAkkaHttp ++
      Libraries.circe ++
      Libraries.akkaActors ++
      Libraries.scalactic ++
      Libraries.scalatest.map(_ % Test) ++
      Libraries.embeddedKafka.map(_ % Test))
  .dependsOn(kafkaAdmin, kafkaKvProtocol, kafkaKvCommon)

lazy val root = (project in file(".")).settings(name := "kafka-kv")
