package com.ud.myapplication.ui.composables.tutorial

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TutorialScreen(modifier: Modifier = Modifier) {
    Text("Pantalla de Tutorial", modifier = modifier.padding(16.dp))
}
