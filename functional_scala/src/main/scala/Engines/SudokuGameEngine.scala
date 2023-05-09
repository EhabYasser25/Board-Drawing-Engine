package Engines

def sudoku_controller(gameBoard: Array[Array[String]], userInput: String, _player1Turn: Boolean) = {
  validateInput(gameBoard, userInput) match {
    case (action, true) => (action(gameBoard), true)
    case (_, false) => (gameBoard, false)
  }
}

def validateInput(gameBoard: Array[Array[String]], userInput: String)= {
  val normalPattern = """([1-9])([a-i]) ([0-9])""".r
  val deletePattern = """([1-9])([a-i])""".r
  userInput match {
    case normalPattern(row, col, num) => checkNormal(gameBoard, row.toInt - 1, col(0) - 'a', num.toInt)
    case deletePattern(row, col) => checkDelete(gameBoard, row.toInt - 1, col(0) - 'a')
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
    case (true, true, true, true) => (applyAction(row, col, num), true)
    case _ => (null, false)
  }
}

def checkEmpty(gameBoard: Array[Array[String]], row: Int, col: Int) = gameBoard(row)(col)(0) == '0'

def checkRow(gameBoard: Array[Array[String]], row: Int, num: Int) = {
  gameBoard(row)
  .map(_(0).toString())
  .contains(num.toString)
  .unary_!
}

def checkCol(gameBoard: Array[Array[String]], col: Int, num: Int) = {
  gameBoard
  .map(_(col))
  .map(_(0).toString())
  .contains(num.toString)
  .unary_!
}

def checkBox(gameBoard: Array[Array[String]], row: Int, col: Int, num: Int) = {
  val r = (row / 3) * 3
  val c = (col / 3) * 3
  gameBoard
  .slice(r, r + 3)
  .flatMap(_.slice(c, c + 3))
  .map(_(0).toString)
  .contains(num.toString)
  .unary_!
}

def checkDelete(gameBoard: Array[Array[String]], row: Int, col: Int) = {
  checkFull(gameBoard, row, col) match {
    case true => (applyAction(row, col, 0), true)
    case _ => (null, false)
  }
}

def checkFull(gameBoard: Array[Array[String]], row: Int, col: Int) = gameBoard(row)(col)(0) != '0'

def applyAction(row: Int, col: Int, num: Int) = {
  (gameBoard: Array[Array[String]]) =>
    gameBoard(row)(col) = s"${num}e"
    gameBoard
}

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

def sudoku_initializer() = {
  Array(
    Array("5i", "3i", "0i", "0i", "7i", "0i", "0i", "0i", "0i"),
    Array("6i", "0i", "0i", "1i", "9i", "5i", "0i", "0i", "0i"),
    Array("0i", "9i", "8i", "0i", "0i", "0i", "0i", "6i", "0i"),
    Array("8i", "0i", "0i", "0i", "6i", "0i", "0i", "0i", "3i"),
    Array("4i", "0i", "0i", "8i", "0i", "3i", "0i", "0i", "1i"),
    Array("7i", "0i", "0i", "0i", "2i", "0i", "0i", "0i", "6i"),
    Array("0i", "6i", "0i", "0i", "0i", "0i", "2i", "8i", "0i"),
    Array("0i", "0i", "0i", "4i", "1i", "9i", "0i", "0i", "5i"),
    Array("0i", "0i", "0i", "0i", "8i", "0i", "0i", "7i", "9i")
  )
}