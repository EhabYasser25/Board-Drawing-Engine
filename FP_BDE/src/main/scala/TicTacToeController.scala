import AbstractGameEngine._

// Define the Tic Tac Toe game controller
object TicTacToeController {

  // Define the controller function for Tic Tac Toe
  def Controller(board: Array[Array[Char]], input: String, currentPlayer: Int): Array[Array[Char]] = {

    // Initialize a variable for checking the validity of player input
    board(19)(19) = 0

    input match {
      // Handle the "reset" input by clearing the board and resetting the player
      case "reset" =>
        for(i <- 0 until 3 ; j <- 0 until 3) {
          board(i)(j) = '\u0000'
        }
        board

      // Handle empty input by returning the current board
      case "" =>
        board

      // Handle player input by updating the board
      case _ =>
        // Split the input into row and column parts
        val parts = input.split(" ").map(_.trim)
        val rowLetter = parts.lift(0)
        val colLetter = parts.lift(1)

        // Check that both row and column parts exist
        (rowLetter, colLetter) match {
          case (Some(row), Some(col)) =>
          case _ => board(19)(19) = 1; return board
        }

        // Convert the column letter to an integer index
        val col = colLetter.getOrElse("").toLowerCase match {
          case "a" => 0
          case "b" => 1
          case "c" => 2
          case _ => board(19)(19) = 1; return board
        }

        // Convert the row number to an integer index
        val row = rowLetter.getOrElse("").toInt - 1

        // Check that the row index is within bounds
        if (row < 0 || row > 2) {
          board(19)(19) = 1
          return board
        }

        // Update the board with the player's move
        board(row)(col) match {
          // If the cell is empty, update it with the player's symbol and alternate turns
          case '\u0000' =>
            currentPlayer match {
              case 1 => board(row)(col) = 'X'
              case 2 => board(row)(col) = 'O'
              case _ => board(19)(19) = 1;
            }
            board(19)(19) = 0

          // If the cell is not empty, mark the move as invalid
          case _ =>
            board(19)(19) = 1
        }
        board
    }
  }
}
