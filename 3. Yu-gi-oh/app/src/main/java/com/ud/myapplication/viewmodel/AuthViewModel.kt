package com.ud.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signOut(onSignOut: () -> Unit) {
        auth.signOut()

        onSignOut()
    }
}
