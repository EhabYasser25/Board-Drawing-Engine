const mod1 = require('./AbstractGameEngine')

class CheckersGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }

    init_board() {
        let board = [];
        for(let i = 0 ; i < 8 ; i++){
            board[i] = [];
            for(let j = 0 ; j < 8 ; j++){
                if((i + j) % 2 != 0) {
                    if(i < 3) board[i][j] = '⚫';
                    else if(i > 4) board[i][j] = '⚪';
                    else board[i][j] = '🟨';
                }
                else board[i][j] = '🟥';
            }
        }
        return board;
    }

    drawer(game_board) {
        console.log("Checkers Game Engine - Drawer: Drawing Game Board ...");
        let rowLabels = ['8', '7', '6', '5', '4', '3', '2', '1'];
        let colLabels = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
        for(let i = 0 ; i < 8 ; i++) {
            let row = '';
            for(let j = 0 ; j < 9 ; j++) {
                if(j == 0) row += rowLabels[i] + ' ';
                else row += game_board[i][j - 1] + ' ';
            }
            console.log(row);
        }
        console.log('   ' + colLabels.join('  '));
    }

    controller(game_board, user_input, player1Turn) {
        console.log("Checkers Game Engine - Controller: Validating Input ...");
        let from_coord = user_input.slice(0, 2);
        let to_coord = user_input.slice(3);
        let from_row = 8 - parseInt(from_coord[0]);
        let from_col = from_coord[1].charCodeAt(0) - 'a'.charCodeAt(0);
        let to_row = 8 - parseInt(to_coord[0]);
        let to_col = to_coord[1].charCodeAt(0) - 'a'.charCodeAt(0);
        if(user_input.length != 5) return [game_board, false];
        let mid_col = ((from_col < to_col)? 'a'.charCodeAt(0) + from_col + 1 : 'a'.charCodeAt(0) + to_col + 1) - 'a'.charCodeAt(0);
        let mid_row = (from_row + to_row) / 2;
        let valid = !(from_row < 0 || from_row > 7
                    || from_col < 0 || from_col > 7
                    || to_row < 0 || to_row > 7
                    || to_col < 0 || to_col > 7
                    || game_board[from_row][from_col] == '🟥'
                    || game_board[from_row][from_col] == '🟨'
                    || game_board[to_row][to_col] != '🟨'
                    || game_board[from_row][from_col] == '⚪' && !player1Turn
                    || game_board[from_row][from_col] == '⚫' && player1Turn
                    || Math.abs(from_row - to_row) == 2 && game_board[mid_row][mid_col] == '🟨'
                    || Math.abs(from_row - to_row) > 2
                    || Math.abs(from_col - to_col) == 2 && game_board[mid_row][mid_col] == '🟨'
                    || Math.abs(from_col - to_col) > 2
                    || from_row == to_row
                    || from_col == to_col
                    || game_board[from_row][from_col] == '⚪' && from_row < to_row
                    || game_board[from_row][from_col] == '⚫' && from_row > to_row);
        if(!valid) return [game_board, valid];
        else {
            game_board[to_row][to_col] = game_board[from_row][from_col];
            game_board[from_row][from_col] = '🟨';
            if(Math.abs(from_row - to_row) == 2) {
                game_board[mid_row][mid_col] = '🟨';
            }
            return [game_board, valid];
        }
    }
}

module.exports = {CheckersGameEngine}