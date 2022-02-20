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

lazy val `kafka-kv-client-http` = (project in file("modules/kafka-kv-client-http"))
  .settings(
    name := "kafka-kv-client-http",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )

lazy val `kafka-kv-client-scala` = (project in file("modules/kafka-kv-client-sdk"))
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


lazy val `kafka-kv-server` = (project in file("modules/kafka-kv-server"))
  .settings(
    name := "kafka-kv-server",
    libraryDependencies ++=
      Libraries.kafkaClients ++
        Libraries.logback ++
        Libraries.scalaLogging ++
        Libraries.circe ++
        Libraries.scalactic ++
        Libraries.scalatest.map(_ % Test) ++
        Libraries.embeddedKafka.map(_ % Test)
  )


lazy val root = (project in file("."))
  .settings(name := "kafka-kv")
