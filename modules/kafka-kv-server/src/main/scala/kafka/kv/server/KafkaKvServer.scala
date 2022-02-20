package kafka.kv.server
import akka.actor.typed.ActorSystem

object KafkaKvServer {

  val actorSystem = ActorSystem[_]()
}
