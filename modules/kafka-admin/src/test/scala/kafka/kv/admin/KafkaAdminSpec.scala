package kafka.kv.admin

import org.scalatest.matchers.should.Matchers._

class KafkaAdminSpec extends KafkaSpec {

  it should "create kafka topic" in {
    val testTopic = "test.topic"

    for {
      _ <- kafkaAdmin.createCompactedTopic(testTopic)
      topics <- kafkaAdmin.listTopics().map(_.map(_.name))
    } yield {
      topics should contain(testTopic)
    }
  }

  it should "create and delete kafka topic" in {
    val topicToDelete = "topic.to.delete"

    for {
      _ <- kafkaAdmin.createCompactedTopic(topicToDelete)
      topicsBeforeDeletion <- kafkaAdmin.listTopics().map(_.map(_.name))
      _ <- kafkaAdmin.deleteTopic(topicToDelete)
      topicsAfterDeletion <- kafkaAdmin.listTopics().map(_.map(_.name))
    } yield {
      topicsBeforeDeletion should contain(topicToDelete)
      topicsAfterDeletion should not contain topicToDelete
    }
  }

  it should "return cluster details" in {
    for {
      clusterDetails <- kafkaAdmin.describeCluster()
    } yield {
      clusterDetails.nodes.size should be(1)
    }
  }
}
