package Main
// Creates player and tracks points collected cards and which are in hand
class Player(val Name: String) {
  var score = 0
  var hand = Array[Main.Card]()
  var collected = Array[Main.Card]()
  
  def addToHand(c: Main.Card) = {
    try{
      hand = hand :+ c
    }
    catch {
      case ex: NullPointerException => this.hand = Array(c)
    }
  }
  
  def showHand() = {
<<<<<<< HEAD
    print("\n\n ========== YOUR  CARDS ========== \n\n")
    for(i <- hand){
      print(i.thisCard + "\t")
    }
    print("\n\n")
=======
    print("\n ========== YOUR  CARDS ========== \n")
    for(i <- hand){
      print(i.thisCard + " ")
    }
    print("\n")
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
  }
  def coll(c: Main.Card) = {
    try{
      this.collected = this.collected :+ c
    }
    catch {
      case ex: NullPointerException => this.collected = Array(c)
    }
  }
  
  def getCard(index: Int ): Main.Card = {
    var i = index - 1
    if(index <= hand.length){
      val card = hand(i)
      hand = hand.take(i) ++ hand.takeRight(hand.length - index)
      print(card.thisCard() + " was chosen!\n")
      card
    } else {
      print ("New try. Options :\n") 
      this.showHand()
<<<<<<< HEAD
      val a = Game.CheckInput("turn2").toInt
=======
      val a = scala.io.StdIn.readInt
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
      this.getCard(a)
    }
  }
    
}