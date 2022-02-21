package kafka.kv.server.http.api
import sttp.tapir._
import sttp.tapir.json.circe._
import sttp.tapir.generic.auto._

import scala.concurrent.{ExecutionContext, Future}
import kafka.kv.server.http.request.CreateStore
import kafka.kv.server.http.response.{FailureInfo, SuccessInfo}
import kafka.kv.server.service.StoreManager

class StoreApi(storeManager: StoreManager)(implicit ec: ExecutionContext) {
  private val baseEndpoint = endpoint.in("api" / "v1" / "create" / "store")

  val createStoreLogic =
    Endpoints.createStore
      .serverLogic { request =>
        storeManager
          .createStore(request.name)
          .map(_ => SuccessInfo.ok(s"Store ${request.name} created"))
          .recoverWith { _ =>
            Future.successful(
              FailureInfo.badRequest(s"Store: ${request.name} already exists")
            )
          }
      }

  val createStoreLogic2 =
    Endpoints.createStore
      .serverLogic { request =>
        storeManager
          .createStore(request.name)
          .map(_ => SuccessInfo.ok(s"Store ${request.name} created"))
          .recoverWith { _ =>
            Future.successful(
              FailureInfo.badRequest(s"Store: ${request.name} already exists")
            )
          }
      }

  val routes = List(createStoreLogic, createStoreLogic2)

  object Endpoints {
    val createStore =
      baseEndpoint.post
        .in(jsonBody[CreateStore])
        .errorOut(statusCode.and(jsonBody[FailureInfo]))
        .out(statusCode.and(jsonBody[SuccessInfo]))
  }
}
