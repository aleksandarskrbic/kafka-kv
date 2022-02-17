package kafka.kv.admin

import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, Config}

import java.util.Properties
import scala.concurrent.{ExecutionContext, Promise}

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
}

object KafkaAdmin {
  def make(servers: String)(implicit ec: ExecutionContext): KafkaAdmin = {
    val config = new Properties()
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

    val admin: AdminClient = AdminClient.create(config)

    new KafkaAdmin(admin)
  }
}
