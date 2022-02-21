package kafka.kv.server
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.adapter._

import akka.http.scaladsl.Http
import akka.kafka.ProducerSettings
import kafka.kv.admin.KafkaAdmin
import kafka.kv.server.http.api.StoreApi
import kafka.kv.server.service.StoreManager
import org.apache.kafka.clients.producer.{ KafkaProducer, Producer, ProducerRecord }
import org.apache.kafka.common.serialization.{ Serializer, StringSerializer }
import org.apache.kafka.common.header
import org.apache.kafka.common.header.Headers
import org.apache.kafka.common.header.internals.{ RecordHeader, RecordHeaders }
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
import kafka.kv.common._

import java.util.Properties
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.DurationInt
import scala.util.{ Failure, Success }

object HttpServer extends App {
  implicit val actorSystem = akka.actor.ActorSystem("ClassicToTypedSystem")
  val typedSystem: ActorSystem[Nothing] = actorSystem.toTyped

  import actorSystem.dispatcher

  val route = AkkaHttpServerInterpreter().toRoute(new StoreApi(StoreManager.make("localhost:9092")).routes)

  val bindAndCheck =
    Http().newServerAt("localhost", 8080).bindFlow(route)

  Await.result(bindAndCheck, 1.minute)
  println("Server started!")
}

object KafkaKvServer extends App {
  implicit val ec = Blocker.default()
  val p = ByteArrayProducer.make(ProducerConfiguration(List("localhost:9092")))
  val h = new RecordHeaders()

  h.add("tip", "asd".getBytes)

  /*  val p = new Properties()
  p.put("bootstrap.servers", "localhost:9092")
  p.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
  p.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
  val a = new KafkaProducer[Array[Byte], Array[Byte]](p)*/
  val r = new ProducerRecord[Array[Byte], Array[Byte]]("topic", null, "asd".getBytes(), "asd".getBytes(), h)
  p.send(r).onComplete {
    case Failure(exception) => println(exception)
    case Success(value)     => println(value.offset())
  }

  Thread.sleep(10000)
}
