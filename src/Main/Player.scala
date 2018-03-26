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
    print("\n\n ========== YOUR  CARDS ========== \n\n")
    for(i <- hand){
      print(i.thisCard + "\t")
    }
    print("\n\n")
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
      val a = Game.CheckInput("turn2").toInt
      this.getCard(a)
    }
  }
    
}