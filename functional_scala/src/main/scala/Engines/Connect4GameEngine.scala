package Engines

def connect4_controller(game_board: Array[Array[String]], input: String, player1Turn: Boolean): (Array[Array[String]], Boolean) = {
  val col = input.toInt
  val validInput = col > 0 && col <= 7
  val validMove = validInput && game_board(0)(col - 1) == "⚪"
  val board: Array[Array[String]] = if(validMove){
    val playerColor = if(player1Turn) "🔴" else "🟡"
    game_board.zipWithIndex.map {
      case (row, rowIndex) =>
        if((rowIndex == game_board.length - 1 || game_board(rowIndex + 1)(col - 1) != "⚪") && row(col - 1) == "⚪")
          row.updated(col - 1, playerColor)
        else
          row
    }
  }
  else {
    game_board
  }
  (board, validMove)
}

def connect4_drawer(game_board: Array[Array[String]]): Unit = {
  val rows = game_board.map(row => row.mkString(" ") + " ")
  val board = s" 1  2  3  4  5  6  7 \n${rows.mkString("\n")}"
  println(board)
}

def connect4_initializer(): Array[Array[String]] = {
  Array.fill[String](6,7)("⚪")
}