package kafka.kv.admin

import scala.concurrent.{Future, Promise}
import org.apache.kafka.common.KafkaFuture

object KafkaFutureOps {
  implicit class KafkaFutureOpsA[T](kafkaFuture: KafkaFuture[T]) {
    def toFuture: Future[T] = {
      val promise = Promise[T]()

      kafkaFuture.whenComplete { (result, error) =>
        if (error != null) promise.failure(error)
        else promise.success(result)
      }

      promise.future
    }
  }
}
