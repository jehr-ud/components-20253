package com.ud.tictactoeud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ud.tictactoeud.models.GameLogic
import com.ud.tictactoeud.ui.theme.TicTacToeUdTheme
import com.ud.tictactoeud.models.StatusGame

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeUdTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Game(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



@Composable
fun Game(modifier: Modifier = Modifier) {

    val gameLogic by remember { mutableStateOf(GameLogic()) }
    var board by remember { mutableStateOf(gameLogic.board) }
    var gameStatus by remember { mutableStateOf(gameLogic.board.checkStatusBoard()) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Player vs machine",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            (0..8).chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    rowItems.forEach { position ->
                        Button(

                            onClick = {
                                if (gameStatus == StatusGame.inGame) {
                                    gameLogic.playMove(position)
                                    board = gameLogic.board.copy()
                                    gameStatus = gameLogic.board.checkStatusBoard()
                                }
                            },
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp),

                            enabled = board.positions[position] == com.ud.tictactoeud.models.Mark.Free && gameStatus == StatusGame.inGame
                        ) {
                            Text(
                                text = when (board.positions[position]) {
                                    com.ud.tictactoeud.models.Mark.player1 -> "X"
                                    com.ud.tictactoeud.models.Mark.player2 -> "O"
                                    else -> ""
                                },
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }


        val statusText = when (gameStatus) {
            StatusGame.WinnerPlayer1 -> "WINNER: Jugador 1 (X)"
            StatusGame.WinnerPlayer2 -> "WINNER: Jugador 2 (O)"
            StatusGame.Draw -> "EMPATE!!!!!!!!"
            StatusGame.inGame -> "En juego "
        }

        Text(
            text = "Estado: $statusText",
            modifier = Modifier.padding(top = 24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Button(
            onClick = {
                gameLogic.reset()
                Log.d("logud", "Juego reiniciado")
            },
            modifier = Modifier.padding(top = 16.dp)

        ) {
            Text("Reiniciar juego")
        }


        if (gameStatus != StatusGame.inGame) {
            Button(
                onClick = {
                    gameLogic.reset()
                    board = gameLogic.board.copy()
                    gameStatus = StatusGame.inGame
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Jugar de nuevo")
            }
        }
    }
}