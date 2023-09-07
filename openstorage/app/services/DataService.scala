package services

import javax.inject._
import models.Data
import models.DataTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import org.redisson.api.RedissonClient
import scala.concurrent.{Future, ExecutionContext}

class DataService @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, redisson: RedissonClient)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  val data = TableQuery[DataTable]
  val bucket = redisson.getBucket("data")

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