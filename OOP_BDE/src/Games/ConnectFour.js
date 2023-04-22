const Game  = require('../Games/Game');

class ConnectFour extends Game {

    constructor() {
        super();
    }

    drawer() {
        console.log('ConnectFour drawer')
    }

    controller() {
        console.log('ConnectFour controller')
    }

}

module.exports = ConnectFour;