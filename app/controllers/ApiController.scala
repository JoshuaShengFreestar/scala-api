package controllers

import models.Card
import models.Hand
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import javax.inject.{Inject, Singleton}

@Singleton
class ApiController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {
  def returnData(): Action[AnyContent] = Action {
    NoContent
  }
  def returnCards(numCards: Int) = Action {
    val cards = for (i <- 1 to numCards) yield Card(0, 0)
      Hand(cards.toArray)
  }
}
