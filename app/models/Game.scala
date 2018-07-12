package models

import reactivemongo.bson.{BSONDateTime, BSONDocumentReader, BSONDocumentWriter, BSONInteger, BSONReader, BSONWriter, Macros}

case class Games(name: String,
                 publisher: String,
                 price: Double,
                 rating: Int,
                 releaseDate: BSONDateTime,
                 image: String,
                 discount: Int,
                 genre: List[Genre],
                 platform: List[Platform],
                 description: String)

object Games {
  implicit object ReleaseDateReader extends BSONReader[BSONInteger, BSONDateTime] {
    override def read(bson: BSONInteger): BSONDateTime = BSONDateTime(bson.value)
  }
  implicit object ReleaseDateWriter extends BSONWriter[BSONDateTime, BSONInteger] {
    override def write(t: BSONDateTime): BSONInteger = BSONInteger(t.value.toInt)
  }
  implicit val gamesWriter: BSONDocumentWriter[Games] = Macros.writer[Games]
  implicit val gamesReader: BSONDocumentReader[Games] = Macros.reader[Games]
}

case class Genre(name: String)
object Genre {
  implicit val genreReader: BSONDocumentReader[Genre] = Macros.reader[Genre]
  implicit val genreWriter: BSONDocumentWriter[Genre] = Macros.writer[Genre]
}

case class Platform(name: String)
object Platform {
  implicit val platformReader: BSONDocumentReader[Platform] = Macros.reader[Platform]
  implicit val platformWriter: BSONDocumentWriter[Platform] = Macros.writer[Platform]
}