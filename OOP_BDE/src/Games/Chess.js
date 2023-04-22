const Game  = require('../Games/Game');

class Chess extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('Chess drawer')
    }

    controller() {
        console.log('Chess controller')
    }

}

module.exports = Chess;