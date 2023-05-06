import scala.annotation._

def AbstractGameEngine(
                        controller: (Array[Array[Char]], String, Boolean) => (Array[Array[Char]], Boolean),
                        drawer:  Array[Array[Char]] => Unit,
                        initializer: () => Array[Array[Char]],
                        twoPlayersGame: Boolean
                      ): Unit = {
  play(initializer(), controller, drawer, twoPlayersGame, true)
}

@tailrec
private def play(
                  board: Array[Array[Char]],
                 controller: (Array[Array[Char]], String, Boolean) => (Array[Array[Char]], Boolean),
                 drawer:  Array[Array[Char]] => Unit,
                 twoPlayersGame: Boolean,
                 player1Turn: Boolean
                ): Unit = {
  if (twoPlayersGame) println(f"Player ${if (player1Turn) 1 else 2} - Turn")
  controller(board, scala.io.StdIn.readLine(), player1Turn) match {
    case (_,false) =>
      println(s"Invalid Move, Try again.")
      play(board, controller, drawer, twoPlayersGame, player1Turn)
    case (newBoard,true) =>
      drawer(newBoard)
      play(newBoard, controller, drawer, twoPlayersGame, !player1Turn)
  }
}