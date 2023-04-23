
object TicTacToeController {
  def Controller(board: Array[Array[Char]], input: String): Option[Array[Array[Char]]] = {
    println(s"you entered: $input")
    println("TicTacToe is controlled here")
    val newBoard: Array[Array[Char]] = board
    if(input.equals("valid move")) {
      Some(newBoard)
    } else {
      None
    }
  }
}
