const mod1 = require('./AbstractGameEngine')
class TicTacToeGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }
    init_board() {
        let board = [];
        for(let i = 0 ; i < 3 ; i++){
            board[i] = [];
            for(let j = 0 ; j < 3 ; j++){
                board[i][j] = '🟩';
            }
        }
        return board;
        // return initialized board
    }
    drawer(game_board){
        for(let i = 0 ; i < 3 ; i++){
            let row = '';
            for(let j = 0 ; j < 4 ; j++){
                if(j == 0) row += (i + 1).toString() + ' ';
                else row += game_board[i][j - 1] + ' ';
            }
            row += '\x1b[0m';
            console.log(row);
        }
        console.log('   a  b  c')
    }
    controller(game_board, user_input, player1Turn){
        let cell_number = parseInt(user_input[0]);
        let cell_letter = user_input[1].toLowerCase();
        let valid = !(user_input.length != 2
                    || cell_number < 1 || cell_number > 3
                    || cell_letter < 'a' || cell_letter > 'c'
                    || game_board[cell_number - 1][parseInt(cell_letter.charCodeAt(0) - 'a'.charCodeAt(0))] != '🟩');
        if(!valid) return [game_board, valid];
        else {
            let symbol = player1Turn? '\x1b[31m' + ' X' + '\x1b[0m' : '\x1b[34m' + ' O' + '\x1b[0m';
            game_board[cell_number - 1][parseInt(cell_letter.charCodeAt(0) - 'a'.charCodeAt(0))] = symbol;
            return [game_board, valid];
        }
    }
}
module.exports = {TicTacToeGameEngine}