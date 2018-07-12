package controllers

import connectors.GamesDatabase
import models.{Games, PriceRange}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GamesController extends Controller {

  def games(priceRange: Option[PriceRange]) = Action.async { request =>
    //GamesDatabase.retrieveAllGames.map(data ⇒ Ok(views.html.games(data)))
    val gamesData: Future[List[Games]] = priceRange.map(x => GamesDatabase.retrieveAllBetweenPrices(x)).getOrElse(GamesDatabase.retrieveAllGames)
    gamesData.map(data => Ok(views.html.games(data)))
  }

  def wishlist = Action { request =>
    Ok(views.html.wishlist())
  }
}
