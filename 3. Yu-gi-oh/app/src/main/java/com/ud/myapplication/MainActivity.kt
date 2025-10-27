package com.ud.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyDrawerScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawerScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Tutorial") }

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
                    label = { Text("Seleccionar cartas") },
                    selected = selectedItem == "Seleccionar cartas",
                    onClick = {
                        selectedItem = "Seleccionar cartas"
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Nombre") },
                    selected = selectedItem == "Nombre",
                    onClick = {
                        selectedItem = "Nombre"
                        scope.launch { drawerState.close() }
                    }
                )
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
                "Seleccionar cartas" -> SeleccionarCartasScreen(Modifier.padding(innerPadding))
                "Nombre" -> NombreScreen(Modifier.padding(innerPadding))
            }
        }
    }
}


@Composable
fun TutorialScreen(modifier: Modifier = Modifier) {
    Text("Pantalla de Tutorial", modifier = modifier.padding(16.dp))
}

@Composable
fun SeleccionarCartasScreen(modifier: Modifier = Modifier) {
    Text("Pantalla de Selección de Cartas", modifier = modifier.padding(16.dp))
}

@Composable
fun NombreScreen(modifier: Modifier = Modifier) {
    Text("Pantalla de Nombre", modifier = modifier.padding(16.dp))
}



@Preview(showBackground = true, name = "Drawer completo")
@Composable
fun PreviewMyDrawerScreen() {
    MyApplicationTheme {
        MyDrawerScreen()
    }
}

@Preview(showBackground = true, name = "Pantalla Tutorial")
@Composable
fun PreviewTutorial() {
    MyApplicationTheme {
        TutorialScreen()
    }
}

@Preview(showBackground = true, name = "Pantalla Seleccionar Cartas")
@Composable
fun PreviewSeleccionarCartas() {
    MyApplicationTheme {
        SeleccionarCartasScreen()
    }
}

@Preview(showBackground = true, name = "Pantalla Nombre")
@Composable
fun PreviewNombre() {
    MyApplicationTheme {
        NombreScreen()
    }
}