package connectors

import com.google.inject.ImplementedBy
import models.{Games, PriceRange}
import reactivemongo.api._
import reactivemongo.api.collections.GenericCollection
import reactivemongo.bson.{BSONDocument, document}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

@ImplementedBy(classOf[GamesDatabaseConnectorImpl])
trait GamesDatabaseConnector {
  protected val mongoUri: String
  protected val databaseName: String
  val collectionName: String

  private val driver: MongoDriver = MongoDriver()
  private def parsedUri: Try[MongoConnection.ParsedURI] = MongoConnection.parseURI(mongoUri)
  private def connection: Try[MongoConnection] = parsedUri.map(driver.connection)
  private def futureConnection: Future[MongoConnection] = Future.fromTry(connection)
  protected def database: Future[DefaultDB] = futureConnection.flatMap(_.database(databaseName))
  def collection: Future[GenericCollection[BSONSerializationPack.type]] = database.map(_.collection(collectionName))
}

class GamesDatabaseConnectorImpl extends GamesDatabaseConnector {
  val mongoUri = "mongodb://localhost:27017/HMRC"
  val databaseName = "Wishlist"
  val collectionName: String = "Games"
}
