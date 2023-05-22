package Engines

import scala.util.Random
import scala.util.Random._
import org.jpl7._

def sudoku_controller(gameBoard: Array[Array[String]], userInput: String, _player1Turn: Boolean) = {
  validateInput(gameBoard, userInput) match {
    case (action, true) => (action(), true)
    case (_, false) => (gameBoard, false)
  }
}

def validateInput(gameBoard: Array[Array[String]], userInput: String)= {
  val normalPattern = """([1-9])([a-i]) ([1-9])""".r
  val deletePattern = """([1-9])([a-i])""".r
  userInput match {
    case normalPattern(row, col, num) => checkNormal(gameBoard, row.toInt - 1, col(0) - 'a', num.toInt)
    case deletePattern(row, col) => checkDelete(gameBoard, row.toInt - 1, col(0) - 'a')
    case "solve" => checkSolve(gameBoard)
    case _ => (null, false)
  }
}

def checkNormal(gameBoard: Array[Array[String]], row: Int, col: Int, num: Int) = {
  (
    checkEmpty(gameBoard, row, col),
    checkRow(gameBoard, row, num),
    checkCol(gameBoard, col, num),
    checkBox(gameBoard, row, col, num)
  ) match {
    case (true, true, true, true) => (applyAction(gameBoard, row, col, s"$num "), true)
    case (a, b, c, d) => (null, false)
  }
}

def checkEmpty(gameBoard: Array[Array[String]], row: Int, col: Int) = gameBoard(row)(col)(0) == '0'

def checkRow(gameBoard: Array[Array[String]], row: Int, num: Int) = {
  !gameBoard(row)
    .map(_(0).toString())
    .contains(num.toString)
}

def checkCol(gameBoard: Array[Array[String]], col: Int, num: Int) = {
  !gameBoard
    .map(_(col))
    .map(_(0).toString())
    .contains(num.toString)
}

def checkBox(gameBoard: Array[Array[String]], row: Int, col: Int, num: Int) = {
  val r = (row / 3) * 3
  val c = (col / 3) * 3
  !gameBoard
    .slice(r, r + 3)
    .flatMap(_.slice(c, c + 3))
    .map(_(0).toString)
    .contains(num.toString)
}

def checkDelete(gameBoard: Array[Array[String]], row: Int, col: Int) = {
  (
    checkFull(gameBoard, row, col),
    checkInitial(gameBoard, row, col)
  ) match {
    case (true, false) => (applyAction(gameBoard, row, col, "0i"), true)
    case _ => (null, false)
  }
}

def checkFull(gameBoard: Array[Array[String]], row: Int, col: Int) = gameBoard(row)(col)(0) != '0'

def checkInitial(gameBoard: Array[Array[String]], row: Int, col: Int) = gameBoard(row)(col).contains("i")

def checkSolve(gameBoard: Array[Array[String]]): (() => Array[Array[String]], Boolean) = {
  checkInitialBoard(gameBoard) match {
    case board if board != null =>
      val goal = board.map(_.mkString("[", ", ", "]")).mkString("[", ",", "]").replaceAll("0", "_")
      val SudokuQ = new Query("consult('src/main/scala/solvers/sudoku.pl')")
      SudokuQ.hasSolution
      val Solver = new Query(s"Rows = $goal, sudoku(Rows), maplist(label, Rows), maplist(portray_clause, Rows)")
      if(!Solver.hasSolution) return (null, false)
      val Soln = Solver.oneSolution().get("Rows")
      val tmpBoard = Soln.toString.stripPrefix("[[").stripSuffix("]]").split("], \\[").map(_.split(", "))
      val newBoard = tmpBoard.zipWithIndex.map { case (elem, index) =>
        elem.zipWithIndex.map { case (elm, indx) =>
          elm.concat(if(gameBoard(index)(indx)(0).equals('0')) "0" else gameBoard(index)(indx)(1).toString)
        }
      }
      (applyAction(newBoard, 0, 0, newBoard(0)(0)), true)
    case _ => (null, false)
  }
}

def checkInitialBoard(gameBoard: Array[Array[String]]) = if (gameBoard.forall(_.forall(_.contains("i")))) gameBoard.map(_.map(_.replace("i", ""))) else null

