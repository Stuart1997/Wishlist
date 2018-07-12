package connectors

import models.Games
import reactivemongo.api._
import reactivemongo.api.collections.GenericCollection
import reactivemongo.bson.document

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object GamesDatabase {
  val mongoUri = "mongodb://localhost:27017/HMRC"
  val driver: MongoDriver = MongoDriver()
  val parsedUri: Try[MongoConnection.ParsedURI] = MongoConnection.parseURI(mongoUri)
  val connection: Try[MongoConnection] = parsedUri.map(driver.connection)
  val futureConnection: Future[MongoConnection] = Future.fromTry(connection)
  val gamesDatabase: Future[DefaultDB] = futureConnection.flatMap(_.database("Wishlist"))
  val gamesCollection:Future[GenericCollection[BSONSerializationPack.type]] = gamesDatabase.map(_.collection("Games"))
  def findByName(name: String): Future[List[Games]] = {
    gamesCollection.flatMap(_.find(document("name" → name)).cursor[Games]().collect[List]())
  }

}
