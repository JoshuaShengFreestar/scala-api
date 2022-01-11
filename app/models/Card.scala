package models

case class Card(suit: Char, value: Int) {
  def suitToInt(): Int = {
    suit match {
      case 'D' => 0
      case 'H' => 1
      case 'S' => 2
      case 'C' => 3
    }
  }
}
