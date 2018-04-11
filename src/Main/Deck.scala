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
<<<<<<< HEAD
    val suits = List("H","S","D","C")
=======
    val suits = List('H','S','D','C')
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
    val values = Range(1,14).toList
    var listOfCard = suits.flatMap(a => values.map(b => (b,a)))
    for(i <- listOfCard){
      if(i == (1,'H')){
<<<<<<< HEAD
        var cards = Array[Card](new Card(1, "H"))
=======
        var cards = Array[Card](new Card(1, 'H'))
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
      }
      else{
        cards = cards :+ new Card(i._1,i._2)
      }
      
    }
<<<<<<< HEAD
    cards = cards :+ new Card(1, "H")
=======
    cards = cards :+ new Card(1, 'H')
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
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
<<<<<<< HEAD
  //For loading purposes only
  def addToDeck(a: Main.Card): Unit = {
    if(cards.isEmpty){
      cards = Array(a)
    }
    else
      cards = cards :+ a
  }
=======
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
  
}