const Game  = require('../Games/Game');

class Checkers extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('Checkers drawer')
    }

    controller() {
        console.log('Checkers controller')
    }

}

module.exports = Checkers;