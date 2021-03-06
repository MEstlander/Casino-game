package Main

object Game extends App {
  var gPlayers = Array[Main.Player]()
  var gDeck = Deck
  var gTable = Array[Main.Card]()
  var round = 0
  var lp = new Main.Player("dumbo")
  var NotOver = true
  var turns = 0
  var collCard = new Main.Card(15,"G") //Golden 15 used to avoid null pointer exceptio
  var loaded = false
  
		print("		================================\n")
		print("		Welcome to the Casino Card Game!\n")
		print("		================================\n")
		print("\nINFO\n")
		print("\nYour cards and the ones on the table are indexed 1,2,3,4... if you cannot \n" +
				"collect anything, type '0' and you will be able to place a card on the table\n" +
				"To collect cards just type the indexes after each other with a '+' between \n"+
				"indexes, to collect separate card combinations add ',' between the combinations.\n")
		print("\nRULES\n")
		print("\nYou can only use one card from your hand each turn, you want to collect\n" +
				 "card combinations from the table that adds up to your cards value.\n" +
				 "10D is worth 2p and is valued at 16 in the hand, 2S is worth 1p and is\n" +
				 "valued at 15 in the hand. Aces (1's) are worth 1p and are valued 14 in the\n" +
				 "hand. For clearing the table you get 1p, player with the most spades at\n" +
				 "the end of a round gets 2p and for most cards 1p.\n" +
				 "First player to 16p wins!\n")
		print("\nYou can save the game by typing 'SAVE', if you wish to exit the game,\n" + 
				"type 'QUIT'.")
		
		
	print("\nHow many players?\n")	
	this.players(CheckInput("players").toInt)
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
	  require(amt > 1)
	  require(amt < 13)
  for( i <- 1 to amt){
    
	  print("Name of player?\n")
    addPlayer(CheckInput("names"))
    
    print("\nBot?(Y for yes) \n")
    if(CheckInput("names") == "Y")
      gPlayers(i - 1 ).Bot = true
    }
  gPlayers = scala.util.Random.shuffle(gPlayers.toList).toArray//shuffle players to random order
  }
	def addPlayer(a: String){
	  if(gPlayers.isEmpty){
	    gPlayers = Array(new Main.Player(a))
    } else {
      gPlayers = gPlayers :+ new Main.Player(a)
    }
    print("Player: " + a + " added\n")
  }
  
  def dealToTable(a: Main.Card) = {
    if (gTable.length == 0){
      gTable = Array(a)
    }
    else{
      gTable = gTable :+ a
    }
  }
  
  def showTable() ={
    print("\n ========== TABLE CARDS ========== \n\n")
    var j = 0
    var k = 1
    for(i <- gTable){
      j +=1
      print(i.thisCard + "\t")
    }
    print("\n")
    for(i <- gTable) {
      print(k + "\t")
      k += 1
    }
  }
  
  def newRound(): Unit = {
    round += 1
    gDeck = Deck
    gDeck.cards = gDeck.makeDeck()
    var dealer = gPlayers(0)
    print("\nRounds begins! Dealer is: " +dealer.Name+"\n")
    gPlayers = gPlayers.tail :+ gPlayers(0) //makes dealer last
    for(i <- Array(1,2,3,4)){
      dealToTable(gDeck.dealCard)
      for(j <- gPlayers){
        j.addToHand(gDeck.dealCard)
      }
    }
  this.letsPlay
  }
  
  def letsPlay() = {
    turns = 0
    while(NotOver){
      
      var player = gPlayers(turns)
      while(gPlayers(gPlayers.length - 1).hand.length > 0){ //checks if the last person has no cards
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
  		print("\n ========== Scores after round " + round + " ========== \n")
  		new Main.Score(gPlayers).count
      
      for(player <- gPlayers){ //Clears everyones collected cards
        player.collected = Array[Main.Card]()
      if(NotOver){
        this.newRound
      }
      }
    }
  }
  
  def turn(p: Main.Player): Unit = {
    if (p.hand.length == 0){
      for(i <- gPlayers)
        print(i.hand.length)
    }
    p.showHand
    print("Which card do you wanna use?\n")
    print(gDeck.cards.length.toString)
    var index = 0
    if(p.Bot){
      index = Main.BotMove.useCardMove(p, gTable)
    } else{
      index = CheckInput("turn").toInt 
    }
    if(index == 0){
      if(p.Bot){
        gTable = gTable :+ p.getCard(scala.math.max(p.hand.length - 1, 1))
        return
      } else{
        print("Which card do you want to give to the table?\n")
        gTable = gTable :+ p.getCard(CheckInput("turn2").toInt)
        return
      }
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
    collCard = in
    var cardIndexes = Array("hi")
    var cardValue = 0
    if(p.Bot){
      cardIndexes = Array(Main.BotMove.collCards(gTable))
    } else{
      cardIndexes = Array(CheckInput("collect"))
    }
    if(cardIndexes(0) == "0") { //return if you type wrong / can't use that card
        p.addToHand(in)
        turn(p)
      }
    var cheatArray = Array(-1)
    for(i <- cardIndexes(0).split(",")){ //used to check that players dont try to pick up the same card twice
      for(j <- i.split("\\+")){
        if(cheatArray.contains(j.toInt)){
          print(" ========== NO CHEATING ==========")
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
      for(i <- j.split("\\+")){
        cardValue += gTable(i.trim.toInt - 1).value
        }
      if(cardValue != in.handValue){
        print("Combination doesn't work\n")
        this.collect(p, in)
      }
    }
      var collCards = Array[Main.Card](new Card(15, "G")) //Golden15 never to be used (avoids the null pointer exception)
      for(i <- cardIndexes){
        for(j <- i.split("\\+")){
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
  //When taking input from players this checks it's correct
  //if it isn't it prompts the user to input again
  //also used for checking if players want to save or not
  def CheckInput(func: String): String = {
    var input = scala.io.StdIn.readLine()
    if(input.toLowerCase == "save"){
      if(func == "collect"){
        gPlayers(turns).addToHand(collCard) //adds the card back to player before saving
      }
      SaveLoad.Save(func, turns)
    }
    if(input.toLowerCase == "load"){
      if(!loaded){
        SaveLoad.Load()
      } else {
        print("\n You already loaded once. Restart if you want to load again.\n" )
        CheckInput(func)
      }
    }
    if(input.toLowerCase == "quit"){
      System.exit(1)
    }
    if(func == "players"){
      try{
        var nmbr = input.toInt
        if(nmbr > 1 && nmbr <13){
          return input
        }
        else{
        print("Please enter a number between 2 and 12\n")
        CheckInput(func)
        }
      
      }
      catch {
        case _: Any => {
          print("Please enter a number between 2 and 12\n")
          CheckInput(func)
        }
        }
    } else if(func == "names"){
      if(gPlayers.isEmpty){
        return input
      }
      else{
        for(i <- Range(0,gPlayers.length)){
          if(gPlayers(i).Name == input){
            print("Name already picked\n")
            CheckInput(func)
          }
        }
            
        return input
      }
    } else if(func == "turn"){
      try{
        var nmbr = input.toInt
        if(nmbr >= 0){
          return input
        }
        else{
          print("Please give a positive number\n")
          CheckInput(func)
        }
      }
      catch {
        case _: Any => {
          print("Please give an integrer.\n")
          CheckInput(func)
        }
      }
    
    } else if(func == "turn2"){
      try{
        var nmbr = input.toInt
        if(nmbr > 0){
          return input
        }
        else{
          print("Please give a positive number\n")
          CheckInput(func)
        }
      }
      catch {
        case _: Any => {
          print("Please give an integrer.\n")
          CheckInput(func)
        }
      }
    
    }else if(func == "collect"){
      try{
        var cards = input.split(",")
        for(i <- cards){
          for(j <- i.split("\\+")){
            require(j.toInt >= 0)
          }
        }
        return input
      }
      catch{
        case _: Any => {
           print("Input was invalid. Try again!\n")
           CheckInput(func)
        }
      }
    }
    else {
      print("This shouldn't have happend... Awkward...\n")
      input
    }
   }
}

