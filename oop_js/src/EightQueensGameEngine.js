const mod1 = require('./AbstractGameEngine')

class EightQueensGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
    }

    init_board() {
        let board = [];
        for (let i = 0; i < 9; i++) {
            board[i] = [];
            for (let j = 0; j < 9; j++) {
                if (i == 0) {
                    board[i][j] = (j == 0) ? '  ' : ' ' + String.fromCharCode('a'.charCodeAt(0) + j - 1) + ' ';
                } else if (j == 0 && i > 0) {
                    board[i][j] = i;
                } else if ((i % 2 === 0 && j % 2 === 0) || (i % 2 === 1 && j % 2 === 1)) {
                    board[i][j] = (j == 1) ? ' 🟧 ' : '🟧 '
                } else {
                    board[i][j] = (j == 1) ? ' ⬜ ' : '⬜ '
                }
            }
        }
        // Add queens to their starting positions
        return board;
    }

    one_player() {
        return true
    }
    drawer(game_board) {
        console.clear()
        const white = '\x1b[30m';
        const reset = '\x1b[0m';
        for (let i = 0; i < 9; i++) {
            let row = white
            for (let j = 0; j < 9; j++) {
                if (game_board[i][j] === '♕') {
                    row += (j == 1) ? ' ' : ''
                    row += game_board[i][j] + '  '
                    continue
                }
                row += game_board[i][j]
            }
            row += reset;
            console.log(row)
        }
        // Display the board with the game specific logic
        // returns void
    }
    controller(game_board, user_input, player1Turn) {
        let row = Number(user_input[0])
        let col = Number(user_input[1].charCodeAt(0) - 'a'.charCodeAt(0)) + 1
        for (let i = 0; i < game_board.length; i++) {
            if (game_board[row][i] === '♕' && i !== col) {
                return [game_board, false]
            }
        }

        for (let i = 0; i < game_board.length; i++) {
            if (game_board[i][col] === '♕' && i !== row) {
                return [game_board, false]
            }
        }

        // Check if there is another queen in the same diagonal (ascending)
        for (let i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (game_board[i][j] === '♕') {
                return [game_board, false];
            }
        }

        // Check if there is another queen in the same diagonal (descending)
        for (let i = row + 1, j = col - 1; i < game_board.length && j >= 0; i++, j--) {
            if (game_board[i][j] === '♕') {
                return [game_board, false];
            }
        }

        game_board[row][col] = '♕'
        return [game_board, true]
    }
}
module.exports = { EightQueensGameEngine }