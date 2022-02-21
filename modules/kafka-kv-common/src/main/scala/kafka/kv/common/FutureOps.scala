package kafka.kv.common

import org.apache.kafka.common.KafkaFuture
import scala.concurrent.{ ExecutionContext, Future, Promise }

object FutureOps {
  implicit class KafkaFutureOps[T](kafkaFuture: KafkaFuture[T]) {
    def toFuture: Future[T] = {
      val promise = Promise[T]()

      kafkaFuture.whenComplete { (result, error) =>
        if (error != null) promise.failure(error)
        else promise.success(result)
      }

      promise.future
    }
  }

  implicit class ScalaFutureOps[T](future: Future[T])(implicit ec: ExecutionContext) {
    def unit: Future[Unit] =
      future.map(_ => ())
  }
}