def applyAction(gameBoard: Array[Array[String]], row: Int, col: Int, cell: String) = () => gameBoard.updated(row, gameBoard(row).updated(col, cell))

def sudoku_drawer(gameBoard: Array[Array[String]]): Unit = {
  val redColor = "\u001b[31m"
  val resetColor = "\u001b[0m"
  val topLetters = "    a   b   c   d   e   f   g   h   i"
  val boldTopHorizontalLine = s"  $redColor┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓$resetColor"
  val boldMiddleHorizontalLine = s"  $redColor┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫$resetColor"
  val boldBottomHorizontalLine = s"  $redColor┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛$resetColor"
  val horizontalLine = s"  $redColor┃$resetColor───┼───┼───$redColor┃$resetColor───┼───┼───$redColor┃$resetColor───┼───┼───$redColor┃$resetColor"
  val boldVerticalLine = s"$redColor┃$resetColor"
  val verticalLine = "|"

  // helper function to draw a single row
  def drawRow(row: Array[String], rowIndex: Int) = {
    val rowString = row
      .map(cell => cell.replace("0", " ").updated(1, ' '))
      .grouped(3)
      .map(_.mkString(s"$verticalLine "))
      .mkString(s"$boldVerticalLine ", s"$boldVerticalLine ", s"$boldVerticalLine")

    val horizontalSeparator = if((rowIndex + 1) % 3 == 0) boldMiddleHorizontalLine else horizontalLine

    (rowIndex + 1)
      .toString
      .concat(s" $rowString\n")
      .concat(if(rowIndex < 8) horizontalSeparator else "")
  }

  // draw the board
  val boardString = gameBoard
    .zipWithIndex
    .map((row, rowIndex) => drawRow(row, rowIndex))
    .mkString(s"$topLetters\n$boldTopHorizontalLine\n", "\n", boldBottomHorizontalLine)

  println(boardString)
}

def sudoku_initializer(): Array[Array[String]] = {
  val random = new Random()

  val grid = Array.fill(9, 9)("0")
  val exist = Array.fill(3, 9, 10)(false)

  def fillDiagonalBox(row: Int, col: Int): Unit = {
    val numbers = random.shuffle(1 to 9)
    val cells = (row to row + 2).flatMap(i => (col to col + 2).filter(j => grid(i)(j) == "0").map(j =>(i, j))).toList

    cells.zip(numbers).foreach {
      case ((i, j), num) =>
        grid(i)(j) = s"$num"
        exist(0)(i)(num) = true
        exist(1)(j)(num) = true
        exist(2)(i / 3 * 3 + j / 3)(num) = true
    }
  }

  def fillRecursion(cell: Int): Boolean = {
    if (cell == 81) return true
    if ((cell / 9 / 3 * 3 + cell % 9 / 3) % 4 == 0) return fillRecursion(cell + 1)

    val numbers = random.shuffle(1 to 9)
    val solved = numbers.exists { i =>
      if (!exist(0)(cell / 9)(i) && !exist(1)(cell % 9)(i) && !exist(2)(cell / 9 / 3 * 3 + cell % 9 / 3)(i)) {
        exist(0)(cell / 9)(i) = true
        exist(1)(cell % 9)(i) = true
        exist(2)(cell / 9 / 3 * 3 + cell % 9 / 3)(i) = true

        val isSolved = fillRecursion(cell + 1)
        if (isSolved) {
          grid(cell / 9)(cell % 9) = i.toString
          true
        } else {
          exist(0)(cell / 9)(i) = false
          exist(1)(cell % 9)(i) = false
          exist(2)(cell / 9 / 3 * 3 + cell % 9 / 3)(i) = false
          false
        }
      } else {
        false
      }
    }

    solved
  }

  def deleteRandomCells(emptyCells: Int): Unit = {
    val cells = (for {
      _ <- 1 to emptyCells
      row = random.nextInt(9)
      col = random.nextInt(9)
      if grid(row)(col) != "0"
    } yield (row, col)).toList

    cells.foreach {
      case (row, col) => grid(row)(col) = "0"
    }
  }

  (0 to 8 by 3).foreach(i => fillDiagonalBox(i, i))
  fillRecursion(0)
  deleteRandomCells(70)
  grid.map(_.map(cell => cell.concat("i")))
}