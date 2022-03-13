package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DiaryView: ViewModel() {
    var entries = mutableStateOf(mutableListOf<DiaryData>())

    fun addEntry(note :DiaryData){
        var newEntries = entries.value.toMutableList()
        newEntries.add(note)
        entries.value = newEntries
        Firebase.firestore.collection("diary").add(note)
    }

}