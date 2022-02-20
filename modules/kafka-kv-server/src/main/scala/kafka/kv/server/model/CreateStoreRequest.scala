package kafka.kv.server.model

import io.circe._
import io.circe.generic.semiauto._

sealed trait KeyType
object KeyType {
  final case object String extends KeyType
  final case object Long extends KeyType

  implicit val decoder: Decoder[KeyType] =
    (cursor: HCursor) =>
      for {
        value <- cursor.as[String]
        result <- value match {
          case "String" | "STRING" | "string" => Right(KeyType.String)
          case "Long" | "LONG" | "long" => Right(KeyType.Long)
          case _ => Left(DecodingFailure(s"Invalid status: $value", cursor.history))
        }
      } yield result
}

final case class CreateStoreRequest(name: String, keyType: KeyType)

object CreateStoreRequest {
  implicit val decoder: Decoder[CreateStoreRequest] = deriveDecoder[CreateStoreRequest]
}
