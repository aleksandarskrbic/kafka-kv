import sbt._

object Dependencies {
  object Versions {
    lazy val kafka = "2.8.1"
    lazy val logback = "1.2.10"
    lazy val scalaLogging = "3.9.4"
    lazy val scalactic = "3.2.11"
    lazy val scalatest = "3.2.11"
    lazy val tapir = "0.20.0-M10"
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
  }
}
