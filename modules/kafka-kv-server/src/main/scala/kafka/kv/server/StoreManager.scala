package kafka.kv.server

import kafka.kv.server.model.CreateStoreRequest

trait StoreManager[F[_]] {
  def createStore(request: CreateStoreRequest)
}

final class DefaultStoreManager {

}
