package kafka.kv.admin

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object App extends App {

  val admin = KafkaAdmin.make("localhost:9092")

  admin.createCompactedTopic("asdddd").onComplete {
    case Failure(exception) => println(exception)
    case Success(value)     => println(value)
  }

  admin.listTopics().onComplete {
    case Failure(exception) => println(exception)
    case Success(value)     => println(value)
  }

  Thread.sleep(5000)
}
