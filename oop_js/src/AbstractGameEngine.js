class AbstractGameEngine {
    constructor() {
        if (new.target === AbstractGameEngine) {
            throw new TypeError("Cannot instantiate abstract class.");
        }
    }

    // Abstract Method, should be implemented by children classes, to implement their own game logic
    // for initializing the game board
    init_board(){
        throw new Error("Abstract method must be implemented.");
        // let game_board = ...
        // return game_board
    }

    async run(){
        let game_board = null;
        while(true){
            this.drawer(game_board = this.controller(
                (game_board == null ? this.init_board() : game_board),
                await this.scanInput()
            ));
            console.log('----------------- Separator -----------------');
        }
    }

    async scanInput(){
        // Scan the user input as a STRING from the console, however no modifications should
        // be made to this string as it would be passed to the controller to validate it depending
        // on the game logic.
        const readlineSync = require('readline-sync');
        return readlineSync.question('Enter Game Input: \n');
    }

    drawer(game_board){
        // Display the game_board to the user through the console
        throw new Error("Abstract method must be implemented.");
    }

    controller(game_board, user_input){
        // Implement the game logic to validate the user input
        // ...
        // Apply the modifications - in case the input was validated - to the game board
        // ...
        // return game_board
        throw new Error("Abstract method must be implemented.");
    }
}
module.exports = {AbstractGameEngine}