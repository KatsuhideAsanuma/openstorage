package models

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{Future, ExecutionContext}
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

class UserModel @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def password = column[String]("password")
    def * = (id, name, email, password) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[UserTable]

  // ユーザー作成
  def createUser(user: User): Future[User] = db.run(users returning users += user)

  // ユーザー読み取り
  def readUser(id: Long): Future[Option[User]] = db.run(users.filter(_.id === id).result.headOption)

  // ユーザー更新
  def updateUser(user: User): Future[Int] = db.run(users.filter(_.id === user.id).update(user))

  // ユーザー削除
  def deleteUser(id: Long): Future[Int] = db.run(users.filter(_.id === id).delete)
}