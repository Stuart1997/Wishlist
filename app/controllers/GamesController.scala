package controllers

import play.api.mvc._

class GamesController extends Controller {

  def games = Action { request =>
    Ok(views.html.games())
  }

  def wishlist = Action { request =>
    Ok(views.html.wishlist())
  }
}
