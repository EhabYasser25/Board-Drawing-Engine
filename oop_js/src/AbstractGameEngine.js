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

    run(two_players_game){
        let game_board;
        let inputStateIndicator = false;
        let player1Turn = true;
        this.drawer(game_board = this.init_board());
        while(true){
            if(two_players_game)
                console.log(`Player ${player1Turn ? 1 : 2} - Turn`);
            const input = this.scanInput();
            if(input == "-1")
                return;
            [game_board, inputStateIndicator] = this.controller(game_board, input, player1Turn);
            while(!inputStateIndicator) {
                console.log('Invalid Move, Try Again');
                const input = this.scanInput();
                if(input == "-1")
                    return;
                [game_board, inputStateIndicator] = this.controller(game_board, input, player1Turn);
            }
            console.clear()
            this.drawer(game_board);
            [inputStateIndicator, player1Turn] = [false, !player1Turn]
            console.log('----------------- Separator -----------------');
        }
    }

    scanInput(){
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

    controller(game_board, user_input, player1Turn){
        // Implement the game logic to validate the user input
        // ...
        // Apply the modifications - in case the input was validated - to the game board
        // ...
        // return [game_board, InputStateIndicator]
        throw new Error("Abstract method must be implemented.");
    }
}
module.exports = {AbstractGameEngine}