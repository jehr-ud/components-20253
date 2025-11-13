package com.ud.myapplication.ui.composables.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ud.myapplication.viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(roomCode: String, viewModel: RoomViewModel = viewModel()) {
    val roomState by viewModel.currentRoom.collectAsState()

    LaunchedEffect(roomCode) {
        viewModel.fetchRoom(roomCode)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Juego - Sala $roomCode") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (roomState == null) {
                CircularProgressIndicator()
                Text("Cargando datos de la sala...")
            } else {
                Text("Host: ${roomState?.hostPlayer ?: "-"}")
                Text("Invitado: ${roomState?.guestPlayer ?: "Esperando..."}")
                Text("Turno actual: ${roomState?.playernTurn ?: "-"}")

                Spacer(modifier = Modifier.height(32.dp))
                Text("lógica del juego 🎮!")
            }
        }
    }
}
