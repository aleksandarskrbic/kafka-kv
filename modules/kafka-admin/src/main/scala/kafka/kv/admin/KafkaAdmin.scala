package kafka.kv.admin

import java.util.Properties
import java.util.{Collections => Java}
import kafka.kv.admin.internal.Blocker
import kafka.kv.admin.internal.FutureOps._

import scala.concurrent.{ExecutionContext, Future}
import kafka.kv.admin.model.{ClusterDetails, ConsumerGroupsDetails, CreateTopic, TopicDetails}
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig}

import scala.jdk.CollectionConverters._

class KafkaAdmin(admin: AdminClient)(implicit ec: ExecutionContext) {
  def createCompactedTopic(name: String): Future[Unit] = {
    val compactedTopic = CreateTopic(name).compacted
    val result = admin.createTopics(Java.singletonList(compactedTopic))
    val kafkaFuture = result.config(name)
    kafkaFuture.toFuture.unit
  }

  def deleteTopic(name: String): Future[Unit] = {
    val topic = Java.singletonList(name)
    val result = admin.deleteTopics(topic)
    val kafkaFuture = result.values().get(name)
    kafkaFuture.toFuture.unit
  }

  def listTopics(): Future[Set[TopicDetails]] = {
    val result = admin.listTopics()
    val kafkaFuture = result.listings()
    kafkaFuture.toFuture.map { topicListings =>
      val topics = topicListings.asScala.toSet
      topics.map(TopicDetails.fromTopicListing)
    }
  }

  def describeCluster(): Future[ClusterDetails] = {
    val result = admin.describeCluster()
    for {
      clusterId <- result.clusterId().toFuture
      controller <- result.controller().toFuture
      nodes <- result.nodes().toFuture
    } yield ClusterDetails(clusterId, controller, nodes.asScala.toList)
  }

  def describeConsumerGroups(consumerGroups: List[String]): Future[ConsumerGroupsDetails] = {
    val result = admin.describeConsumerGroups(consumerGroups.asJava)
    val kafkaFuture = result.all()
    kafkaFuture.toFuture.map(result => ConsumerGroupsDetails(result.asScala.toMap))
  }

  def close(): Unit =
    admin.close()
}

object KafkaAdmin {
  def make(servers: String): KafkaAdmin = {
    val config = new Properties()
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers)

    new KafkaAdmin(AdminClient.create(config))(Blocker.default())
  }
}
