import sbt._

object Dependencies {
  object Versions {
    lazy val kafka = "2.8.1"
    lazy val logback = "1.2.10"
    lazy val scalaLogging = "3.9.4"
    lazy val scalactic = "3.2.11"
    lazy val scalatest = "3.2.11"
    lazy val tapir = "0.20.0-M10"
    lazy val circe = "0.12.3"
    lazy val caffeine = "3.0.5"
  }

  object Libraries {
    lazy val embeddedKafka = Seq("io.github.embeddedkafka" %% "embedded-kafka" % Versions.kafka)
    lazy val kafkaClients = Seq("org.apache.kafka" % "kafka-clients" % Versions.kafka)
    lazy val logback = Seq("ch.qos.logback" % "logback-classic" % Versions.logback)
    lazy val scalaLogging = Seq("com.typesafe.scala-logging" %% "scala-logging"  % Versions.scalaLogging)
    lazy val scalactic = Seq("org.scalactic" %% "scalactic"  % Versions.scalactic)
    lazy val scalatest = Seq("org.scalatest" %% "scalatest" % Versions.scalatest)
    lazy val tapirAkkaHttp = Seq("com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % Versions.tapir)
    lazy val tapirCirce = Seq("com.softwaremill.sttp.tapir" %% "tapir-json-circe" % Versions.tapir)
    lazy val caffeine = Seq("com.github.ben-manes.caffeine" % "caffeine" % Versions.caffeine)
    lazy val circe = Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % Versions.circe)
  }
}
