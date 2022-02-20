package kafka.kv.server.http.api

import kafka.kv.server.StoreManager
import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._
import sttp.model.StatusCode
import kafka.kv.server.http.api._

import scala.concurrent.{ExecutionContext, Future}
import kafka.kv.server.http.request.CreateStore
import kafka.kv.server.http.response.{FailureInfo, SuccessInfo}

class StoreApi(storeManager: StoreManager)(implicit ec: ExecutionContext) {
  private val baseEndpoint = endpoint.in("api" / "v1" / "create" / "store")

  val createStoreEndpoint =
    baseEndpoint.post
      .in(jsonBody[CreateStore])
      .errorOut(statusCode.and(jsonBody[FailureInfo]))
      .out(statusCode.and(jsonBody[SuccessInfo]))
      .serverLogic { request =>
        storeManager
          .createStore(request.name)
          .map { _ =>
            (StatusCode.Ok, SuccessInfo(s"Store ${request.name} created")).lift
          }
          .recoverWith { case _: Throwable =>
            val response = (
              StatusCode.BadRequest,
              FailureInfo(s"Store: ${request.name} already exists")
            )
            Future.successful(response.lift)
          }
      }
}
