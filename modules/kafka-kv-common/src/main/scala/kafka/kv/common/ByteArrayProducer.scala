package kafka.kv.common

import org.apache.kafka.clients.producer.{ Callback, KafkaProducer, ProducerRecord, RecordMetadata }

import java.util.Properties
import scala.concurrent.{ ExecutionContext, Future, Promise }

final case class ProducerConfiguration(servers: List[String]) {
  val bootstrapServers: String = servers.mkString(",")
}

final class ByteArrayProducer(inner: KafkaProducer[Array[Byte], Array[Byte]])(implicit ec: ExecutionContext) {

  def send(record: ProducerRecord[Array[Byte], Array[Byte]]): Future[RecordMetadata] = {
    val promise = Promise[RecordMetadata]()

    inner.send(record, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
        if (exception != null) promise.failure(exception)
        else promise.success(metadata)
      }
    })

    promise.future
  }
}

object ByteArrayProducer {
  private val ByteArraySerializer = "org.apache.kafka.common.serialization.ByteArraySerializer"

  def make(config: ProducerConfiguration)(implicit ec: ExecutionContext): ByteArrayProducer = {
    val props = new Properties()
    props.put("bootstrap.servers", config.bootstrapServers)
    props.put("key.serializer", ByteArraySerializer)
    props.put("value.serializer", ByteArraySerializer)
    props.put("compression.type", "lz4")
    props.put("acks", "all")
    val kafkaProducer = new KafkaProducer[Array[Byte], Array[Byte]](props)
    new ByteArrayProducer(kafkaProducer)
  }
}
