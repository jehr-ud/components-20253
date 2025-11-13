package com.ud.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ud.myapplication.SessionManager
import com.ud.myapplication.ui.composables.profile.ProfileScreen
import com.ud.myapplication.ui.composables.room.RoomMenuScreen
import com.ud.myapplication.ui.theme.MyApplicationTheme
import com.ud.myapplication.ui.composables.tutorial.TutorialScreen
import com.ud.myapplication.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val session = SessionManager(this)
        setContent {
            MyApplicationTheme {
                MyDrawerScreen(session)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawerScreen(sessionManager: SessionManager) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Tutorial") }
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú principal", modifier = Modifier.padding(16.dp))
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Tutorial") },
                    selected = selectedItem == "Tutorial",
                    onClick = {
                        selectedItem = "Tutorial"
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Salas") },
                    selected = selectedItem == "Salas",
                    onClick = {
                        selectedItem = "Salas"
                        scope.launch { drawerState.close() }
                    }
                )

                if (sessionManager.getEmail() != null) {
                    NavigationDrawerItem(
                        label = { sessionManager.getEmail()?.let { Text(it) } },
                        selected = selectedItem == sessionManager.getEmail(),
                        onClick = {
                            selectedItem = "Nombre"
                            scope.launch { drawerState.close() }
                        }
                    )

                    HorizontalDivider()

                    NavigationDrawerItem(
                        label = { Text("Cerrar sesion") },
                        selected = selectedItem == sessionManager.getEmail(),
                        onClick = {
                            selectedItem = "Nombre"
                            scope.launch {
                                authViewModel.signOut {
                                    sessionManager.clearSession()
                                    val intent = Intent(context, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    context.startActivity(intent)
                            }}
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = { Text(selectedItem) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            when (selectedItem) {
                "Tutorial" -> TutorialScreen(Modifier.padding(innerPadding))
                "Salas" -> RoomMenuScreen(Modifier.padding(innerPadding), sessionManager=sessionManager)
                "Nombre" -> ProfileScreen(Modifier.padding(innerPadding))
            }
        }
    }
}
