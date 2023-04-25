
// Define a game engine
object AbstractGameEngine {
  // Track the current player (1 or 2)
  var currentPlayer = 1

  // Define a function type for the game controller
  var Controller: (Array[Array[Char]], String) => Array[Array[Char]] = _

  // Set the controller and display the initial board using the drawer function
  def playGame(drawer: Array[Array[Char]] => Unit,
               controller: (Array[Array[Char]], String) => Array[Array[Char]],
               board: Array[Array[Char]]): Unit = {
    Controller = controller
    drawer(board)
  }

  // Call the controller with player input and return the updated board
  def Control(board: Array[Array[Char]], input: String): Array[Array[Char]] = {
    Controller(board, input)
  }

  // Switch to the next player's turn
  def alternateTurns(): Unit = {
    currentPlayer = if (currentPlayer == 1) 2 else 1
  }
}
