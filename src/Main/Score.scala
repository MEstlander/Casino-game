package Main

class Score(val players: Array[Main.Player]) {
  //counts the cards after round and gives points
  //also announces winner if someone or multiple have >16 points
  
  def count(): Unit ={
    var mostCards = Array[Main.Player](players(0))
    for (i <- players){
      if (mostCards(0).collected.length < i.collected.length //longest stack of collected cards
          && i.Name != mostCards(0).Name){ // just so the first players isn't added twice
        mostCards = Array[Main.Player](i)
      } else if(mostCards(0).collected.length == i.collected.length ){
        mostCards = mostCards :+ i
      }
    }
    for (j <- mostCards){
      j.score += 1
      
    }
    this.mostSpades()
  }
  
  def mostSpades():Unit = {
    var mostSpades = Array[Main.Player](players(0))
    var spadeCount = 0
    for (i <- players){
      var spades = 0
      for (j <- i.collected)
        if(j.suit == 'S')
          spades += 1
      if(spades > spadeCount){
        mostSpades = Array(i)
      }
      else if(spades == spadeCount){
       mostSpades = mostSpades :+ i
    }
    }
    for( k <- mostSpades){
      k.score += 2
    }
    this.cardPoints
  }
  
  def cardPoints(): Unit = {
    for (i <- players){
      for(j <- i.collected){
        i.score += j.point
    }
    print("\nPlayer " + i.Name + " has " + i.score + " points\n")
  }
    this.winner()
  }
  
  def winner():Unit = {
    var winners = Array[Main.Player]()
    var evens = Array[Main.Player]()
    for (i <- players){
      if( i.score >= 16){
        Game.NotOver = false
        winners = winners :+ i
      }
    }
    if (winners.length > 1){
      var high = 0
      for(j <- winners){
        if(j.score > high){
          high = j.score
          evens = Array(j)
          }
        else if (j.score == high){
          evens = evens :+ j
        }
        }
      winners = evens
      }
    if(winners.length > 0){
      if(winners.length > 1){
        print ("It's a tie between:\n")
        for (k <- winners){
          print(k.Name + "\n")
        }
      }
      else{
        print("\n ============================== \n" +
            " === THE WINNER IS " + winners(0).Name + "! ! ! === \n" +
            " ==============================")
      }
    }
  }
 
}