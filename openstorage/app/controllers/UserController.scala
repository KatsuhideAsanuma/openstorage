package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import services.UserService

@Singleton
class UserController @Inject()(val controllerComponents: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends BaseController {

  // ユーザー登録
  def register = Action.async(parse.json) { request =>
    val result = request.body.validate[User]
    result.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      user => {
        userService.createUser(user).map { user =>
          Ok(Json.toJson(user))
        }
      }
    )
  }

  // APIキー発行
  def issueApiKey(userId: String) = Action.async { request =>
    userService.issueApiKey(userId).map { apiKey =>
      Ok(Json.obj("apiKey" -> apiKey))
    }
  }

  // APIキー管理
  def manageApiKey(userId: String) = Action.async(parse.json) { request =>
    val result = request.body.validate[ApiKey]
    result.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors))))
      },
      apiKey => {
        userService.manageApiKey(userId, apiKey).map { _ =>
          Ok(Json.obj("message" -> "API key updated successfully"))
        }
      }
    )
  }
}