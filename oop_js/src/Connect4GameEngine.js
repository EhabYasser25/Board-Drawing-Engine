const mod1 = require('./AbstractGameEngine')
class Connect4GameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }
    init_board() {
        console.log("Connect Four Game Engine: Initializing Game Board ... ")
        return []
        // return initialized board
    }
    drawer(game_board){
        console.log("Connect Four Game Engine - Drawer: Drawing Game Board ...")
        console.log(game_board)
        // Display the board with the game specific logic
        // returns void
    }
    controller(game_board, user_input){
        console.log("Connect Four Game Engine - Controller: Validating Input ...")
        // Validate the input & update
        game_board.push(user_input.split("").reverse().join(""));
        return game_board;
    }
}
module.exports = {Connect4GameEngine}