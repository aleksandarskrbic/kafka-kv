package kafka.kv.admin

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec
import io.github.embeddedkafka.{EmbeddedK, EmbeddedKafka, EmbeddedKafkaConfig}

trait KafkaSpec
    extends AsyncFlatSpec
    with BeforeAndAfterAll
    with EmbeddedKafka {

  val kafkaPort = 9092

  implicit val config: EmbeddedKafkaConfig =
    EmbeddedKafkaConfig(
      kafkaPort = kafkaPort,
      zooKeeperPort = 5555
    )

  var kafkaAdmin: KafkaAdmin = _
  var embeddedKafkaServer: EmbeddedK = _

  override def beforeAll(): Unit = {
    embeddedKafkaServer = EmbeddedKafka.start()

    while (!EmbeddedKafka.isRunning) {}
    kafkaAdmin = KafkaAdmin.make(s"localhost:$kafkaPort")
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    embeddedKafkaServer.stop(true)
    super.afterAll()
  }
}
