package Engines

def checkers_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  val normal_input = """([1-8])([a-h]) ([1-8])([a-h])""".r
  input match
    case normal_input(from_row, from_col, to_row, to_col) =>
    case _ => return (game_board, false)
  val from_row = 8 - Integer.parseInt(input(0).toString)
  val from_col = input(1) - 'a'
  val to_row = 8 - Integer.parseInt(input(3).toString)
  val to_col = input(4) - 'a'
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
  if(!validInput) return (game_board, validInput)
  val eaten = Math.abs(from_row - to_row) match
    case 2 => true
    case _ => false
  val board: Array[Array[String]] = game_board.zipWithIndex.map {
    case (row, rowIndex) =>
      if(rowIndex == from_row) row.updated(from_col, "🟨")
      else if(rowIndex == to_row) row.updated(to_col, game_board(from_row)(from_col))
      else if(rowIndex == mid_row && eaten) row.updated(mid_col, "🟨")
      else row
  }
  (board, validInput)
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
