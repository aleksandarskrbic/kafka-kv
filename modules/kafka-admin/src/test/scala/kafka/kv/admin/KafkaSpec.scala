package kafka.kv.admin

import io.github.embeddedkafka.{EmbeddedK, EmbeddedKafka, EmbeddedKafkaConfig}
import kafka.kv.admin.KafkaFutureOps._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec
import scala.jdk.CollectionConverters._
import scala.concurrent.Future

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

  def listTopics(): Future[Set[String]] =
    Future.fromTry {
      withAdminClient { adminClient =>
        val kafkaFuture = adminClient.listTopics().names()
        kafkaFuture.toFuture.map(_.asScala.toSet)
      }
    }.flatten

  def createTopic(topic: String): Future[Unit] =
    Future.successful(createCustomTopic(topic))

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
