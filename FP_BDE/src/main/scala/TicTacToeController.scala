import AbstractGameEngine.Board

object TicTacToeController {
  def Controller(board: Board, input: String): Option[Board] = {
    println(s"you entered: $input")
    println("TicTacToe is controlled here")
    val newBoard: Board = board
    if(input.equals("valid move")) {
      Some(newBoard)
    } else {
      None
    }
  }
}
