package com.ud.tictactoeud.models

enum class Mark{
    player1,
    player2,
    Free
}

enum class StatusGame{
    WinnerPlayer1,
    WinnerPlayer2,
    Draw,
    inGame
}

class Board {
    var positions: Array<Mark> = Array(9) { Mark.Free }
    val winningCombinations = listOf(
        // lineas
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        // cols
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        // diagonales
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    fun checkOccupation(position: Int): Boolean{
        return positions[position] == Mark.Free
    }

    fun checkStatusBoard(): StatusGame{
        for (combination in winningCombinations){
            val position1 = checkOccupation(combination[0])
            val position2 = checkOccupation(combination[1])
            val position3 = checkOccupation(combination[2])

            if (position1 && position2 && position3){
                if (positions[combination[0]] == Mark.player1 && positions[combination[1]] == Mark.player1 && positions[combination[2]] == Mark.player1) {
                    return StatusGame.WinnerPlayer1
                } else {
                    return StatusGame.WinnerPlayer2
                }
            }
        }

        // calculate Draw status
        var totalFreePositions = 0
        for (position in 0..9){
            if (checkOccupation(position)){
                totalFreePositions++
            }
        }

        if (totalFreePositions == 9 ){
            return StatusGame.Draw
        }

        return StatusGame.inGame

    }

    fun reset(){
        positions = Array(9) { Mark.Free }
    }

    fun changePosition(position: Int, mark: Mark){
        positions[position] = mark
    }

    fun getPosition(position: Int): Mark{
        return positions[position]
    }
}


class GameLogic(
    var player1Turn: Boolean,
    var board: Board) {

    fun changeTurn(position: Int): StatusGame{
        val status = board.checkStatusBoard()

        if (status == StatusGame.inGame){
            var mark = Mark.player1

            if (!player1Turn){
                mark = Mark.player2
            }

            board.changePosition(position, mark)
            player1Turn = !player1Turn
        }

        return status
    }

    fun reset(){
        player1Turn = true
        this.board.reset()
    }
}