package controllers

import javax.inject.Inject
import models.{Games, PriceRange}
import play.api.mvc._
import services.GamesDatabaseService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GamesController @Inject()(gamesDataBaseService: GamesDatabaseService) extends Controller {

  def games(priceRange: Option[PriceRange]): Action[AnyContent] = Action.async { request =>
    //GamesDatabase.retrieveAllGames.map(data ⇒ Ok(views.html.games(data)))
    val gamesData: Future[List[Games]] = priceRange.map(x => gamesDataBaseService.retrieveAllBetweenPrices(x)).getOrElse(gamesDataBaseService.retrieveAllGames)
    gamesData.map(data => Ok(views.html.games(data)))
  }

  def wishlist = Action { request =>
    Ok(views.html.wishlist())
  }
}
