package services

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

class DataService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, redisson: RedissonClient)
                           (implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._


  // データ作成
  def createData(data: Data): Future[Data] = db.run(data returning data += data)

  // データ読み取り
  def readData(id: Long): Future[Option[Data]] = db.run(data.filter(_.id === id).result.headOption)

  // データ更新
  def updateData(data: Data): Future[Int] = db.run(data.filter(_.id === data.id).update(data))

  // データ削除
  def deleteData(id: Long): Future[Int] = db.run(data.filter(_.id === id).delete)

  // Redisからデータ読み取り
  def readDataFromRedis(id: Long): Future[Option[Data]] = Future {
    Option(bucket.get(id.toString))
  }
}