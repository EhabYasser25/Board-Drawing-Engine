const mod1 = require('./AbstractGameEngine')
class ChessGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }
    init_board() {
        console.log("Chess Game Engine: Initializing Game Board ... ")
        return []
        // return initialized board
    }
    drawer(game_board){
        console.log("Chess Game Engine - Drawer: Drawing Game Board ...")
        console.log(game_board)
        // Display the board with the game specific logic
        // returns void
    }
    controller(game_board, user_input){
        console.log("Chess Game Engine - Controller: Validating Input ...")
        // Validate the input & update
        game_board.push(user_input)
        return game_board
    }
}
module.exports = {ChessGameEngine}