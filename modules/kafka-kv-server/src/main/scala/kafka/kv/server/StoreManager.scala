package kafka.kv.server

import kafka.kv.server.model.CreateStoreRequest

import scala.concurrent.Future

trait StoreManager {
  def createStore(request: CreateStoreRequest): Future[Unit]
}

final class DefaultStoreManager {

}
