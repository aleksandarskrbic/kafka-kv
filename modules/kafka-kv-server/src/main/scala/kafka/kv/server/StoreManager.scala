package kafka.kv.server

import kafka.kv.admin.KafkaAdmin
import kafka.kv.server.http.request.CreateStore

import scala.concurrent.{ExecutionContext, Future}

trait StoreManager {
  def createStore(name: String): Future[Unit]
}

final class DefaultStoreManager(
    kafkaAdmin: KafkaAdmin
)(implicit ec: ExecutionContext)
    extends StoreManager {
  override def createStore(name: String): Future[Unit] =
    for {
      topics <- kafkaAdmin.listTopics()
      storeExists = topics.map(_.name).contains(name)
      _ <- {
        if (storeExists)
          Future.failed(new RuntimeException("Store already exists"))
        else
          kafkaAdmin.createCompactedTopic(name)
      }
    } yield ()
}

object StoreManager {
  def make(servers: String)(implicit ec: ExecutionContext): StoreManager = {
    val kafkaAdmin = KafkaAdmin.make(servers)
    new DefaultStoreManager(kafkaAdmin)
  }
}
