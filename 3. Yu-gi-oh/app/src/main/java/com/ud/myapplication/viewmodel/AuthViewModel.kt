package com.ud.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState


    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        _authState.value = AuthState.Success(user?.uid, user?.email)
                    } else {
                        _authState.value =
                            AuthState.Error(task.exception?.message ?: "Error en autenticación")
                    }
                }
        }
    }


    fun signOut(onSignOut: () -> Unit) {
        auth.signOut()
        _authState.value = AuthState.Idle
        onSignOut()
    }
}


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: String?, val email: String?) : AuthState()
    data class Error(val message: String) : AuthState()
}

