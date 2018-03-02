package Main

object Game extends App {
  var gPlayers = Array[Main.Player]()
  var gDeck = Deck
  var gTable = Array[Main.Card](new Card(1,'S'))
  var round = 0
  var lp = new Main.Player("dumbo")
  var NotOver = true
  
  print()
		print("		================================\n")
		print("		Welcome to the Casino Card Game!\n")
		print("		================================\n")
		print("\nINFO\n")
		print("\nThe cards in your hand and on the table are indexed 1,2,3,4... if you \n" +
				"cannot use a card, type '0' and you will be able to put down a card on the table\n" +
				"To collect a sum just type the index after each other without any spaces or symbols,\n"+
				"to separate sums add ','.\n")
		print("\nRULES\n")
		print("You can only use one card from your hand, you want to\n" +
				 "collect sums from the table that adds up to your card.\n" +
				 "10D is worth 2p and is valued at 16 in the hand, 2S is worth 1p and is\n" +
				 "valued at 15 in the hand. Aces (1's) are worth 1p and are valued 14 in \n" +
				 "the hand. For a sweep you get 1p, for most spades 2p and for most cards 1p.\n" +
				 "First player to 16p wins!")
		print("You can save the game by typing 'SAVE', if you wish to exit the game,\n" + 
				"type 'QUIT'.")
		
		
	print("\nHow many players?\n")	
	this.players(scala.io.StdIn.readInt())
	this.newRound
	def rmFromTable(c: Main.Card): (Boolean, Main.Card) = {
    var i = 0
    for(j <- gTable){
      
      if(j.thisCard == c.thisCard){
        gTable = gTable.take(i) ++ gTable.takeRight(gTable.length - i - 1)
        return (true, c)
      }
        
      i += 1
    }
    return (false, c)
  }
		
	def players(amt: Int) = {
  for( i <- 1 to amt){
    print("Name of player?\n")
    if(i == 1){
      gPlayers = Array(new Main.Player(scala.io.StdIn.readLine()))
    } else {
      gPlayers = gPlayers :+ new Main.Player(scala.io.StdIn.readLine())
    }
    print("Player: " + gPlayers.reverse.head.Name + " added\n")
    }
  gPlayers = scala.util.Random.shuffle(gPlayers.toList).toArray //shuffle players to random order
  }
  
  def dealToTable() = {
    require(gDeck.cards.length > 4)
    for(i <- Range(0,4)){
      if (i == 0){
        gTable = Array(gDeck.dealCard())
      }
      else{
        gTable = gTable :+ gDeck.dealCard()
      }
    }
  }
  
  def showTable() ={
    print("\n ========== TABLE CARDS ========== \n")
    var j = 0 
    for(i <- gTable){
      j +=1
      print(i.thisCard + "("+j+")")
    }
  }
  
  def newRound(): Unit = {
    round += 1
    gDeck = Deck
    gDeck.cards = gDeck.makeDeck()
    print(gDeck.cards.length)
    var dealer = gPlayers(0)
    print("\nRounds begins! Dealer is: " +dealer.Name+"\n")
    gPlayers = gPlayers.tail :+ gPlayers(0) //makes dealer last
    this.dealToTable
    for(i <- Range(0,4)){
      for(j <- gPlayers){
        j.addToHand(gDeck.dealCard)
      }
    }
  this.letsPlay
  }
  
  def letsPlay() = {
    var turns = 0
    while(NotOver){
      
      var player = gPlayers(turns)
      while(player.hand.length > 0){
        player = gPlayers(turns)
        print("\n" + player.Name + "'s turn\n")
        this.showTable
        turn(player)
        if(gDeck.cards.length > 0){
          player.addToHand(gDeck.dealCard())
        }
        if(turns < gPlayers.length - 1){
          turns += 1
        }
        else {
          turns = 0
        }
      }
      this.takeAll
      print()
  		print("		*** Scores after round " + round + " ***")
  		print()
  		new Main.Score(gPlayers)
      
      for(player <- gPlayers){
        player.collected = Array[Main.Card]()
      if(NotOver){
        this.newRound
      }
      }
    }
  }
  
  def turn(p: Main.Player): Unit = {
    p.showHand
    print("Which card do you wanna use?\n")
      var index: Int = scala.io.StdIn.readInt 
    
    if(index == 0){
      gTable = gTable :+ p.getCard(scala.io.StdIn.readInt)
      return
    }else if(index > p.hand.length){
      print("No such card")
      turn(p)
    }
    val card = p.getCard(index)
    collect(p, card)
    p.collected ++ Array(card)
    if(gTable.length == 0){
      p.score += 1
    }
  }
  
  def collect(p: Main.Player, in: Main.Card): Unit = {
    print("Which cards do you want to pick up?\n")
    var cardValue = 0
    var cardIndexes = Array(scala.io.StdIn.readLine())
    
    if(cardIndexes(0) == "0") { //return if you type wrong / can't use that card
        p.addToHand(in)
        turn(p)
      }
    var cheatArray = Array(0)
    for(i <- cardIndexes(0).split(",")){
      for(j <- i.split("")){
        if(cheatArray.contains(j.toInt)){
          print("========== NO CHEATING ==========")
          p.addToHand(in)
          turn(p)
        }
        else{
          cheatArray ++ Array(j.toInt)
        }
      }
    }
    if (cardIndexes(0).contains(",")){
      cardIndexes = cardIndexes(0).split(",")
    }
    for(j <- cardIndexes){
      cardValue = 0
      for(i <- j.split("")){
        cardValue += gTable(i.trim.toInt - 1).value
        }
      if(cardValue != in.handValue){
        print("Combination doesn't work")
        this.collect(p, in)
      }
    }
      var collCards = Array[Main.Card](new Card(15, 'G')) //Golden15 never to be used
      for(i <- cardIndexes){
        for(j <- i.split("")){
        collCards = collCards :+ gTable(j.toInt - 1)
        }
      } 
      for(j <- collCards){
        if(this.rmFromTable(j)._1)
          p.coll(j)
      }
      p.coll(in)
      lp = p
      if(gTable.length == 0){
        p.score += 1
        print("\n Table was cleared!!!\n")
      }
  }
  
  def takeAll(): Unit = {
    if(gTable.length == 0) return
    for(i <- gTable){
      lp.coll(rmFromTable(i)._2)
    }
  }
 }

