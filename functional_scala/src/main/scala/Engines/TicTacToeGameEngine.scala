package Engines

def ticTacToe_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  input.length match {
    case 2 =>
    case _ => return (game_board, false)
  }
  input(0) match {
    case '1' | '2' | '3' =>
    case _ => return (game_board, false)
  }
  val (number, letter) = input.splitAt(1)
  val row = number.toInt
  val column = letter.toLowerCase()
  val validInput = !(column < "a" || column > "c"
                    || game_board(row - 1)(
    column match
      case "a" => 0
      case "b" => 1
      case "c" => 2
  ) != "🟩")
  validInput match
    case false => return (game_board, validInput)
    case true => game_board(row - 1)(
      column match
        case "a" => 0
        case "b" => 1
        case "c" => 2
    ) = if(player1Turn) "\u001b[31m" + "\u2009\u2009X\u2009\u2009" + "\u001B[0m" else "\u001b[34m" + "\u2009\u2009O\u2009\u2009" + "\u001B[0m"
  (game_board, validInput)
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