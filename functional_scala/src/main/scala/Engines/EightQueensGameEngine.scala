package Engines

def eightQueens_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {

  val row = input.charAt(0).toString.toInt
  val col = input.charAt(1).toLower - 'a' + 1

  def isValidMove: Boolean = {
    if row >= 1 && row <= 8 && col >= 1 && col <= 8 then {
      def isQueenPresentInRowOrColumn: Boolean = {
        game_board(row).contains("♕") || game_board.exists(row => row(col) == "♕")
      }

      def isQueenPresentInDiagonals: Boolean = {
        val diagonalPositions = (1 to 8).map(d => (row - d, col - d)) ++ (1 to 8).map(d => (row + d, col + d))
        diagonalPositions.exists { case (r, c) =>
          r >= 1 && r <= 8 && c >= 1 && c <= 8 && game_board(r)(c) == "♕"
        }
      }

      (!isQueenPresentInRowOrColumn && !isQueenPresentInDiagonals) || game_board(row)(col) == "♕"
    } else
      false
  }

  def modifyBoard: Array[Array[String]] = {
    val modifiedBoard = game_board.zipWithIndex.map { case (rowArr, rowIndex) =>
      rowArr.zipWithIndex.map { case (cell, colIndex) =>
        if (rowIndex == row && colIndex == col) {
          if (cell == "♕") {
            if (colIndex == 1) {
              if (game_board(rowIndex)(colIndex + 1) == "🟧 ") " ⬜ "
              else " 🟧 "
            } else {
              if (game_board(rowIndex)(colIndex - 1) == "🟧 ") "⬜ "
              else "🟧 "
            }
          } else "♕"
        } else cell
      }
    }

    modifiedBoard
  }

  if isValidMove then
    (modifyBoard, true)
  else
    (game_board, false)

}

def eightQueens_drawer(game_board: Array[Array[String]]): Unit = {
  val white = "\u001B[30m"
  val reset = "\u001B[0m"

  game_board.zipWithIndex.foreach { case (row, i) =>
    val displayRow = row.zipWithIndex.foldLeft(white) { case (acc, (cell, j)) =>
      if (cell == "♕") {
        acc + (if (j == 1) " " else "") + cell + "  "
      } else {
        acc + cell
      }
    } + reset
    println(displayRow)
  }

}

def eightQueens_initializer(): Array[Array[String]] = {
  val board: Array[Array[String]] = Array.ofDim[String](9, 9)
  val white = "\u001B[37m" // White color
  val reset = "\u001B[0m"

  val initialBoard = board.zipWithIndex.map { case (rowArr, rowIndex) =>
    rowArr.zipWithIndex.map { case (_, colIndex) =>
      if (rowIndex == 0) {
        if (colIndex == 0) "  " else s" $white${('a' + colIndex - 1).toChar}$reset "
      } else if (colIndex == 0 && rowIndex > 0) {
        s"$white$rowIndex$reset"
      } else if ((rowIndex % 2 == 0 && colIndex % 2 == 0) || (rowIndex % 2 == 1 && colIndex % 2 == 1)) {
        if (colIndex == 1) " 🟧 " else "🟧 "
      } else {
        if (colIndex == 1) " ⬜ " else "⬜ "
      }
    }
  }
  initialBoard
}
