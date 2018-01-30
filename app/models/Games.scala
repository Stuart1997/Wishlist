package models

import org.joda.time.DateTime
import play.api.libs.json.{Format, Json}

case class Games(name: String,
                 publisher: String,
                 price: Double,
                 rating: Int,
                 releaseDate: DateTime,
                 image: String,
                 discount: Int,
                 genre: List[String],
                 platform: List[String],
                 description: String)

object Games {
  implicit val formats: Format[Games] = Json.format[Games]
}
