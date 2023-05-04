const mod1 = require('./AbstractGameEngine')
class Connect4GameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }
    init_board() {
        // console.log("Connect Four Game Engine: Initializing Game Board ... ")
        // return initialized board
        let board = [];
        for(let i = 0 ; i < 6 ; i++){
            board[i] = [];
            for(let j = 0 ; j < 7 ; j++){
                board[i][j] = '⚪';
            }
        }
        return board;
    }
    drawer(game_board){
        // console.log("Connect Four Game Engine - Drawer: Drawing Game Board ...")
        // console.log(game_board)
        // Display the board with the game specific logic
        // returns void
        const blueColor = '\x1b[44m';
        const resetColor = '\x1b[0m';
        console.clear()
        console.log(`${blueColor} 1  2  3  4  5  6  7 ${resetColor}`);
        for(let i = 0 ; i < 6 ; i++){
            let row = blueColor;
            for(let j = 0 ; j < 7 ; j++){
                row += game_board[i][j] + ' ';
            }
            row += resetColor;
            console.log(row);
        }
    }
    controller(game_board, user_input, player1Turn){
        // console.log("Connect Four Game Engine - Controller: Validating Input ...")
        // // Validate the input & update
        // game_board.push(user_input.split("").reverse().join(""));
        let valid = true;
        let col = parseInt(user_input);
        if(col <= 7 && col > 0){
            if(game_board[0][col-1] == '⚪'){
                for(let i = 5 ; i >= 0 ; i--){
                    if(game_board[i][col-1] == '⚪'){
                        if(player1Turn){
                            game_board[i][col-1] = '🔴';
                        }
                        else {
                            game_board[i][col-1] = '🟡';
                        }
                        break;
                    }
                }
            }
            else {
                valid = false;
            }
        }
        else{
            valid = false;
        }
        return [game_board, valid];
    }
}
module.exports = {Connect4GameEngine}