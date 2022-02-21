import sbt.Keys.libraryDependencies
import Dependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

lazy val `embedded-kafka` = (project in file("modules/embedded-kafka"))
  .settings(
    name := "embedded-kafka",
    libraryDependencies ++= Libraries.embeddedKafka
  )

lazy val kafkaKvProtocol = (project in file("modules/kafka-kv-protocol"))
  .settings(
    name := "kafka-kv-protocol",
    libraryDependencies ++=
      Libraries.circe ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test)
  )

lazy val kafkaAdmin = (project in file("modules/kafka-admin"))
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

lazy val kafkaKvClientHttp = (project in file("modules/kafka-kv-client-http"))
  .settings(
    name := "kafka-kv-client-http",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.akkaActors ++
        Libraries.tapirCirce ++
        Libraries.tapirAkkaHttp ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )

lazy val kafkaKvClientSdk = (project in file("modules/kafka-kv-client-sdk"))
  .settings(
    name := "kafka-kv-client-sdk",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )

lazy val kafkaKvServer = (project in file("modules/kafka-kv-server"))
  .settings(
    name := "kafka-kv-server",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.akkaActors ++
        Libraries.tapirCirce ++
        Libraries.tapirAkkaHttp ++
        Libraries.circe ++
        Libraries.akkaActors ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )
  .dependsOn(kafkaAdmin)

lazy val root = (project in file("."))
  .settings(name := "kafka-kv")
