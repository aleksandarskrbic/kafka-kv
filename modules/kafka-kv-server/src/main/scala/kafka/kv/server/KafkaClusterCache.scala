package kafka.kv.server
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.pattern.StatusReply
import kafka.kv.admin.KafkaAdmin
import kafka.kv.admin.model.TopicDetails

import scala.concurrent.duration._
import scala.util.{Failure, Success}

object KafkaClusterCache {
  final case class State(value: Set[TopicDetails] = Set.empty)

  object CacheActor {
    sealed trait Message
    final case class Response(state: State) extends Message
    final case class Get(replyTo: ActorRef[StatusReply[State]]) extends Message

    def make(kafkaAdmin: KafkaAdmin): Behavior[CacheActor.Message] =
      Behaviors.setup { ctx =>
          val actorSystem = ctx.system
          val scheduler = actorSystem.scheduler
          implicit val ec = actorSystem.executionContext

          val stateActor = ctx.spawn(StateActor.make(kafkaAdmin), "cache-actor")

          scheduler.scheduleAtFixedRate(0.seconds, 10.seconds) { () =>
            stateActor ! StateActor.Refresh
          }

        Behaviors.receiveMessage {
          case Response(state) => ???
          case Get(replyTo) => ???
        }
      }
  }

  object StateActor {
    sealed trait Message
    final case object Refresh extends Message
    final case class UpdateState(state: State) extends Message
    final case object UpdateFailure extends Message
    final case class GetState(replyTo: ActorRef[CacheActor.Message]) extends Message

    def make(kafkaAdmin: KafkaAdmin, state: State = State()): Behavior[StateActor.Message] = {
      Behaviors.setup { ctx =>

        def updateState(): Unit = {
          ctx.pipeToSelf(kafkaAdmin.listTopics()) {
            case Failure(_) => Refresh
            case Success(newState) => UpdateState(State(newState))
          }
        }

        Behaviors.receiveMessage {
          case GetState(replyTo: ActorRef[CacheActor.Message]) =>
            replyTo ! CacheActor.Response(state)
            Behaviors.same

          case Refresh =>
            updateState()
            Behaviors.same

          case UpdateState(newState) =>
            make(kafkaAdmin, newState)
          }
        }
    }
  }
}

final class KafkaClusterCache(kafkaAdmin: KafkaAdmin) {
  private val topics: Set[TopicDetails] = Set.empty
}
