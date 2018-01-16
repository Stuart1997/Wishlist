package controllers

import play.api.mvc._

class GamesController extends Controller {

  def index = Action { request =>
    Ok(views.html.index("Hello"))
  }
}
