package models

case class Hand(cards: Array[Card]) {

  def isStraight(cards : Array[Card]): Boolean = {
    val sortedCards = cards.sortBy(_.value)
    var count = 1
    var last = sortedCards(0)
    for (i <- sortedCards) {
      if (i.value == last.value + 1) {
        count += 1
        last = i
      }
      else if (i.value > last.value + 1) {
        count = 1
        last = i
      }
      if (count == 5) return true
    }
    false
  }

  def isFlush(cards : Array[Card]): Boolean = {
    val sortedSuits = cards.sortBy(_.suitToInt).map(_.suitToInt())
    var count = 0
    for (i <- 1 until sortedSuits.length) {
      if (sortedSuits(i) == sortedSuits(i - 1)) count += 1 else count = 1
      if (count == 5) return true
    }
    false
  }

  def getHighCard(cards : Array[Card]): Int = {
    cards.sortBy(_.value).last.value
  }

  /*
  Triple is incremented after double is, so if double > triple there is a pair that is not part of a set(triple)
   */
  def getFullHouse(cards : Array[Card]): Boolean = {
    val sortedVals = cards.sortBy(_.value).map(_.value)
    var count = 0
    var double = 0
    var triple = 0
    for (i <- 1 until cards.length) {
      if (sortedVals(i) == sortedVals(i-1)) {
        count += 1
      }
      count match {
        case 2 => double += 1
        case 3 => triple += 1
        case _ =>
      }
    }
    double > triple || triple > 1
  }

  def getDupes(cards: Array[Card]): Tuple3[Int, Int, Int] = {
    val sortedVals = cards.sortBy(_.value).map(_.value)
    var count = 1
    var double = 0
    var triple = 0
    var quad = 0
    for (i <- 1 until cards.length) {
      if (sortedVals(i) == sortedVals(i-1)) count += 1 else count = 1
      count match {
        case 2 => double += 1
        case 3 => triple += 1
        case 4 => {
          double += 1
          quad += 1
        }
        case _ =>
      }
    }
    Tuple3(double, triple, quad)
  }

  val straight: Boolean = isStraight(cards)
  val flush:Boolean = isFlush(cards)
  val highCard: Int = getHighCard(cards)
  val fullHouse: Boolean = getFullHouse(cards)
  val (pairs : Int, triples : Int, quads : Int) = getDupes(cards)
}