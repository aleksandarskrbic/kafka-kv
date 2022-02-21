package kafka.kv.server.http.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.model.StatusCode
import kafka.kv.server.http.api._

final case class SuccessInfo(message: String)

object SuccessInfo {
  implicit val encoder: Encoder[SuccessInfo] = deriveEncoder
  implicit val decoder: Decoder[SuccessInfo] = deriveDecoder

  def ok(message: String) =
    (StatusCode.Ok -> SuccessInfo(message)).right
}
