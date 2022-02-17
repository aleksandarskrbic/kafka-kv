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
    libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"
  )

lazy val root = (project in file("."))
  .settings(
    name := "kafka-kv"
  )
