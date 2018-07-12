package connectors

import org.scalatestplus.play.{OneAppPerTest, PlaySpec}

import scala.concurrent.ExecutionContext.Implicits.global

class GamesDataBaseSpec extends PlaySpec with OneAppPerTest {

  val connector: GamesDatabase.type = GamesDatabase
  "test" in {
    connector.findByName("Middle-earth: Shadow of Mordor")
  }

}
