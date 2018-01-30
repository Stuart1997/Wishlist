package controllers

import org.scalatest.Matchers
import org.scalatestplus.play._
import play.api.mvc._
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers.{status, _}

class GamesControllerSpec extends PlaySpec with OneAppPerTest {
  "GamesController" should {
    "check that the games page returns a 200" in {
      val controller = new GamesController
      val test = controller.games()(FakeRequest())
      status(test) mustBe 200
    }

    "check that the wishlist page returns a 200" in {
      val controller = new GamesController
      val test = controller.wishlist()(FakeRequest())
      status(test) mustBe 200
    }
  }
}
