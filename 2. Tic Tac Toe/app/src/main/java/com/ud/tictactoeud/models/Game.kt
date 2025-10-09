
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


data class Board(
    var positions: Array<Mark> = Array(9) { Mark.Free }
) {
    private val winningCombinations = listOf(

        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),

        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),

        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    fun checkStatusBoard(): StatusGame {
        for (combination in winningCombinations) {
            val pos1 = positions[combination[0]]
            val pos2 = positions[combination[1]]
            val pos3 = positions[combination[2]]


            if (pos1 != Mark.Free && pos1 == pos2 && pos2 == pos3) {
                return if (pos1 == Mark.player1) StatusGame.WinnerPlayer1 else StatusGame.WinnerPlayer2
            }
        }


        if (positions.none { it == Mark.Free }) {
            return StatusGame.Draw
        }

        return StatusGame.inGame
    }

    fun reset() {
        positions = Array(9) { Mark.Free }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Board
        return positions.contentEquals(other.positions)
    }

    override fun hashCode(): Int {
        return positions.contentHashCode()
    }
}


class GameLogic(
    var player1Turn: Boolean = true,
    var board: Board = Board()
) {

    fun playMove(position: Int) {

        if (board.positions[position] == Mark.Free && board.checkStatusBoard() == StatusGame.inGame) {
            val mark = if (player1Turn) Mark.player1 else Mark.player2
            board.positions[position] = mark
            player1Turn = !player1Turn
        }
    }

    fun reset() {
        player1Turn = true
        this.board.reset()
    }
}
