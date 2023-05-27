package Engines

import org.jpl7._

import java.io.PrintWriter

def eightQueens_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {

  if(input == "solve") {
    val queensList = solve(game_board)
    return autoSolveAction(game_board, queensList)
  }

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

def boundCheck(input: String) = {
  val inputPattern = """([1-8])([a-h])""".r

  input match {
    case inputPattern(row, col) => (row.toInt, col(0) - 'a' + 1, true)
    case _ => (0, 0, false)
  }
}

def solve(game_board: Array[Array[String]]): Array[String] = {
  val queries = "src/main/scala/solvers/EightQueens.pl"

  val connect = new Query("consult('solvers/EightQueens.pl')") // The queries

  connect.hasSolution

  val Qs = game_board.transpose.drop(1).map(col => if(col.contains("♕")) col.indexOf("♕") else "_").mkString("[", ", ", "]")

  val query = Query(s"Qs = $Qs, n_queens(8, Qs), labeling([], Qs)") // Prolog query to solve the 8 Queens problem
  if (query.hasSolution) {
    val solution = query.oneSolution() // Get the first solution
    val queens = solution.get("Qs") // Extract the solution for the Qs variable
    val queensList = queens.toTermArray.map(_.toString) // Convert the Prolog terms to strings
    queensList
  } else {
    println("No solution found.")
    val empty: Array[String] = Array.ofDim(8)
    empty
  }
}

def autoSolveAction(game_board: Array[Array[String]], solution: Array[String]): (Array[Array[String]], Boolean) = {
  if (solution(0) != null) {
    val updated_board = solution.zipWithIndex.foldLeft(game_board) { case (board, (row, col)) =>
      if (game_board(row.toInt)(col) != "♕") {
        val updated_row = board(row.toInt).updated(col + 1, "♕")
        board.updated(row.toInt, updated_row)
      } else {
        board
      }
    }
    (updated_board, true)
  } else {
    (game_board, false)
  }
}


def isFirstMove(gameBoard: Array[Array[String]]): Boolean = {
  !gameBoard.exists(row => row.exists(_.contains("♕")))
}

def isValidMove(row: Int, col: Int,
                isQueenInRowOrColumn: (Array[Array[String]], Int, Int) => Boolean,
                isQueenInDiagonal: (Array[Array[String]], Int, Int) => Boolean,
                game_board: Array[Array[String]]): Boolean = {
  (!isQueenInRowOrColumn(game_board, row, col) && !isQueenInDiagonal(game_board, row, col)) || game_board(row)(col) == "♕"
}

def isQueenPresentInDiagonals(game_board: Array[Array[String]],row: Int, col: Int): Boolean = {
  val diagonalPositions = (1 to 8).map(d => (row - d, col - d)) ++ (1 to 8).map(d => (row + d, col + d)) ++ (1 to 8).map(d => (row + d, col - d)) ++ (1 to 8).map(d => (row - d, col + d))
  diagonalPositions.exists { (r, c) =>
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
  game_board
    .foreach( row =>
      val displayRow = row.zipWithIndex.foldLeft("") { case (acc, (cell, j)) =>
        if (cell == "♕") {
          acc + (if (j == 1) " " else "") + cell + "  "
        } else {
          acc + cell
        }
      }
      println(displayRow)
    )

  if (isFirstMove(game_board))
    println("To solve enter 'solve'")
}

def eightQueens_initializer(): Array[Array[String]] = {
  val board: Array[Array[String]] = Array.ofDim[String](9, 9)

  val initialBoard = board.zipWithIndex.map { case (rowArr, rowIndex) =>
    rowArr.zipWithIndex.map { case (_, colIndex) =>
      if (rowIndex == 0) {
        if (colIndex == 0) "  " else s" ${('a' + colIndex - 1).toChar} "
      } else if (colIndex == 0 && rowIndex > 0) {
        s"$rowIndex"
      } else if ((rowIndex % 2 == 0 && colIndex % 2 == 0) || (rowIndex % 2 == 1 && colIndex % 2 == 1)) {
        if (colIndex == 1) " 🟧 " else "🟧 "
      } else {
        if (colIndex == 1) " ⬜ " else "⬜ "
      }
    }
  }
  initialBoard
}