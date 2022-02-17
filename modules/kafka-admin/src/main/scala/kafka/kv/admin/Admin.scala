package kafka.kv.admin

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object Admin extends App {

  val admin = KafkaAdmin.make("localhost:9092")

  admin.deleteTopic("random").onComplete {
    case Failure(exception) => println(exception)
    case Success(value)     => println(value)
  }

  Thread.sleep(5000)
}
