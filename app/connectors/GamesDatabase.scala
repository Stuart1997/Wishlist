package connectors

import models.{Games, PriceRange}
import reactivemongo.api._
import reactivemongo.api.collections.GenericCollection
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, document}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object GamesDatabase {
  val mongoUri = "mongodb://localhost:27017/Wishlist"
  val driver: MongoDriver = MongoDriver()
  val parsedUri: Try[MongoConnection.ParsedURI] = MongoConnection.parseURI(mongoUri)
  val connection: Try[MongoConnection] = parsedUri.map(driver.connection)
  val futureConnection: Future[MongoConnection] = Future.fromTry(connection)
  val gamesDatabase: Future[DefaultDB] = futureConnection.flatMap(_.database("Wishlist"))
  val gamesCollection:Future[GenericCollection[BSONSerializationPack.type]] = gamesDatabase.map(_.collection("Games"))

  private def find(query: BSONDocument) = {
    gamesCollection.flatMap(_.find(query).cursor[Games]().collect[List]())
  }

  def findByName(name: String): Future[List[Games]] = {
    find(document("name" → name))
  }

  def retrieveAllGames = {
    find(document)
  }

  def retrieveAllGamesOverPrice(price: Double) = {
    val query = document("price" -> BSONDocument("$gt" -> price))
    find(query)
  }

  def retrieveAllBetweenPrices(priceRange: PriceRange) = {
    val query = document("price" -> BSONDocument("$gt" -> priceRange.lowestPrice, "$lt" -> priceRange.highestPrice))
    find(query)
  }

}
