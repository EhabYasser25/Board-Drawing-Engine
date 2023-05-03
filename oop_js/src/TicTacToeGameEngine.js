const mod1 = require('./AbstractGameEngine')
class TicTacToeGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }
    init_board() {
        console.log("Tic Tac Toe Game Engine: Initializing Game Board ... ")
        return []
        // return initialized board
    }
    drawer(game_board){
        console.log("Tic Tac Toe Game Engine - Drawer: Drawing Game Board ...")
        console.log(game_board)
        // Display the board with the game specific logic
        // returns void
    }
    controller(game_board, user_input, player1Turn){
        console.log("Tic Tac Toe Game Engine - Controller: Validating Input ...")
        // Validate the input & update
        game_board.push(user_input)
        return [game_board, true]
    }
}
module.exports = {TicTacToeGameEngine}