package kafka.kv.server
import akka.actor.typed.ActorSystem
import org.apache.kafka.clients.producer.{
  KafkaProducer,
  Producer,
  ProducerRecord
}
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import org.apache.kafka.common.header
import org.apache.kafka.common.header.Headers
import org.apache.kafka.common.header.internals.{RecordHeader, RecordHeaders}

import java.util.Properties

object KafkaKvServer extends App {
  val h = new RecordHeaders()

  h.add("tip", "asd".getBytes)

  val p = new Properties()
  p.put("bootstrap.servers", "localhost:9092")
  p.put(
    "key.serializer",
    "org.apache.kafka.common.serialization.ByteArraySerializer"
  )
  p.put(
    "value.serializer",
    "org.apache.kafka.common.serialization.ByteArraySerializer"
  )
  val a = new KafkaProducer[Array[Byte], Array[Byte]](p)
  val r = new ProducerRecord[Array[Byte], Array[Byte]](
    "topic",
    null,
    "asd".getBytes(),
    "asd".getBytes(),
    h
  )

  a.send(r)

  Thread.sleep(10000)
}
