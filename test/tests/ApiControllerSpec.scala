package tests
import scala.concurrent.{ExecutionContext, Future}
import controllers.ApiController
import mockws.MockWS
import mockws.MockWSHelpers.Action
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class ApiControllerSpec extends PlaySpec with Results {
  val resString =
    """{"success": true,
      |"deck_id": "h7jdxf1ameji",
      |"cards":
        |[{"code": "2D",
        |"image": "https://deckofcardsapi.com/static/img/2D.png",
        |"images": {"svg": "https://deckofcardsapi.com/static/img/2D.svg",
        |"png": "https://deckofcardsapi.com/static/img/2D.png"},
        |"value": "2", "suit": "DIAMONDS"}],
      |"remaining": 51}""".stripMargin
  val ws = MockWS {
    case (GET, "https://deckofcardsapi.com/api/deck/new/draw/?count=1") => Action { Ok(resString) }
  }
  implicit val ec = ExecutionContext.Implicits.global
  "The api" should {
    "be valid" in {
      val controller = new ApiController(ec, ws, Helpers.stubControllerComponents())
      val result: Future[Result] = controller.returnCards(1).apply(FakeRequest())
      val bodyText: String       = contentAsString(result)

      bodyText mustBe """{"cards":[{"suit":"D","value":2}],"hasStraight":false,"hasFlush":false,"highCard":2,"hasFullHouse":false,"pairs":0,"triples":0,"quads":0}"""
    }
    "be invalid for numCards < 1 and numCards > 52" in {
      val controller = new ApiController(ec, ws, Helpers.stubControllerComponents())
      val resultLower: Future[Result] = controller.returnCards(0).apply(FakeRequest())
      val bodyTextLower: String       = contentAsString(resultLower)
      val resultUpper: Future[Result] = controller.returnCards(53).apply(FakeRequest())
      val bodyTextUpper: String       = contentAsString(resultUpper)
      bodyTextLower mustBe ""
      bodyTextUpper mustBe ""
    }
  }
}
