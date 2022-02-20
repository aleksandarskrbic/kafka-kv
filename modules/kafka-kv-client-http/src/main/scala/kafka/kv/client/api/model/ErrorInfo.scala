package kafka.kv.client.api.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final case class ErrorInfo(message: String)

object ErrorInfo {
  implicit val encoder: Encoder[ErrorInfo] = deriveEncoder
  implicit val decoder: Decoder[ErrorInfo] = deriveDecoder
}
