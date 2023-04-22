const Game  = require('../Games/Game');

class EightQueens extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('EightQueens drawer')
    }

    controller() {
        console.log('EightQueens controller')
    }

}

module.exports = EightQueens;