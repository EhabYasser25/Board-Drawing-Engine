package Engines

def sudoku_controller(gameBoard: Array[Array[String]], userInput: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  val valid = validateInput(gameBoard, userInput)
  if (valid) {
    val row = userInput(0).asDigit - 1
    val col = userInput(1) - 'a'
    val num = if (userInput.length == 4) userInput(3).toString else ""
    gameBoard(row)(col) = num
  }
  (gameBoard, valid)
}

def validateInput(gameBoard: Array[Array[String]], userInput: String): Boolean = {
  // to store true if the current operation is removing element
  val removeElement = userInput.length == 2

  // handle removing element
  val input = if (removeElement) userInput + "0" else userInput

  // handle shorter or longer input
  if (input.length != 4) return false

  // get data from input string
  val row = input(0).asDigit - 1
  val col = input(1) - 'a'
  val space = input(2)
  val num = input(3).asDigit

  // check boundaries
  if (row < 0 || row >= 9 || col < 0 || col >= 9 || num < 0 || num > 9) return false

  // check on the in-between space
  if (space != ' ') return false

  // handle removing element
  if (removeElement) {
    // check if it is initial element
    if (!gameBoard(row)(col).forall(_.isDigit)) return false

    // check in full cell
    if (gameBoard(row)(col).toInt == 0) return false

    // return true if element is not initial and the cell is full
    return true
  }

  // check empty cell
  if (gameBoard(row)(col).toInt != 0) return false

  // check on non-repetition of the num in its row or column
  if (gameBoard(row).contains(num.toString) || gameBoard.map(_(col)).contains(num.toString)) return false

  // check on non-repetition of the num in its square
  val r = (row / 3) * 3
  val c = (col / 3) * 3
  if (gameBoard.slice(r, r + 3).flatMap(_.slice(c, c + 3)).contains(num.toString)) return false

  // return true if passed all checks
  true
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
  def drawRow(row: Array[String], rowIndex: Int): String = {
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
  Array(
    Array("5i", "3i", "0i", "0i", "7i", "0i", "0i", "0i", "0i"),
    Array("6i", "0i", "0i", "1i", "9i", "5i", "0i", "0i", "0i"),
    Array("5i", "9i", "8i", "0i", "7i", "0i", "0i", "6i", "0i"),
    Array("8i", "0i", "0i", "0i", "6i", "0i", "0i", "0i", "3i"),
    Array("4i", "0i", "0i", "8i", "0i", "3i", "0i", "0i", "1i"),
    Array("7i", "0i", "0i", "0i", "2i", "0i", "0i", "0i", "6i"),
    Array("0i", "6i", "0i", "0i", "0i", "0i", "2i", "8i", "0i"),
    Array("0i", "0i", "0i", "4i", "1i", "9i", "0i", "0i", "5i"),
    Array("0i", "0i", "0i", "0i", "8i", "0i", "0i", "7i", "9i")
  )
}