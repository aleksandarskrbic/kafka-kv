package kafka.kv.server.http.request

import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class CreateStore(name: String, keyType: KeyType)

object CreateStore {
  implicit val encoder: Encoder[CreateStore] = deriveEncoder
  implicit val decoder: Decoder[CreateStore] = deriveDecoder
}

sealed trait KeyType
object KeyType {
  final case object String extends KeyType
  final case object Long extends KeyType

  implicit val encoder: Encoder[KeyType] = {
    case String => Json.fromString("string")
    case Long   => Json.fromString("long")
  }

  implicit val decoder: Decoder[KeyType] =
    (cursor: HCursor) =>
      for {
        value <- cursor.as[String]
        result <- value.toLowerCase match {
          case "string" => Right(KeyType.String)
          case "long"   => Right(KeyType.Long)
          case _ =>
            Left(DecodingFailure(s"Invalid input: $value", cursor.history))
        }
      } yield result
}
