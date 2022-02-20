package kafka.kv.server.http.response
import io.circe.{Codec, Decoder, Encoder}
import io.circe.generic.semiauto.{deriveCodec, deriveDecoder, deriveEncoder}

sealed trait GenericResponse

object GenericResponse {
  final case class FailureInfo(message: String) extends GenericResponse

  implicit val failureCodec: Codec[FailureInfo] = deriveCodec

  implicit val successCoded: Codec[SuccessInfo] = deriveCodec

  final case class SuccessInfo(message: String) extends GenericResponse
}
