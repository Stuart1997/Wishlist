package controllers

import connectors.GamesDatabase
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

class GamesController extends Controller {

  def games = Action.async { request =>
    GamesDatabase.findByName("Middle-earth: Shadow of Mordor").map(data ⇒ Ok(views.html.games(data)))
  }

  def wishlist = Action { request =>
    Ok(views.html.wishlist())
  }
}
