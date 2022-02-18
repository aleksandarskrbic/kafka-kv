package kafka.kv.admin

import org.scalatest.matchers.should.Matchers._

class KafkaAdminSpec extends KafkaSpec {

  it should "create kafka topic" in {
    val testTopic = "test.topic"

    for {
      _ <- kafkaAdmin.createCompactedTopic(testTopic)
      topics <- listTopics()
    } yield topics should contain(testTopic)
  }

  it should "delete kafka topic" in {
    val topicToDelete = "topic.to.delete"

    for {
      _ <- createTopic(topicToDelete)
      _ <- kafkaAdmin.deleteTopic(topicToDelete)
      topics <- listTopics()
    } yield topics should not contain topicToDelete
  }
}
