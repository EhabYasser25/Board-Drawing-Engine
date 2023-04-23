import scala.swing.MainFrame

object AbstractGameEngine extends App {
  var currentBoard: Array[Array[Char]] = null
  var currentPlayer = 1
  def playGame(game: String,

               controller: (Array[Array[Char]], String) => Option[Array[Array[Char]]],
               board: Array[Array[Char]]): Unit = {

    currentBoard = board
    while (true) {
      // drawer(currentBoard, currentPlayer)
      game match {
        case "TicTacToe" => TicTacToeDrawer.main(Array.empty)
        case _ =>
      } // Draw the board
      print(s"Player $currentPlayer, enter your move: ")
      val input = scala.io.StdIn.readLine()

      val updatedBoard = controller(currentBoard, input) // Validate and update move
      updatedBoard match {
        case Some(newBoard) =>
          currentBoard = newBoard
          currentPlayer = (currentPlayer % 2) + 1 // Alternate players
        case None =>
          println("Invalid move, please try again.")
      }
    }
  }
}
