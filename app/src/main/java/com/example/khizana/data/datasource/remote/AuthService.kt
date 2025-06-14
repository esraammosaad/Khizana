package com.example.khizana.data.datasource.remote

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import javax.inject.Inject

class AuthService @Inject constructor() {

    private var auth: FirebaseAuth = Firebase.auth
    private var currentUser : FirebaseUser? = null

    init {
        currentUser = auth.currentUser
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun logout(){
        auth.signOut()
    }
}