package kafka.kv.server.http.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

final case class FailureInfo(message: String)

object FailureInfo {
  implicit val encoder: Encoder[FailureInfo] = deriveEncoder
  implicit val decoder: Decoder[FailureInfo] = deriveDecoder
}
