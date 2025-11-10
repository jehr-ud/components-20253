package com.ud.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class RoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mode = Activity.getIntent.getStringExtra("mode") ?: "guest"

        setContent {
            if (mode == "host") {
                HostScreen()
            } else {
                GuestScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen() {
    val roomCode = remember { Random.nextInt(1000, 9999).toString() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Sala del Host") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Tu código de sala es:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text(roomCode, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Text("Comparte este código con tus amigos para que se unan.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestScreen() {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var joined by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Unirse a una Sala") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!joined) {
                Text("Ingresa el código de la sala:")
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Código de Sala") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { joined = true }, enabled = code.text.isNotBlank()) {
                    Text("Unirse")
                }
            } else {
                Text("Conectado a la sala $code ✅", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
