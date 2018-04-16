package Main
import java.time._
/*
 * Extremely simple bot
 */
object BotMove {
  var collSum = 0
  def useCardMove(player: Main.Player, table: Array[Main.Card]): Int = {
    Thread.sleep(500L)
    player.hand = player.hand.sortBy(_.handValue) //sorts so it tries to use highest value cards first
    val tableIndexs = table.map(a => a.value).zipWithIndex
    val cardsIndex = player.hand.map(a => a.handValue).zipWithIndex
    var tableSumms = Array[Int]()
    for(x <- tableIndexs){
      for(j <- tableIndexs.drop(x._2 + 1)){
        if(tableSumms.length == 0){
          tableSumms = Array(x._1+j._1)
        }
        else{
          tableSumms :+ (x._1 + j._1)
        }
      }
    }
    for(y <- cardsIndex){
      if( tableSumms.exists(_ == y._1) ){
        collSum = y._1
        return y._2 + 1
      }
      if ( tableIndexs.exists(_._1 == y._1)){
        collSum = y._1
        return y._2 + 1
      }
    }
    collSum = 0
    0
  }
  def collCards(table: Array[Main.Card]): String = {
    Thread.sleep(500L)
    val tableIndexs = table.map(a => a.value).zipWithIndex
    for(x <- tableIndexs){
      for(j <- tableIndexs.drop(x._2 + 1)){
        if(x._1 + j._1 == collSum) return (x._2 + 1).toString + '+' + (j._2 + 1)
      }
      if(x._1 == collSum) return (x._2 + 1).toString
    }
    "0"
  }
    
}