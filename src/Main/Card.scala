package Main

/* Gives value for cards depending if it's in hand or on the table
 * also gives suit 
 */

<<<<<<< HEAD
class Card(val value: Int,  val gsuit: String) {
=======
class Card(val value: Int,  val gsuit: Char) {
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
  
  val tablevalue = value
  val suit = gsuit
  var point = 0
  var handValue = this.value
  
  if(this.value == 1){
    handValue = 14
    point = 1
  }
<<<<<<< HEAD
  if(this.value == 2 && this.suit == "S"){
    handValue =  15
    point = 1
  }
  if(this.value == 10 && this.suit == "D"){
=======
  if(this.value == 2 && this.suit == 'S'){
    handValue =  15
    point = 1
  }
  if(this.value == 10 && this.suit == 'D'){
>>>>>>> b70d4655f62ddc9ec5186d1589114c5a2be7a44d
    handValue = 16
    point = 2
  }
  def thisCard(): String =  { //Card info as string for other functions like showHand
    return (this.value).toString + this.suit
  }
}