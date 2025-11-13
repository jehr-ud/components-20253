package com.ud.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.ud.myapplication.SessionManager
import com.ud.myapplication.viewmodel.AuthState
import com.ud.myapplication.viewmodel.AuthViewModel


class LoginActivity : ComponentActivity() {
    private lateinit var session: SessionManager
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = SessionManager(this)

        if (session.isLoggedIn()) goToGame()

        setContent {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { uid, email ->
                    session.saveUser(uid, email)
                    goToGame()
                }
            )
        }
    }

    private fun goToGame() {
        startActivity(Intent(this, GameActivity::class.java))
        finish()
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: (String?, String?) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state by viewModel.authState.collectAsState()

    LaunchedEffect(state) {
        when (val s = state) {
            is AuthState.Success -> onLoginSuccess(s.userId, s.email)
            is AuthState.Error -> Toast.makeText(context, s.message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Sign in")
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { viewModel.signIn(email, password) },
            enabled = state !is AuthState.Loading
        ) {
            if (state is AuthState.Loading)
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            else
                Text("Login")
        }
    }
}
