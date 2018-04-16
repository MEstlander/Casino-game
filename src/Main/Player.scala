package Main
// Creates player and tracks points collected cards and which are in hand
class Player(val Name: String) {
  var score = 0
  var Bot = false
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
    //Prints hand so player can see their cards
    print("\n\n ========== YOUR  CARDS ========== \n\n")
    for(i <- hand){
      print(i.thisCard + "\t")
    }
    print("\n\n")
  }
  def coll(c: Main.Card) = {
    // adds card to stack
    try{
      this.collected = this.collected :+ c
    }
    catch {
      case ex: NullPointerException => this.collected = Array(c)
    }
  }
  
  def getCard(index: Int ): Main.Card = {
    // returns and removes a card at a certain index
    var i = index - 1
    if(index <= hand.length){
      val card = hand(i)
      hand = hand.take(i) ++ hand.takeRight(hand.length - index)
      print("\n" + card.thisCard() + " was chosen!\n")
      card
    } else {
      print ("New try. Options :\n") 
      this.showHand()
      val a = Game.CheckInput("turn2").toInt
      this.getCard(a)
    }
  }
    
}