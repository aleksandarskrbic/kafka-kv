package kafka.kv.embedded

import org.slf4j.LoggerFactory
import io.github.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}

object EmbeddedKafkaServer extends App with EmbeddedKafka {
  val log = LoggerFactory.getLogger(this.getClass)

  val port = 9092

  val embeddedKafkaServer = EmbeddedKafka.start()(
    EmbeddedKafkaConfig(kafkaPort = port, zooKeeperPort = 5555)
  )

  createCustomTopic(topic = "transactions.raw", partitions = 3)
  createCustomTopic(topic = "transactions.enriched", partitions = 3)
  log.info(s"Kafka running: localhost:$port")

  embeddedKafkaServer.broker.awaitShutdown()
}
