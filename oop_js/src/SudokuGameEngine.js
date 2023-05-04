const mod1 = require('./AbstractGameEngine')

class SudokuGameEngine extends mod1.AbstractGameEngine {
    constructor() {
        super()
    }

    init_board() {
        return [
            ['5', '3', '0', '0', '7', '0', '0', '0', '0'],
            ['6', '0', '0', '1', '9', '5', '0', '0', '0'],
            ['0', '9', '8', '0', '0', '0', '0', '6', '0'],
            ['8', '0', '0', '0', '6', '0', '0', '0', '3'],
            ['4', '0', '0', '8', '0', '3', '0', '0', '1'],
            ['7', '0', '0', '0', '2', '0', '0', '0', '6'],
            ['0', '6', '0', '0', '0', '0', '2', '8', '0'],
            ['0', '0', '0', '4', '1', '9', '0', '0', '5'],
            ['0', '0', '0', '0', '8', '0', '0', '7', '9'],
        ]
    }

    drawer(game_board){

        //define constants used in drawing
        const redColor = '\x1b[31m'
        const resetColor = '\x1b[0m'
        const boldTopHorizontalLine = `${redColor}┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓${resetColor}`
        const boldMiddleHorizontalLine = `${redColor}┣━━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━┫${resetColor}`
        const boldBottomHorizontalLine = `${redColor}┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛${resetColor}`
        const horizontalLine = `${redColor}┃${resetColor}───┼───┼───${redColor}┃${resetColor}───┼───┼───${redColor}┃${resetColor}───┼───┼───${redColor}┃${resetColor}`
        const boldVerticalLine = `${redColor}┃${resetColor}`
        const verticalLine = `│`

        //print the board
        console.log('    a   b   c   d   e   f   g   h   i')
        console.log(`  ${boldTopHorizontalLine}`)
        for (let i = 0; i < 9; i++) {
            let row = `${boldVerticalLine} `
            for (let j = 0; j < 9; j++) {
                const value = Number(game_board[i][j]) === 0 ? ' ' : game_board[i][j]
                row += `${value} `
                if ((j + 1) % 3 === 0)
                    row += `${boldVerticalLine} `
                else
                    row += `${verticalLine} `
            }
            console.log(`${i+1} ${row}`)
            if ((i + 1) % 3 === 0 && i < 8)
                console.log(`  ${boldMiddleHorizontalLine}`)
            else if(i < 8)
                console.log(`  ${horizontalLine}`)
        }
        console.log(`  ${boldBottomHorizontalLine}`)

    }

    controller(game_board, user_input, _){

        //validate input
        let valid = this.validateInput(game_board, user_input)

        //update the board if input is valid
        if(valid) {
            //get data from input string
            const row = parseInt(user_input[0]) - 1
            const col = user_input[1].charCodeAt(0) - 'a'.charCodeAt(0)
            game_board[row][col] = user_input.length === 4 ? Number(user_input[3]) : 0
        }

        //return the results
        return [game_board, valid]

    }

    validateInput(game_board, user_input) {

        //to store true if the current operation is removing element
        let removeElement = false

        //handle removing element
        if(user_input.length === 2) {
            user_input += ' 0'
            removeElement = true
        }

        //handle shorter or longer input
        if(user_input.length !== 4)
            return false

        //get data from input string
        const row = parseInt(user_input[0]) - 1
        const col = user_input[1].charCodeAt(0) - 'a'.charCodeAt(0)
        const space = user_input[2]
        const num = Number(user_input[3])

        //check boundaries
        if (row < 0 || row >= 9 || col < 0 || col >= 9 || num < 0 || num > 9)
            return  false

        //check on the in-between space
        if(space !== ' ')
            return false

        //handle removing element
        if(removeElement) {

            //check if it is initial element
            if(typeof game_board[row][col] === 'string')
                return false

            //check in full cell
            if(Number(game_board[row][col]) === 0)
                return false

            //return true if element is not initial and the cell is full
            return true
        }

        //check empty cell
        if (Number(game_board[row][col]) !== 0)
            return  false

        //check on non-repetition of the num in its row or column
        for(let i = 0; i < 9; i++)
            if(Number(game_board[i][col]) === num || Number(game_board[row][i]) === num)
                return false

        //check on non-repetition of the num in its square
        let r = Math.floor(row / 3) * 3
        let c = Math.floor(col / 3) * 3
        for(let i = r; i < r+3; i++)
            for(let j = c; j < c+3; j++)
                if(Number(game_board[i][j]) === num)
                    return false

        //return true if passed all check
        return true

    }
}

module.exports = {SudokuGameEngine}