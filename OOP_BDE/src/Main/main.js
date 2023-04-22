const TicTacToe  = require('../Games/TicTacToe');
const ConnectFour  = require('../Games/ConnectFour');
const Checkers  = require('../Games/Checkers');
const Chess  = require('../Games/Chess');
const Sudoku  = require('../Games/Sudoku');
const EightQueens  = require('../Games/EightQueens');

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});

console.log('Welcome to our project');
console.log();
console.log('Please choose a game:');
console.log('1. Tic Tac Toe');
console.log('2. Connect 4');
console.log('3. Checkers');
console.log('4. Chess');
console.log('5. Sudoku');
console.log('6. 8 Queens');

let game;

readline.question('', (choice) => {
    switch (parseInt(choice)) {
        case 1:
            game = new TicTacToe();
            break;
        case 2:
            game = new ConnectFour();
            break;
        case 3:
            game = new Checkers();
            break;
        case 4:
            game = new Chess();
            break;
        case 5:
            game = new Sudoku();
            break;
        case 6:
            game = new EightQueens();
            break;
        default:
            console.log('Invalid choice');
            readline.close();
            return;
    }
    game.drawer()
    game.controller()
    readline.close();
});