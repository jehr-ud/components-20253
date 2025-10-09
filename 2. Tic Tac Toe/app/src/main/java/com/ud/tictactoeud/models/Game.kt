package com.ud.tictactoeud.models

enum class Mark {
    player1,
    player2,
    Free
}

enum class StatusGame {
    WinnerPlayer1,
    WinnerPlayer2,
    Draw,
    inGame
}

class Board(val level: LevelsType) {
    val size = when(level) {
        LevelsType.normal -> 3
        LevelsType.complex -> 5
    }

    var positions: Array<Mark> = Array(size * size) { Mark.Free }

    val winningCombinations = if (level == LevelsType.normal) {
        listOf(
            // Lineas
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            // Columnas
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            // Diagonales
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
    } else {
        listOf(
            // Lineas
            listOf(0, 1, 2, 3, 4),
            listOf(5, 6, 7, 8, 9),
            listOf(10, 11, 12, 13, 14),
            listOf(15, 16, 17, 18, 19),
            listOf(20, 21, 22, 23, 24),
            // Columnas
            listOf(0, 5, 10, 15, 20),
            listOf(1, 6, 11, 16, 21),
            listOf(2, 7, 12, 17, 22),
            listOf(3, 8, 13, 18, 23),
            listOf(4, 9, 14, 19, 24),
            // Diagonales
            listOf(0, 6, 12, 18, 24),
            listOf(4, 8, 12, 16, 20)
        )
    }

    fun checkOccupation(position: Int): Boolean {
        return positions[position] == Mark.Free
    }

    fun checkStatusBoard(): StatusGame {
        for (combination in winningCombinations) {
            val position1 = checkOccupation(combination[0])
            val position2 = checkOccupation(combination[1])
            val position3 = checkOccupation(combination[2])
            val position4 = if (combination.size > 3) checkOccupation(combination[3]) else true
            val position5 = if (combination.size > 4) checkOccupation(combination[4]) else true

            if (position1 && position2 && position3 && position4 && position5) {
                if (positions[combination[0]] == Mark.player1 &&
                    positions[combination[1]] == Mark.player1 &&
                    positions[combination[2]] == Mark.player1 &&
                    (combination.size < 4 || positions[combination[3]] == Mark.player1) &&
                    (combination.size < 5 || positions[combination[4]] == Mark.player1)
                ) {
                    return StatusGame.WinnerPlayer1
                } else {
                    return StatusGame.WinnerPlayer2
                }
            }
        }

        // Calcular Draw status
        var totalFreePositions = 0
        for (position in positions) {
            if (position == Mark.Free) {
                totalFreePositions++
            }
        }

        if (totalFreePositions == 0) {
            return StatusGame.Draw
        }

        return StatusGame.inGame
    }

    fun reset() {
        positions = Array(size * size) { Mark.Free }
    }

    fun changePosition(position: Int, mark: Mark) {
        positions[position] = mark
    }

    fun getPosition(position: Int): Mark {
        return positions[position]
    }
}



class GameLogic(
    var player1Turn: Boolean,
    var board: Board
) {
    fun changeTurn(position: Int): StatusGame {
        val status = board.checkStatusBoard()

        if (status == StatusGame.inGame) {
            var mark = Mark.player1

            if (!player1Turn) {
                mark = Mark.player2
            }

            board.changePosition(position, mark)
            player1Turn = !player1Turn
        }

        return status
    }

    fun reset() {
        player1Turn = true
        this.board.reset()
    }
}
