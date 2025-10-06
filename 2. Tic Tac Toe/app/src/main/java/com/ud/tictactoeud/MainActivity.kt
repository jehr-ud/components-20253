package com.ud.tictactoeud

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.tictactoeud.models.Board
import com.ud.tictactoeud.models.GameLogic
import com.ud.tictactoeud.ui.theme.TicTacToeUdTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("logud", "application oncreate")
        enableEdgeToEdge()
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

    override fun onStart() {
        super.onStart()
        Log.d("logud", "application start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("logud", "application resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("logud", "application pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("logud", "application destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("logud", "application resart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("logud", "application onstop")
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
    val board = Board()
    val gameLogic = GameLogic(true, board)
    Column {
        Text(
            text = "Player vs machine",
            modifier = modifier
        )

        Column(
            modifier = Modifier
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0..2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (column in 0..2) {
                        Button(
                            onClick = { gameLogic.changeTurn(row)},
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                        ) {
                            Text(text = "(${row + 1}, ${column + 1})", color = Color.White)
                        }
                    }
                }
            }
        }

        Text(
            text = "Status: --",
            modifier = modifier
        )

    }
}

@Preview(showBackground = true)
@Composable
fun Game() {
    TicTacToeUdTheme {
        Game(modifier = Modifier.padding(2.dp))
    }
}