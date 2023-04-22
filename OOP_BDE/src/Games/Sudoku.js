const Game  = require('../Games/Game');

class Sudoku extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('Sudoku drawer')
    }

    controller() {
        console.log('Sudoku controller')
    }

}

module.exports = Sudoku;