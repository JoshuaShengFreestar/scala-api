package tests

import models.Card
import models.Hand
import org.scalatestplus.play._

class HandSpec extends PlaySpec {
  val deck : Seq[Card] = for {
    i <- Seq('D', 'H', 'S', 'C')
    j <- 1 to 13
  } yield Card(i, j)
  "A Hand" must {
    "correctly detect Doubles, Triples, and Quads" in {
      val hand : Hand = Hand(deck.toArray)
      val pair: Hand = Hand(Array(Card('H', 1), Card('D', 1)))
      val triple: Hand = Hand(Array(Card('H', 1), Card('D', 1), Card('C', 1)))
      val quad: Hand = Hand(Array(Card('H', 1), Card('D', 1), Card('C', 1), Card('S', 1)))

      hand.pairs mustBe 26
      hand.triples mustBe 13
      hand.quads mustBe 13

      pair.pairs mustBe 1
      pair.triples mustBe 0
      pair.quads mustBe 0

      triple.pairs mustBe 1
      triple.triples mustBe 1
      triple.quads mustBe 0

      quad.pairs mustBe 2
      quad.triples mustBe 1
      quad.quads mustBe 1
    }
    "correctly detect straights" in {
      val straight : Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4), Card('H', 3)))
      val almost : Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4)))
      val more : Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4), Card('H', 3), Card('H', 6)))

      straight.straight mustBe true
      almost.straight mustBe false
      more.straight mustBe true
    }

    "correctly detect flush" in {
      val flush: Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4), Card('H', 3)))
      val almost: Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4)))
      val more: Hand = Hand(Array(Card('H', 1), Card('H', 2), Card('H', 5), Card('H', 4), Card('H', 3), Card('H', 7)))

      flush.flush mustBe true
      almost.flush mustBe false
      more.flush mustBe true
    }

    "correctly detect full house" in {
      val fullHouse: Hand = Hand(Array(Card('D', 5), Card('C', 5), Card('H', 5), Card('D', 4), Card('C', 4)))
      val twoTriples: Hand = Hand(Array(Card('D', 5), Card('C', 5), Card('H', 5), Card('D', 4), Card('C', 4), Card('H', 4)))
      val twoDoubles: Hand = Hand(Array(Card('D', 5), Card('C', 5), Card('D', 4), Card('C', 4)))

      fullHouse.fullHouse mustBe true
      twoTriples.fullHouse mustBe true
      twoDoubles.fullHouse mustBe false
    }

    "correctly detect high card" in {
      val fullHand : Hand = Hand(deck.toArray)
      val oneCard: Hand = Hand(Array(Card('D', 1)))

      fullHand.highCard mustBe 13
      oneCard.highCard mustBe 1
    }
  }
}

class CardSpec extends PlaySpec {
  "A Card" must {
    "correctly convert between suits and values" in {
      val Cards: Seq[Card] = Seq(Card('D', 1), Card('H', 1), Card('S', 1), Card('C', 1))
      for (i <- 0 to 3) {
        Cards(i).suitToInt mustBe i
      }
    }
  }
}
