package kafka.kv.client

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.adapter._
import kafka.kv.client.api.StoreApi
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import akka.http.scaladsl.Http

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object App extends App {
  implicit val actorSystem = akka.actor.ActorSystem("ClassicToTypedSystem")
  val typedSystem: ActorSystem[Nothing] = actorSystem.toTyped

  import actorSystem.dispatcher

  val route = AkkaHttpServerInterpreter().toRoute(
    StoreApi.createStoreEndpoint
  )

  val bindAndCheck =
    Http().newServerAt("localhost", 8080).bindFlow(route)

  Await.result(bindAndCheck, 1.minute)
  println("Server started!")
}
