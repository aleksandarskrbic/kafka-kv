package kafka.kv.server

import kafka.kv.admin.KafkaAdmin
import kafka.kv.server.model.CreateStoreRequest

import scala.concurrent.Future

trait StoreManager {
  def createStore(request: CreateStoreRequest): Future[Unit]
}

final class DefaultStoreManager(kafkaAdmin: KafkaAdmin) extends StoreManager {
  override def createStore(request: CreateStoreRequest): Future[Unit] = ???
}

object StoreManager {
  def make(servers: String): StoreManager = {
    val kafkaAdmin = KafkaAdmin.make(servers)
    new DefaultStoreManager(kafkaAdmin)
  }
}
