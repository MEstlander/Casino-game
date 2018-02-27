package Main

/* Gives value for cards depending if it's in hand or on the table
 * also gives suit 
 */

class Card(val value: Int,  val gsuit: Char) {
  val tablevalue = value
  val suit = gsuit
  var point = 0
  var handValue = this.value
  if(this.value == 1){
    handValue = 14
    point = 1
  }
  if(this.value == 2 && this.suit == 'S'){
    handValue =  15
    point = 1
  }
  if(this.value == 10 && this.suit == 'D'){
    handValue = 16
    point = 2
  }
  def thisCard(): String =  {
    return (this.value).toString + this.suit
  }
}