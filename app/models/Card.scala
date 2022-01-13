package models

//invariant: suit is one of (D, H, S, C) and value is from 1 to 13
case class Card(suit: Char, value: Int) {
  def suitToInt(): Int = {
    suit match {
      case 'D' => 0
      case 'H' => 1
      case 'S' => 2
      case 'C' => 3
      case _ => -1
    }
  }
}
