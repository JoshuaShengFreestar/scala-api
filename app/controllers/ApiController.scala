package controllers

import models.Card
import models.Hand
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

import javax.inject.{Inject, Singleton}

@Singleton
class ApiController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {
  implicit val cardJson = Json.format[Card]
  implicit val handJson = Json.format[Hand]
  def returnData(): Action[AnyContent] = Action {
    NoContent
  }
  def returnCards(numCards: Int) = Action {
    if (numCards < 1 || numCards > 52) {
      NoContent
    }
    else {
      val cards = for (i <- 1 to numCards) yield Card(0, 0)
      Ok(Json.toJson(Hand(cards.toArray)))
    }
  }
}
