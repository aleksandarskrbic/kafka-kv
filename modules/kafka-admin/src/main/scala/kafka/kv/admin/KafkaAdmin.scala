package kafka.kv.admin

import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, Config}

import java.util.Properties
import scala.concurrent.ExecutionContext
import java.util.{Collections => Java}
import KafkaFutureOps._

class KafkaAdmin(admin: AdminClient)(implicit ec: ExecutionContext) {
  def createCompactedTopic(name: String) = {
    val topic = Topic(name).toJava
    val result = admin.createTopics(topic)

    val kafkaFuture = result.config(name)

    kafkaFuture.toFuture
  }

  def deleteTopic(name: String) = {
    val topic = Java.singletonList(name)
    val result = admin.deleteTopics(topic)
    val kafkaFuture = result.values().get(name)

    kafkaFuture.toFuture
  }
}

object KafkaAdmin {
  def make(servers: String)(implicit ec: ExecutionContext): KafkaAdmin = {
    val config = new Properties()
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers)

    val admin: AdminClient = AdminClient.create(config)

    new KafkaAdmin(admin)
  }
}
