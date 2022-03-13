package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserView: ViewModel() {
    var username = mutableStateOf("Testing")
    fun userLogin(email: String, password: String){
        Firebase.auth
            .signInWithEmailAndPassword(email,password)
            .addOnSuccessListener { username.value = email }

    }

    fun userLogout(){
        Firebase.auth.signOut()
        username.value = ""
    }
}