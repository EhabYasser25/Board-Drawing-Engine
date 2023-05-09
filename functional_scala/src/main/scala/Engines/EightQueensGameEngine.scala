package Engines

def eightQueens_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {

  boundCheck(input) match {
    case (row, col, true) => if isValidMove(row, col,
                                            isQueenPresentInRowOrColumn,
                                            isQueenPresentInDiagonals,
                                            game_board)
                              then (modifyBoard(game_board, row, col), true)
                              else (game_board, false)
    case(_, _, false) =>  (game_board, false)
  }

}

def boundCheck(input: String): (Int, Int, Boolean) = {
  val row = input.charAt(0).toString.toInt
  val col = input.charAt(1).toLower - 'a' + 1
  (row, col, row >= 1 && row <= 8 && col >= 1 && col <= 8)
}

def isValidMove(row: Int, col: Int,
                isQueenInRowOrColumn: (Array[Array[String]], Int, Int) => Boolean,
                isQueenInDiagonal: (Array[Array[String]], Int, Int) => Boolean,
                game_board: Array[Array[String]]): Boolean = {
  (!isQueenInRowOrColumn(game_board, row, col) && !isQueenInDiagonal(game_board, row, col)) || game_board(row)(col) == "♕"
}

def isQueenPresentInDiagonals(game_board: Array[Array[String]],row: Int, col: Int): Boolean = {
  val diagonalPositions = (1 to 8).map(d => (row - d, col - d)) ++ (1 to 8).map(d => (row + d, col + d))
  diagonalPositions.exists { case (r, c) =>
    r >= 1 && r <= 8 && c >= 1 && c <= 8 && game_board(r)(c) == "♕"
  }
}

def isQueenPresentInRowOrColumn(game_board: Array[Array[String]], row: Int, col: Int): Boolean = {
  game_board(row).contains("♕") || game_board.exists(row => row(col) == "♕")
}

def modifyBoard(game_board: Array[Array[String]], row: Int, col: Int): Array[Array[String]] = {
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

def eightQueens_drawer(game_board: Array[Array[String]]): Unit = {
  val white = "\u001B[30m"
  val reset = "\u001B[0m"

  game_board
  .foreach( row =>
    val displayRow = row.zipWithIndex.foldLeft(white) { case (acc, (cell, j)) =>
      if (cell == "♕") {
        acc + (if (j == 1) " " else "") + cell + "  "
      } else {
        acc + cell
      }
    } + reset
    println(displayRow)
  )

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
