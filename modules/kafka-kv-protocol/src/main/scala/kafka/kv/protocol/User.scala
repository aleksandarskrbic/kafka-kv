package kafka.kv.protocol

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

final case class User(username: String, password: String)

object User {
  implicit val codec: Codec[User] = deriveCodec
}
