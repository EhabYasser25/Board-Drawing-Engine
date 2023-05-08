import scala.annotation._

def AbstractGameEngine(
                        controller: (Array[Array[String]], String, Boolean) => (Array[Array[String]], Boolean),
                        drawer:  Array[Array[String]] => Unit,
                        initializer: () => Array[Array[String]],
                        twoPlayersGame: Boolean
                      ): Unit = {
  drawer(initializer())
  play(initializer(), controller, drawer, twoPlayersGame, true)
}

@tailrec
private def play(
                  board: Array[Array[String]],
                  controller: (Array[Array[String]], String, Boolean) => (Array[Array[String]], Boolean),
                  drawer:  Array[Array[String]] => Unit,
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