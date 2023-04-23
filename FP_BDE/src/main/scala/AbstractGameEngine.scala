object AbstractGameEngine {
  def playGame(drawer: Board => Unit,
               controller: (Board, String) => Option[Board],
               board: Board): Unit = {
    var currentBoard = board
    var currentPlayer = 1

    while (true) {
      drawer(currentBoard) // Draw the board

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
  case class Board(grid: Vector[Vector[Int]])
}
