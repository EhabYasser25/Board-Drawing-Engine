const mod1 = require('../AbstractGameEngine')
class ChessGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super();
        this.pieces = new Map([
            // ['White King', '\u2654'],
            // ['White Queen', '\u2655'],
            // ['White Rook', '\u2656'],
            // ['White Bishop', '\u2657'],
            // ['White Knight', '\u2658'],
            // ['White Pawn', '\u2659'],
            // ['Black King', '\u265A'],
            // ['Black Queen', '\u265B'],
            // ['Black Rook', '\u265C'],
            // ['Black Bishop', '\u265D'],
            // ['Black Knight', '\u265E'],
            // ['Black Pawn', '\u265F'],
            // ['White Square', '\u25A1'],
            // ['Black Square', '\u25A0']
            ['White King', '\u265A'],
            ['White Queen', '\u265B'],
            ['White Rook', '\u265C'],
            ['White Bishop', '\u265D'],
            ['White Knight', '\u265E'],
            ['White Pawn', '\u265F'],
            ['White Square', '\u25A0'],
            ['Black King', '\u2654'],
            ['Black Queen', '\u2655'],
            ['Black Rook', '\u2656'],
            ['Black Bishop', '\u2657'],
            ['Black Knight', '\u2658'],
            ['Black Pawn', '\u2659'],
            ['Black Square', '\u25A1']
        ]);
    }
    toUnicode(pieceName){
        return this.pieces.get(pieceName)
    }
    init_board() {
        // return initialized board
        return [
            ['Black Rook', 'Black Knight', 'Black Bishop', 'Black Queen', 'Black King', 'Black Bishop', 'Black Knight', 'Black Rook'],
            ['Black Pawn', 'Black Pawn', 'Black Pawn', 'Black Pawn', 'Black Pawn', 'Black Pawn', 'Black Pawn', 'Black Pawn'],
            ['White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square'],
            ['Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square'],
            ['White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square'],
            ['Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square', 'Black Square', 'White Square'],
            ['White Pawn', 'White Pawn', 'White Pawn', 'White Pawn', 'White Pawn', 'White Pawn', 'White Pawn', 'White Pawn'],
            ['White Rook', 'White Knight', 'White Bishop', 'White Queen', 'White King', 'White Bishop', 'White Knight', 'White Rook'],
        ];
    }
    drawer(game_board){
        // Display the board with the game specific logic
        let output = `  a b c d e f g h\n`;         // cols identifiers
        for(let i = 0 ; i < 8 ; i++) {
            output += `${i+1} `                            // rows identifiers
            for (let j = 0; j < 8; j++)
                output += `${this.toUnicode(game_board[i][j])} `
            output += `\n`;
        }
        console.log(output);
        // returns void
    }
    controller(game_board, user_input, player1Turn){        // player1 -> white
        // Converting the input to the corresponding coordinates
        if(user_input.length < 5) return [game_board, false]
        let pos = [parseInt(user_input[0]) - 1, user_input[1].charCodeAt(0) - 'a'.charCodeAt(0), parseInt(user_input[3]) - 1, user_input[4].charCodeAt(0) - 'a'.charCodeAt(0)];
        let diff = [pos[2] - pos[0], pos[3] - pos[1]];
        // console.log(pos)
        // console.log(diff)
        // console.log(game_board[pos[0]][pos[1]])
        // console.log(game_board[pos[2]][pos[3]].substring(6, 12))

        // validating input ...
        // Phase 1 of validation
        // good_flag is true when !badCond1 && !badCond2 etc ... = !(badCond1 || badCond2 || etc ...)
        console.log('Reached Phase 1 of validation')    // comment this
        let good_flag = !(
            pos.some(x => x < 0) || pos.some(x => x > 7) ||
            pos.some(x => isNaN(x)) || [pos[0], pos[1]] === [pos[2], [pos[3]]] ||
            (game_board[pos[0]][pos[1]].substring(0, 5) === game_board[pos[2]][[pos[3]]].substring(0, 5)) &&
                (game_board[pos[2]][[pos[3]]].substring(6, 12) !== 'Square') ||
            game_board[pos[0]][pos[1]].substring(6, 12) === 'Square' ||
            (game_board[pos[0]][pos[1]].substring(0, 5) === 'Black' && player1Turn) ||
            (game_board[pos[0]][pos[1]].substring(0, 5) === 'White' && !player1Turn)
        );
        if(!good_flag)
            return [game_board, false]

        console.log('Reached Phase 2 of validation')    // comment this
        // Phase 2 of validation
        console.log(game_board[pos[0]][pos[1]])
        switch (game_board[pos[0]][pos[1]]){
            case "White King":
                good_flag = Math.abs(diff[0]) <= 1 && Math.abs(diff[0]) <= 1;
                break;
            case "Black King":
                good_flag = Math.abs(diff[0]) <= 1 && Math.abs(diff[0]) <= 1;
                break;
            case "White Queen":
                good_flag = Math.abs(diff[0]) === Math.abs(diff[1]) || pos[0] === pos[2] || pos[1] === pos[3];
                break;
            case "Black Queen":
                good_flag = Math.abs(diff[0]) === Math.abs(diff[1]) || pos[0] === pos[2] || pos[1] === pos[3];
                break;
            case "White Rook":
                good_flag = pos[0] === pos[2] || pos[1] === pos[3];
                break;
            case "Black Rook":
                good_flag = pos[0] === pos[2] || pos[1] === pos[3];
                break;
            case "White Bishop":
                good_flag = Math.abs(diff[0]) === Math.abs(diff[1]);
                break;
            case "Black Bishop":
                good_flag = Math.abs(diff[0]) === Math.abs(diff[1]);
                break;
            case "White Knight":
                good_flag =  Math.abs(diff[0]) + Math.abs(diff[1]) === 3;
                break;
            case "Black Knight":
                good_flag =  Math.abs(diff[0]) + Math.abs(diff[1]) === 3;
                break;
            case "White Pawn":
                good_flag = (diff[0] === -1) && ((game_board[pos[2]][pos[3]].substring(6, 12) !== 'Square') ? (Math.abs(diff[1]) === 1) : (Math.abs(diff[1]) === 0));
                break;
            case "Black Pawn":
                good_flag = (diff[0] === 1) && ((game_board[pos[2]][pos[3]].substring(6, 12) !== 'Square') ? (Math.abs(diff[1]) === 1) : (Math.abs(diff[1]) === 0));
                break;
            default:
                console.log("Error - Chess controller - phase 2")
                break;
        }
        if(!good_flag)
            return [game_board, false]

        console.log('Reached Phase 3 of validation')        // comment this
        // Phase 3 of validation
        switch (game_board[pos[0]][pos[1]]){
            case "White Queen":
                if(Math.abs(diff[0]) === Math.abs(diff[1])){
                    for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                        if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                            good_flag = false;
                } else if(pos[0] === pos[2] || pos[1] === pos[3]){
                    if(pos[0] === pos[2]){
                        for(let m = 1 ; m < Math.abs(diff[1]) ; m++)
                            if(game_board[pos[0]][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                                good_flag = false;
                    } else {
                        for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                            if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1]].substring(6, 12) !== 'Square')
                                good_flag = false;
                    }
                } else
                    console.log('Chess Controller: Error in phase 3 validation - queen move');
                break;
            case "Black Queen":
                if(Math.abs(diff[0]) === Math.abs(diff[1])){
                    for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                        if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                            good_flag = false;
                } else if(pos[0] === pos[2] || pos[1] === pos[3]){
                    if(pos[0] === pos[2]){
                        for(let m = 1 ; m < Math.abs(diff[1]) ; m++)
                            if(game_board[pos[0]][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                                good_flag = false;
                    } else {
                        for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                            if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1]].substring(6, 12) !== 'Square')
                                good_flag = false;
                    }
                } else
                    console.log('Chess Controller: Error in phase 3 validation - queen move');
                break;
            case "White Rook":
                if(pos[0] === pos[2]){
                    for(let m = 1 ; m < Math.abs(diff[1]) ; m++)
                        if(game_board[pos[0]][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                            good_flag = false;
                } else {
                    for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                        if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1]].substring(6, 12) !== 'Square')
                            good_flag = false;
                }
                break;
            case "Black Rook":
                if(pos[0] === pos[2]){
                    for(let m = 1 ; m < Math.abs(diff[1]) ; m++)
                        if(game_board[pos[0]][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                            good_flag = false;
                } else {
                    for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                        if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1]].substring(6, 12) !== 'Square')
                            good_flag = false;
                }
                break;
            case "White Bishop":
                for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                    if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                        good_flag = false;
                break;
            case "Black Bishop":
                for(let m = 1 ; m < Math.abs(diff[0]) ; m++)
                    if(game_board[pos[0] + (m * diff[0] / Math.abs(diff[0]))][pos[1] + (m * diff[1] / Math.abs(diff[1]))].substring(6, 12) !== 'Square')
                        good_flag = false;
                break;
        }
        if(!good_flag)
            return [game_board, false]

        // Move has been validated => Apply it on the game board
        game_board[pos[2]][pos[3]] = game_board[pos[0]][pos[1]]
        game_board[pos[0]][pos[1]] = ((pos[0] + pos[1]) % 2 === 0) ? 'White Square' : 'Black Square';


        return [game_board, true]
    }
}
module.exports = {ChessGameEngine}