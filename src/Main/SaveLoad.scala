package Main
import java.io._
import scala.io.Source

object SaveLoad {
  
  
  def Save(func: String) :Unit = {
    try{
      var save = new PrintWriter(new File("save.txt"))
      save.write("Deck")
      save.write(System.getProperty("line.separator"))
      for(i <- Game.gDeck.cards){
        save.write(i.value.toString + "," + i.suit)
        save.write(System.getProperty("line.separator"))
      }
      save.write("Table")
      save.write(System.getProperty("line.separator"))
      for(i <- Game.gTable){
        save.write(i.value.toString + "," + i.suit)
        save.write(System.getProperty("line.separator"))
      }
      save.write("Funct")
      save.write(System.getProperty("line.separator"))
      save.write(func)
      save.write(System.getProperty("line.separator"))
      save.write(Game.gPlayers.length.toString)
      save.write(System.getProperty("line.separator"))
      for(i <- Game.gPlayers){
        save.write("Players")
        save.write(System.getProperty("line.separator"))
        save.write(i.Name)
        save.write(System.getProperty("line.separator"))
        save.write("Hand")
        save.write(System.getProperty("line.separator"))
        for(j <- i.hand){
          save.write(j.value.toString + "," + j.suit)
          save.write(System.getProperty("line.separator"))
        }
        save.write("Collected")
        save.write(System.getProperty("line.separator"))
        for(j <- i.collected){
          save.write(j.value.toString + "," + j.suit)
          save.write(System.getProperty("line.separator"))
        }
        save.write("Score")
        save.write(System.getProperty("line.separator"))
        save.write(i.score.toString)
        save.write(System.getProperty("line.separator"))
      }
      save.write("Lp")
      save.write(System.getProperty("line.separator"))
      save.write(Game.lp.Name)
      save.write(System.getProperty("line.separator"))
      save.write("Round")
      save.write(System.getProperty("line.separator"))
      save.write(Game.round.toString)
      save.write(System.getProperty("line.separator"))
      save.close
    }
    catch{
      case _: Any => {
        print("Error while saving")
        Game.CheckInput(func)
      }
    }
    System.exit(1)
  }
  
  
  def Load() = {
    var phases : Array[String] = Array("Funct", "Deck", "Table", "Players", "Hand", "Collected", "Score", "Lp", "Round")
    var phase = ""
    var playerNumber = 0
    var funct = ""
    for(line <- Source.fromFile("save.txt").getLines())
      if(phases.contains(line)){
        phase = line
      }
      else{
        if(phase == "Deck"){
          Game.gDeck.addToDeck(new Card(line.split(",")(0).toInt, line.split(",")(1)))
        }
        if(phase == "Table"){
          Game.dealToTable(new Card(line.split(",")(0).toInt, line.split(",")(1)))
        }
        if(phase == "Players"){
          if (Game.gPlayers.isEmpty){
            Game.gPlayers = Array(new Player(line))
          }
          else{
            Game.gPlayers = Game.gPlayers :+ (new Player(line))
            playerNumber += 1
          }
        }
        if(phase == "Hand"){
          Game.gPlayers(playerNumber).addToHand(new Card(line.split(",")(0).toInt, line.split(",")(1)))
        }
        if(phase == "Collected"){
          Game.gPlayers(playerNumber).coll(new Card(line.split(",")(0).toInt, line.split(",")(1)))
        }
        if(phase == "Funct"){
          funct == line
        }
        if(phase == "Score") {
          Game.gPlayers(playerNumber).score = line.toInt
        }
        if(phase == "Lp"){
          for(i <- Game.gPlayers){
            if(i.Name == line){
              Game.lp = i
            }
          }
        }
        if(phase == "Round"){
          Game.round = line.toInt
        }
      }
      
    Game.CheckInput(funct)
  }
  
}
