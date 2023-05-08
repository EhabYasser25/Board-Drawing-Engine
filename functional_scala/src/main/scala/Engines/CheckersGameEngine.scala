package Engines

def checkers_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  input.length match {
    case 5 =>
    case _ => return (game_board, false)
  }
  input(0) | input(3) match {
    case '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' =>
    case _ => return (game_board, false)
  }
  input(1) | input(4) match {
    case 'a' | 'b' | 'c' | 'd' | 'e' | 'f' | 'g' | 'h' =>
    case _ => return (game_board, false)
  }
  input(2) match{
    case ' ' =>
    case _ => return (game_board, false)
  }
  val from_row = 8 - Integer.parseInt(input(0).toString)
  val from_col_letter = input(1)
  val to_row = 8 - Integer.parseInt(input(3).toString)
  val to_col_letter = input(4)
  val from_col = from_col_letter - 'a'
  val to_col = to_col_letter - 'a'
  val mid_row = (from_row + to_row) / 2
  val mid_col = (from_col + to_col) / 2
  val validInput = !(
    game_board(from_row)(from_col) == "🟥" ||
    game_board(from_row)(from_col) == "🟨" ||
    game_board(to_row)(to_col) != "🟨" ||
    game_board(from_row)(from_col) == "⚪" && !player1Turn ||
    game_board(from_row)(from_col) == "⚫" && player1Turn ||
    Math.abs(from_row - to_row) == 2 && game_board(mid_row)(mid_col) == "🟨" ||
    Math.abs(from_row - to_row) > 2 ||
    Math.abs(from_col - to_col) == 2 && game_board(mid_row)(mid_col) == "🟨" ||
    Math.abs(from_col - to_col) > 2 ||
    from_row == to_row ||
    from_col == to_col ||
    game_board(from_row)(from_col) == "⚪" && from_row < to_row ||
    game_board(from_row)(from_col) == "⚫" && from_row > to_row
  )
  validInput match
    case false => return (game_board, validInput)
    case true =>
      game_board(to_row)(to_col) = game_board(from_row)(from_col)
      game_board(from_row)(from_col) = "🟨"
      Math.abs(from_row - to_row) match
        case 2 => game_board(mid_row)(mid_col) = "🟨"
        case _ =>

  (game_board, validInput)
}

def checkers_drawer(game_board: Array[Array[String]]): Unit = {
  val rows = game_board.zipWithIndex.map {
    case (row, index) =>
      val rowString = row.zipWithIndex.map {
        case (cell, 0) => s"${8 - index} $cell"
        case (cell, _) => s"$cell"
      }.mkString(" ")
      s"$rowString"
  }

  val columnLabels = "  \u2009\u2009a  \u2009b  c  \u2009d  \u2009e  f  \u2009g  \u2009h"

  (rows :+ columnLabels).foreach(println)
}

def checkers_initializer(): Array[Array[String]] = {
  Array.tabulate(8, 8) { (i, j) =>
    if ((i + j) % 2 != 0) {
      if (i < 3) "⚫" else if (i > 4) "⚪" else "🟨"
    } else {
      "🟥"
    }
  }
}
