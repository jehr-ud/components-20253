package com.ud.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuScreen(
                onCreateRoom = {
                    val intent = Intent(this, RoomActivity::class.java)
                    intent.putExtra("mode", "host")
                    Activity.startActivity(intent)
                },
                onJoinRoom = {
                    val intent = Intent(this, RoomActivity::class.java)
                    intent.putExtra("mode", "guest")
                    Activity.startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onCreateRoom: () -> Unit, onJoinRoom: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menú Principal") })
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
