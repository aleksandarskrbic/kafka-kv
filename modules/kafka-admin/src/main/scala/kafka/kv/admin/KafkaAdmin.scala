package kafka.kv.admin

import kafka.kv.admin.internal.Blocker

import java.util.Properties
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import java.util.{Collections => Java}
import kafka.kv.admin.internal.FutureOps._
import kafka.kv.admin.model.{ClusterDetails, CreateTopic, TopicDetails}
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig}

import java.util.concurrent.atomic.AtomicLong
import scala.jdk.CollectionConverters._
import java.util.concurrent.{Executors, ThreadFactory}

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
      topics.map(listing => TopicDetails(listing.name(), listing.isInternal))
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
}

object KafkaAdmin {
  def make(
            servers: String,
            ec: ExecutionContext = Blocker.default()
          ): KafkaAdmin = {

    val config = new Properties()
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers)

    val admin: AdminClient = AdminClient.create(config)

    new KafkaAdmin(admin)(ec)
  }
}
