package models

case class Hand(cards: Array[Card]) {

  def isStraight(cards : Array[Card]): Boolean = {
    true
  }

  def isFlush(cards : Array[Card]): Boolean = {
    true
  }

  def getHighCard(cards : Array[Card]): Int = {
    0
  }

  def getFullHouse(cards : Array[Card]): Boolean = {
    true
  }

  def getDupes(cards: Array[Card]): Tuple3[Int, Int, Int] = {
    Tuple3(0, 0, 0)
  }

  val straight: Boolean = isStraight(cards)
  val flush:Boolean = isFlush(cards)
  val highCard: Int = getHighCard(cards)
  val fullHouse: Boolean = getFullHouse(cards)
  val (pairs : Int, triples : Int, quads : Int) = getDupes(cards)
}