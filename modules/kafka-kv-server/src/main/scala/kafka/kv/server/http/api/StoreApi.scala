package kafka.kv.server.http.api

import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._
import sttp.model.StatusCode

import scala.concurrent.Future
import kafka.kv.server.http.request.CreateStore
import kafka.kv.server.http.response.{FailureInfo, SuccessInfo}

class StoreApi {
  val createStoreEndpoint =
    endpoint.post
      .in("api" / "v1" / "create" / "store")
      .in(jsonBody[CreateStore])
      .errorOut(statusCode.and(jsonBody[FailureInfo]))
      .out(statusCode.and(jsonBody[SuccessInfo]))
      .serverLogic { _ =>
        val response = StatusCode.Ok -> SuccessInfo("asd")

        if (true) {
          Future.successful(Right(response))
        } else {
          Future.successful(Left(StatusCode.NotFound -> FailureInfo("error")))
        }
      }
}
