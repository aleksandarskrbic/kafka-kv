package kafka.kv.server.http

import sttp.model.StatusCode
import kafka.kv.server.http.response.{FailureInfo, SuccessInfo}

import scala.concurrent.Future

package object api {
  implicit class SuccessResponse(inner: (StatusCode, SuccessInfo)) {
    def right = Right(inner)
  }

  implicit class FailureResponse(inner: (StatusCode, FailureInfo)) {
    def left = Left(inner)
  }

  implicit class ResponseTransformer[T](future: Future[T]) {
    def ok(message: String) =
      SuccessInfo.ok(message)
  }
}
