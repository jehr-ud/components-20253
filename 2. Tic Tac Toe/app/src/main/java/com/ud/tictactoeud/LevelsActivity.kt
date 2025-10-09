package com.ud.tictactoeud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.ud.tictactoeud.models.LevelsType
import com.ud.tictactoeud.ui.theme.TicTacToeUdTheme

class LevelsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelsScreen()
        }
    }
}

@Composable
fun LevelsScreen() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(text = "Levels", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { var level = LevelsType.normal.name
            val intent = Intent(context, MainActivity ::class.java)
            intent.putExtra("level",level)
            context.startActivity(intent)}) {
            Text(text = "Normal")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { var level = LevelsType.complex.name
            val intent = Intent(context, MainActivity ::class.java)
            intent.putExtra("level",level)
            context.startActivity(intent) }) {
            Text(text = "Complex")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicTacToeUdTheme {
        MainActivity()
    }
}
