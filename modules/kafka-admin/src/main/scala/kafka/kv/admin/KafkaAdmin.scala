package kafka.kv.admin

import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, Config}

import java.util.Properties
import scala.concurrent.{ExecutionContext, Promise}
import java.util.{List => JavaList, Collections => Java}

class KafkaAdmin(admin: AdminClient)(implicit ec: ExecutionContext) {
  def createCompactedTopic(name: String) = {
    val promise = Promise[Config]()
    val topic = Topic(name).toJava
    val result = admin.createTopics(topic)
    val kafkaFuture = result.config(name)

    kafkaFuture.whenComplete { (config, error) =>
      if (error != null) promise.failure(error)
      else promise.success(config)
    }

    promise.future
  }

  def deleteTopic(name: String) = {
    val promise = Promise[Unit]()
    val topic = Java.singletonList(name)
    val result = admin.deleteTopics(topic)
    val kafkaFuture = result.topicNameValues().get(name)

    kafkaFuture.whenComplete { (_, error) =>
      if (error != null) promise.failure(error)
      else promise.success(())
    }

    promise.future
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
