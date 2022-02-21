package kafka.kv.client.api.model

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class SuccessInfo(message: String)

object SuccessInfo {
  implicit val encoder: Encoder[SuccessInfo] = deriveEncoder
  implicit val decoder: Decoder[SuccessInfo] = deriveDecoder
}