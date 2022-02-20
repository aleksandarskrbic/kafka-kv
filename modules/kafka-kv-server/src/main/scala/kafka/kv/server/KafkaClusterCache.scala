package kafka.kv.server
import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import kafka.kv.admin.KafkaAdmin
import kafka.kv.admin.model.TopicDetails

import scala.concurrent.duration._
import scala.util.{Failure, Success}

object KafkaClusterCache {
  object CacheActor {
    sealed trait Message
    final case class Response(state: Set[TopicDetails]) extends Message

    def apply(kafkaAdmin: KafkaAdmin): Behavior[CacheActor.Message] =
      Behaviors.setup { ctx =>
          val actorSystem = ctx.system
          val scheduler = actorSystem.scheduler
          implicit val ec = actorSystem.executionContext

          val stateActor = ctx.spawn(StateActor.make(kafkaAdmin), "cache-actor")

          scheduler.scheduleAtFixedRate(0.seconds, 10.seconds) { () =>
            stateActor ! StateActor.Refresh
          }

        ???
      }
  }

  object StateActor {
    sealed trait Message
    final case object Refresh extends Message
    final case class UpdateState(state: Set[TopicDetails]) extends Message
    final case class GetState(replyTo: ActorRef[CacheActor.Message]) extends Message

    def make(kafkaAdmin: KafkaAdmin, state: Set[TopicDetails] = Set.empty): Behavior[StateActor.Message] =
      Behaviors.receive {
        case (_ , GetState(replyTo: ActorRef[CacheActor.Message])) =>
          replyTo ! CacheActor.Response(state)
          Behaviors.same

        case (ctx, Refresh) =>
          ctx.pipeToSelf(kafkaAdmin.listTopics()) {
            case Failure(_) => UpdateState(state)
            case Success(newState) => UpdateState(newState)
          }
          Behaviors.same

        case (_, UpdateState(newState)) =>
          make(kafkaAdmin, newState)
      }
  }

}

final class KafkaClusterCache(kafkaAdmin: KafkaAdmin) {
  private val topics: Set[TopicDetails] = Set.empty
}
