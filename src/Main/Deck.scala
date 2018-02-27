package Main
import scala.math.random
/*
 * Creates first empty deck
 * then makesDeck which it can shuffle aswell
 * and can deal cards
 */
object Cards {
  var cards = Array[Card]()
 
  def makeDeck() ={
    val suits = List('H','S','D','C')
    val values = Range(1,14).toList
    var listOfCard = suits.flatMap(a => values.map(b => (b,a)))
    for(i <- listOfCard){
      this.cards = this.cards :+ new Card(i._1,i._2)
      print(i)
  }
  }
  
  def shuffleDeck() = {
    var a = this.cards.toList
    a = scala.util.Random.shuffle(a)
    for (i <- a){ print(i.thisCard)}
    this.cards = a.toArray
  }
  def dealCard(): Main.Card = {
    // saves the first Card as a variable then 
    // removes it from the deck
    val Card = this.cards(0)
    this.cards = this.cards.drop(1)
    print (Card.thisCard)
    print (this.cards(0).thisCard)
    Card
  }
  
}