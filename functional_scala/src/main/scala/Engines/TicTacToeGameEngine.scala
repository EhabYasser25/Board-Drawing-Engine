package Engines

def ticTacToe_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  val normal_input = """([1-3])([a-c])""".r
  input match
    case normal_input(row, col) =>
    case _ => return (game_board, false)
  val (number, letter) = input.splitAt(1)
  val row = number.toInt
  val col = letter.toLowerCase() match
    case "a" => 0
    case "b" => 1
    case "c" => 2
    case _ => -1
  if(col == -1) return (game_board, false)
  val validInput = !(game_board(row - 1)(col) != "🟩")
  if(!validInput) return (game_board, validInput)
  val board: Array[Array[String]] = game_board.zipWithIndex.map {
    case (_row, rowIndex) =>
      if(rowIndex == row - 1) _row.updated(col, if(player1Turn) "\u001b[31m" + "\u2009\u2009X\u2009\u2009" + "\u001B[0m" else "\u001b[34m" + "\u2009\u2009O\u2009\u2009" + "\u001B[0m")
      else _row
  }
  (board, validInput)
}

def ticTacToe_drawer(game_board: Array[Array[String]]): Unit = {
  val rows = game_board.zipWithIndex.map {
    case (row, index) =>
      val rowString = row.zipWithIndex.map {
        case (cell, 0) => s"${index + 1} $cell"
        case (cell, _) => s"$cell"
      }.mkString(" ")
      s"$rowString"
  }

  val columnLabels = "   a  b  c"

  (rows :+ columnLabels).foreach(println)
}

def ticTacToe_initializer(): Array[Array[String]] = {
  Array.fill[String](3,3)("🟩")
}