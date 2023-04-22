const Game  = require('../Games/Game');

class TicTacToe extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('TicTacToe drawer')
    }

    controller() {
        console.log('TicTacToe controller')
    }

}

module.exports = TicTacToe;