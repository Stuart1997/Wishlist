package models

import play.api.mvc.QueryStringBindable

case class PriceRange(lowestPrice: Double, highestPrice: Double)

object PriceRange {
  implicit def queryStringBindable(implicit doubleBinder: QueryStringBindable[Double]) = new QueryStringBindable[PriceRange] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, PriceRange]] = {
      for {
        lowestPrice <- doubleBinder.bind("lowestPrice", params)
        highestPrice <- doubleBinder.bind("highestPrice", params)
      } yield {
        (lowestPrice, highestPrice) match {
          case (Right(lowestPrice), Right(highestPrice)) => Right(PriceRange(lowestPrice, highestPrice))
          case _ => Left("Unable to bind an PriceRange")
        }
      }
    }
    override def unbind(key: String, ageRange: PriceRange): String = {
      doubleBinder.unbind("lowestPrice", ageRange.lowestPrice) + "&" + doubleBinder.unbind("highestPrice", ageRange.highestPrice)
    }
  }
}