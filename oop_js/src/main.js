// Imports ...
const mod1 = require('./Engines/ChessGameEngine')
const mod2 = require('./Engines/Connect4GameEngine')
const mod3 = require('./Engines/SudokuGameEngine')
const mod4 = require('./Engines/EightQueensGameEngine')
const mod5 = require('./Engines/CheckersGameEngine')
const mod6 = require('./Engines/TicTacToeGameEngine')
const readlineSync = require('readline-sync');


while(true){
    // 1- Scan the user input to choose a game
    const gameNumber = readlineSync.question(
        'Enter the choice number:\n' +
        '1. Chess Game\n' +
        '2. Connect 4 Game\n' +
        '3. Sudoku\n' +
        '4. 8 Queens\n' +
        '5. Checkers\n' +
        '6. Tic Tac Toe\n' + 
        'Enter -1 in any Game to return to main menu.\n'
    )

    // 2- depending on the input, create the gameInstance
    let gameInstance;
    let two_players_game = true;
    switch (gameNumber){
        case '1':
            gameInstance = new mod1.ChessGameEngine();
            break;
        case '2':
            gameInstance = new mod2.Connect4GameEngine();
            break;
        case '3':
            gameInstance = new mod3.SudokuGameEngine();
            two_players_game = false;
            break;
        case '4':
            gameInstance = new mod4.EightQueensGameEngine();
            two_players_game = false;
            break;
        case '5':
            gameInstance = new mod5.CheckersGameEngine();
            break;
        case '6':
            gameInstance = new mod6.TicTacToeGameEngine();
            break;
        default:
            continue;
    }

    // 3- gameInstance.run()
    gameInstance.run(two_players_game)
}