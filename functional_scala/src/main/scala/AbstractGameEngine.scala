import scala.annotation._

def AbstractGameEngine(
                        controller: (Array[Array[String]], String, Boolean) => (Array[Array[String]], Boolean),
                        drawer:  Array[Array[String]] => Unit,
                        initializer: () => Array[Array[String]],
                        twoPlayersGame: Boolean
                      ): Unit = {
  val board = initializer()
  drawer(board)
  play(board, controller, drawer, twoPlayersGame, true)
}

@tailrec
def play(
          board: Array[Array[String]],
          controller: (Array[Array[String]], String, Boolean) => (Array[Array[String]], Boolean),
          drawer:  Array[Array[String]] => Unit,
          twoPlayersGame: Boolean,
          player1Turn: Boolean
        ): Unit = {
  println((if(twoPlayersGame) s"Player ${if (player1Turn) 1 else 2} - Turn\n" else "").concat("Enter Game Input: "))
  val input = scala.io.StdIn.readLine()
  if(input == "-1")
    return;
  controller(board, input, player1Turn) match {
    case (_,false) =>
      println(s"Invalid Move, Try again.")
      play(board, controller, drawer, twoPlayersGame, player1Turn)
    case (newBoard,true) =>
      drawer(newBoard)
      play(newBoard, controller, drawer, twoPlayersGame, !player1Turn)
  }
}