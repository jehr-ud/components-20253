package com.ud.myapplication.ui.composables.room

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ud.myapplication.SessionManager
import com.ud.myapplication.ui.composables.game.GameScreen
import com.ud.myapplication.viewmodel.RoomViewModel
import kotlin.random.Random

@Composable
fun RoomMenuScreen(modifier: Modifier = Modifier, viewModel: RoomViewModel = viewModel(), sessionManager: SessionManager) {
    var showHost by remember { mutableStateOf(false) }
    var showGuest by remember { mutableStateOf(false) }

    when {
        showHost -> HostScreen(
            viewModel = viewModel,
            sessionManager = sessionManager,
            onRoomCreated = { roomCode ->
                println("Sala creada con código: $roomCode")
            }
        )

        showGuest -> GuestScreen(viewModel, sessionManager = sessionManager,)

        else -> ModeSelection(
            onCreateRoom = { showHost = true },
            onJoinRoom = { showGuest = true }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeSelection(onCreateRoom: () -> Unit, onJoinRoom: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menú de Salas") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onCreateRoom, modifier = Modifier.fillMaxWidth()) {
                Text("Crear Sala")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onJoinRoom, modifier = Modifier.fillMaxWidth()) {
                Text("Unirse a una Sala")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen(
    viewModel: RoomViewModel = viewModel(),
    sessionManager: SessionManager,
    onRoomCreated: (String) -> Unit
) {
    val roomCode = remember { Random.nextInt(1000, 9999).toString() }
    var createdCode by remember { mutableStateOf(false) }
    var startGame by remember { mutableStateOf(false) }

    if (startGame) {
        GameScreen(roomCode, viewModel)
        return
    }

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
            if (createdCode) {
                Text("Tu código de sala es:", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(12.dp))
                Text(roomCode, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(32.dp))
                Text("Esperando jugadores para unirse...")
            }

            Button(onClick = {
                viewModel.createRoom(roomCode, sessionManager) { success ->
                    if (success) {
                        onRoomCreated(roomCode)
                        createdCode = true

                        // Escuchar si el guest entra
                        viewModel.listenForGuest(roomCode) {
                            startGame = true
                        }
                    }
                }
            }) {
                Text(if (createdCode) "Sala creada" else "Crear Sala")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestScreen(viewModel: RoomViewModel, sessionManager: SessionManager,) {
    var code by remember { mutableStateOf(TextFieldValue("")) }
    var joined by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                Button(onClick = {
                    viewModel.joinRoom(code.text, sessionManager) { success ->
                        if (success) {
                            joined = true

                        } else {
                            Toast.makeText(context, "No se pudo unir a la sala", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, enabled = code.text.isNotBlank()) {
                    Text("Unirse")
                }
            } else {
                Text("Conectado a la sala ${code.text} ✅", style = MaterialTheme.typography.headlineSmall)
                GameScreen(code.text, viewModel)
            }
        }
    }
}