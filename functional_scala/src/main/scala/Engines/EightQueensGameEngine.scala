package Engines

import org.jpl7.{Atom, Query, Variable}

import java.io.PrintWriter

def eightQueens_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {

  if(input == "solve" && isFirstMove(game_board))
    return solve()

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

def solve(): (Array[Array[String]], Boolean) = { // TODO Int --> String
  val ipPath = "D:/CSED/semester4/Paradigms/Board-Drawing-Engine/functional_scala/src/main/scala/solvers/board"
  val opPath = "D:/CSED/semester4/Paradigms/Board-Drawing-Engine/functional_scala/src/main/scala/solvers/solution"
  val queries = "D:/CSED/semester4/Paradigms/Board-Drawing-Engine/functional_scala/src/main/scala/solvers/EightQueens.pl"

  val connect = new Query(s"consult('$queries')") // The queries

  val sol = connect.hasSolution // for debugging

  val board: Array[Array[Int]] = Array.ofDim[Int](8, 8) // initialize board

  printBoardToFile(ipPath, board) // print initial board

  // Create a Prolog term for the file path and board variable
  val filePathTerm = new Atom(s"$ipPath")
  val boardTerm = new Variable("Board")

  // Create a Prolog query to call the read_board predicate
  val queryReadBoard = new Query("read_board", Array(filePathTerm, boardTerm))

  // Execute the query
  val resultReadBoard = queryReadBoard.hasSolution

  if (resultReadBoard) {
    val solution = queryReadBoard.oneSolution().get("Board")
    println(s"Board read successfully: $solution")
  } else {
    println("Failed to read the board.")
  }

//  val solver = new Query("solve(board)") // Solve the puzzle
//
//  val prologWriteFile = new Query(s"save_solution('$opPath')") // write to file
//
  val solution = convertIntBoardToString(board)

  (solution, true)
}

def convertIntBoardToString(board: Array[Array[Int]]) : Array[Array[String]] = { // TODO Fix
  val white = "\u001B[37m" // White color
  val reset = "\u001B[0m"

  val initialBoard = board.zipWithIndex.map { case (rowArr, rowIndex) =>
    rowArr.zipWithIndex.map { case (_, colIndex) =>
      if (rowIndex == 0) {
        if (colIndex == 0) "  " else s" $white${('a' + colIndex - 1).toChar}$reset "
      } else if(board(rowIndex)(colIndex) == 1) { "♕" }
      else if (colIndex == 0 && rowIndex > 0) {
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

def printBoardToFile(filePath: String, board: Array[Array[Int]]): Unit = { // DONE
  val writer = new PrintWriter(filePath)
  try {
    for (row <- board) {
      val rowString = row.map(_.toString).mkString(" ")
      writer.println(rowString)
    }
  } finally {
    writer.close()
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

  if (isFirstMove(game_board))
    println("To solve enter 'solve'")
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
