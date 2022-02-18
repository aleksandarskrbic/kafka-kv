package kafka.kv.admin

import scala.concurrent.Promise
import scala.jdk.CollectionConverters._
import org.scalatest.matchers.should.Matchers._
import KafkaFutureOps._

class KafkaAdminSpec extends KafkaSpec {

  it should "create kafka topic" in {
    val testTopic = "test.topic"

    kafkaAdmin
      .createCompactedTopic(testTopic)
      .flatMap { _ =>
        withAdminClient { adminClient =>
          val kafkaFuture = adminClient.listTopics().names()
          kafkaFuture.toFuture
        }.get
      }
      .map(topics => topics should contain(testTopic))
  }

  it should "delete kafka topic" in {
    val topicToDelete = "topic.to.delete"

    createCustomTopic(topicToDelete)

    kafkaAdmin
      .deleteTopic(topicToDelete)
      .flatMap { _ =>
        withAdminClient { adminClient =>
          val kafkaFuture = adminClient.listTopics().names()

          kafkaFuture.toFuture
        }.get
      }
      .map(topics => topics should not contain topicToDelete)
  }

}
