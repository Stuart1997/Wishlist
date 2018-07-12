package services

import com.google.inject.ImplementedBy
import connectors.GamesDatabaseConnector
import javax.inject.Inject
import models.{Games, PriceRange}
import reactivemongo.bson.{BSONDocument, document}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@ImplementedBy(classOf[GamesDatabaseServiceImpl])
trait GamesDatabaseService {
  val databaseConnector: GamesDatabaseConnector

  private def find(query: BSONDocument): Future[List[Games]] = {
    databaseConnector.collection.flatMap(_.find(query).cursor[Games]().collect[List]())
  }

  private def findOne(query: BSONDocument): Future[Option[Games]] = {
    databaseConnector.collection.flatMap(_.find(query).one[Games])
  }

  def findByName(name: String): Future[Option[Games]] = {
    findOne(document("name" → name))
  }

  def retrieveAllGames: Future[List[Games]] = {
    find(document)
  }

  def retrieveAllGamesOverPrice(price: Double): Future[List[Games]] = {
    val query = document("price" -> BSONDocument("$gt" -> price))
    find(query)
  }

  def retrieveAllBetweenPrices(priceRange: PriceRange): Future[List[Games]] = {
    val query = document("price" -> BSONDocument("$gt" -> priceRange.lowestPrice, "$lt" -> priceRange.highestPrice))
    find(query)
  }

}

class GamesDatabaseServiceImpl @Inject()(val databaseConnector: GamesDatabaseConnector) extends GamesDatabaseService
