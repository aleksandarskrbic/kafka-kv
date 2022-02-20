package kafka.kv.client.api

import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._
import sttp.model.StatusCode
import kafka.kv.client.api.model.{ErrorInfo, SuccessInfo}

import scala.concurrent.Future

object StoreApi {
  val createStoreEndpoint =
    endpoint.post
      .in("api" / "v1" / "store")
      .errorOut(statusCode.and(jsonBody[ErrorInfo]))
      .out(statusCode.and(jsonBody[SuccessInfo]))
      .serverLogic { _ =>
        val response = StatusCode.Ok -> SuccessInfo("asd")

        if (true) {
          Future.successful(Right(response))
        } else {
          Future.successful(Left(StatusCode.NotFound -> ErrorInfo("error")))
        }
      }
}
