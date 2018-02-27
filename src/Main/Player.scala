package Main
// Creates player and tracks points collected cards and which are in hand
class Player(val Name: String) {
  var score = 0
  var hand = Array[Main.Card]()
  var collected = Array[Main.Card]()
  def GetCard() = {
    hand = hand :+ Main.Cards.dealCard()
  }
  def showHand() = {
    print("\n CARDS: \n ===== \n")
    for(i <- hand){
      print(i.thisCard + " ")
    }
  }
  def removeCard(index: Int ): Main.Card = {
    var i = index - 1
    if(index <= hand.length){
      val card = hand(i)
      hand = hand.take(i) ++ hand.takeRight(hand.length - index)
      this.showHand()
      card
    } else {
      print ("No such card. Options :") 
      this.showHand()
      val a = scala.io.StdIn.readLine()
      this.removeCard(a.toInt)
    }
  }
    
}