package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import services.UserService
import scala.concurrent.Future

// ユーザーモデルの定義
case class User(id: Long, name: String, email: String, password: String)

class UserControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "UserController POST" should {

    "register a new user" in {
      val userService = mock[UserService]
      when(userService.createUser(any[User])) thenReturn Future.successful(User(1, "testuser", "test@example.com", "password"))

      val controller = new UserController(stubControllerComponents(), userService)
      val userJson = Json.parse("""
        {
          "name": "testuser",
          "email": "test@example.com",
          "password": "password"
        }
      """)
      val request = FakeRequest(POST, "/register").withJsonBody(userJson)
      val result = controller.register().apply(request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe Json.toJson(User(1, "testuser", "test@example.com", "password")) // レスポンスの確認を修正
    }
  }
}