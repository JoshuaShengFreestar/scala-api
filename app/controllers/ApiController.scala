package controllers

import models.{Card, Hand}
import org.mongodb.scala._
import org.mongodb.scala.bson._
import org.mongodb.scala.bson.Document
import play.api.libs.functional.syntax.toInvariantFunctorOps
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}
import play.api.libs.ws._

import scala.collection.Seq
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import tour.Helpers._


@Singleton
class ApiController @Inject() (ec: ExecutionContext, ws: WSClient, val controllerComponents: ControllerComponents) extends BaseController {
  //json formatting templates
  implicit val charFormat: Format[Char] = Format.of[String].inmap[Char](_.head, _.toString)
  implicit val cardJson: OFormat[Card] = Json.format[Card]
  implicit val handWrites: Writes[Hand] = (hand: Hand) => Json.obj(
    "cards" -> hand.cards,
    "hasStraight" -> hand.straight,
    "hasFlush" -> hand.flush,
    "highCard" -> hand.highCard,
    "hasFullHouse" -> hand.fullHouse,
    "pairs" -> hand.pairs,
    "triples" -> hand.triples,
    "quads" -> hand.quads
  )

  //main function called by api
  def returnCards(numCards: Int): Action[AnyContent] = Action {
    if (!validateRequest(numCards)) {
      NoContent
    }
    else {
      val deckInfo : Seq[String] = get(s"https://deckofcardsapi.com/api/deck/new/draw/?count=${numCards}")
      val cards = parseApiData(deckInfo)
      mongoLog("GET", numCards.toString, Json.toJson(Hand(cards.toArray)))
      Ok(Json.toJson(Hand(cards.toArray)))
    }
  }

    //makes api call
  private def get(url: String, connectTimeout: Int = 5000, readTimeout: Int = 5000): Seq[String] = {
    val request: WSRequest = ws.url(url).addHttpHeaders("Accept" -> "application/json").withRequestTimeout(5000.millis)
    val futureResponse : Future[Seq[String]] = request.get().map{ response => (response.json \\ "code").map(_.as[String])}(ec)
    val response = for {r <- Await.result(futureResponse, 3000.millis)} yield r
    response
  }

  //logs to mongodb
  private def mongoLog(connType: String, param: String, resp: JsValue): Unit = Future {
    val respBson = resp.toString
    val uri: String = "mongodb+srv://admin:admin@cluster0.1op9b.mongodb.net/logs?retryWrites=true&w=majority"
    System.setProperty("org.mongodb.async.type", "netty")
    val client: MongoClient = MongoClient(uri)
    val db: MongoDatabase = client.getDatabase("logs")
    val coll: MongoCollection[Document] = db.getCollection("scalaapi")
    val document = BsonDocument("req" -> BsonString(connType), "param" -> BsonString(param), "response" -> BsonDocument(json=respBson))
    coll.insertOne(document).printResults()
  }(ec)

  private def validateRequest(numCards: Int): Boolean = {
    if (numCards < 1 || numCards > 52) false else true
  }

  private def parseApiData(rawDeckInfo: Seq[String]) = {
    val cards: Seq[Card] = for (i <- rawDeckInfo) yield {
      val value : Int = i.charAt(0) match {
        case 'A' => 1
        case 'J' => 11
        case 'Q' => 12
        case 'K' => 13
        case '0' => 10
        case default => default.asDigit
      }
      Card(i.charAt(1), value)
    }
    cards
  }
}
