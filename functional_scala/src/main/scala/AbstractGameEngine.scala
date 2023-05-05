

def AbstractGameEngine(
                        controller: (Array[Array[Char]], String, Boolean) => (Array[Array[Char]], Boolean),
                        drawer: (Array[Array[Char]]) => Unit,
                        initializer: () => Array[Array[Char]],
                        two_players_game: Boolean
                      ): Unit = {

  var game_board: Array[Array[Char]] = initializer();
  var inputStateIndicator = false;
  var player1Turn = true;
  drawer(game_board);
  while(true){
    if (two_players_game) println(f"Player ${if (player1Turn) 1 else 2} - Turn")
    var tempResult: (Array[Array[Char]], Boolean) = controller(game_board, scala.io.StdIn.readLine(), player1Turn);
    game_board = tempResult._1;inputStateIndicator = tempResult._2;
    while(!inputStateIndicator){
      println("Invalid Move, Try again.");
      tempResult = controller(game_board, scala.io.StdIn.readLine(), player1Turn);
      game_board = tempResult._1;inputStateIndicator = tempResult._2;
    }
    drawer(game_board);
    inputStateIndicator = false; player1Turn = !player1Turn;
    println("----------------- Separator -----------------");
  }

}