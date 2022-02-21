package kafka.kv.server.http.response

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import sttp.model.StatusCode
import kafka.kv.server.http.api._

final case class FailureInfo(message: String)

object FailureInfo {
  implicit val encoder: Encoder[FailureInfo] = deriveEncoder
  implicit val decoder: Decoder[FailureInfo] = deriveDecoder

  def badRequest(message: String) =
    (StatusCode.BadRequest -> FailureInfo(message)).left
}
