package Main
import scala.math.random
/*
 * Creates first empty deck
 * then makesDeck which it can shuffle aswell
 * and can deal cards
 */

object Deck {
  var cards = Array[Card]()
  
  //Makes a shuffled deck
  def makeDeck() ={
    var cards = Array[Card]()
    val suits = List("H","S","D","C")
    val values = Range(1,14).toList
    var listOfCard = suits.flatMap(a => values.map(b => (b,a)))
    for(i <- listOfCard){
      if(i == (1,'H')){
        var cards = Array[Card](new Card(1, "H"))
      }
      else{
        cards = cards :+ new Card(i._1,i._2)
      }
      
    }
    cards = cards :+ new Card(1, "H")
    var a = cards.toList
    a = scala.util.Random.shuffle(a)
    cards = a.toArray
    cards
  }
  
  def shuffleDeck():Unit = {
    var a = this.cards.toList
    a = scala.util.Random.shuffle(a)
    this.cards = a.toArray
  }
  def dealCard(): Main.Card = {
    // saves the first Card as a variable then 
    // removes it from the deck
    val Cardo = cards(0)
    cards = cards.drop(1)
    Cardo
  }
  //For loading purposes only
  def addToDeck(a: Main.Card): Unit = {
    if(cards.isEmpty){
      cards = Array(a)
    }
    else
      cards = cards :+ a
  }
  
}