package services

import javax.inject.{Inject, Singleton}
import models.{User, UserModel}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.{Future, ExecutionContext}

@Singleton
class UserService @Inject()(userModel: UserModel, dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  def createUser(user: User): Future[User] = {
    userModel.createUser(user)
  }

  def readUser(id: Long): Future[Option[User]] = {
    userModel.readUser(id)
  }
 def issueApiKey(userId: Long): Future[String] = {
    // APIキーの発行ロジックをここに書きます。
    // この例では、簡単のためにユーザーIDをそのままAPIキーとして返しています。
    Future.successful(userId.toString)
  }
  // 他のメソッドも同様に定義します
}