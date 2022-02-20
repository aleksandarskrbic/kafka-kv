package kafka.kv.server.http

import kafka.kv.server.http.response.{FailureInfo, GenericResponse, SuccessInfo}
import sttp.model.StatusCode

package object api {
  implicit class SuccessResponse(inner: (StatusCode, SuccessInfo)) {
    def lift = Right(inner)
  }

  implicit class FailureResponse(inner: (StatusCode, FailureInfo)) {
    def lift = Left(inner)
  }
}
