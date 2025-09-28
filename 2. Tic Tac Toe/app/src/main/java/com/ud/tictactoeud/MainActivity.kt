package com.ud.tictactoeud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TicTacToeApp()
            }
        }
    }
}

@Composable
fun TicTacToeApp() {
    var board by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = winner?.let { "Ganador: $it" } ?: "Turno: $currentPlayer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))


        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    Cell(
                        value = board[row][col],
                        onClick = {
                            if (board[row][col].isEmpty() && winner == null) {
                                board = board.mapIndexed { r, rowArray ->
                                    rowArray.mapIndexed { c, cell ->
                                        if (r == row && c == col) currentPlayer else cell
                                    }.toTypedArray()
                                }.toTypedArray()

                                // verificar ganador
                                val w = checkWinner(board)
                                if (w != null) {
                                    winner = w
                                } else if (board.flatten().none { it.isEmpty() }) {
                                    winner = "Empate"
                                } else {
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            board = Array(3) { Array(3) { "" } }
            currentPlayer = "X"
            winner = null
        }) {
            Text("Reiniciar")
        }
    }
}

@Composable
fun Cell(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.White)
            .clickable(enabled = value.isEmpty()) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = value, fontSize = 36.sp, fontWeight = FontWeight.Bold)
    }
}

fun checkWinner(board: Array<Array<String>>): String? {

    for (i in 0..2) {
        if (board[i][0].isNotEmpty() && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return board[i][0]
        if (board[0][i].isNotEmpty() && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return board[0][i]
    }

    if (board[0][0].isNotEmpty() && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[0][0]
    if (board[0][2].isNotEmpty() && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[0][2]
    return null
}