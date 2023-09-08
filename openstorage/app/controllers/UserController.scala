package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import models.User
import models.ApiKey
import services.UserService
import models.UserModel
import play.api.libs.json.{Json, Reads, Format}
import scala.concurrent.{ExecutionContext, Future}
import org.redisson.api.RedissonClient



class UserController @Inject()(cc: ControllerComponents, protected val dbConfigProvider: DatabaseConfigProvider, userModel: UserModel)
                              (implicit ec: ExecutionContext) extends AbstractController(cc) {
  implicit val userReads: Reads[User] = Json.reads[User]
  implicit val userFormat: Format[User] = Json.format[User]
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  val userService: UserService = new UserService(userModel, dbConfigProvider)

  // コントローラーのメソッドを実装する部分...

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
  def issueApiKey(userId: Long) = Action.async { request =>
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