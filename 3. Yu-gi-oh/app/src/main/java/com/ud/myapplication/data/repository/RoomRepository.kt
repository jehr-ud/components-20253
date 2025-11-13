package com.ud.myapplication.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.ud.myapplication.data.model.Room

class RoomRepository {
    private val db = FirebaseDatabase.getInstance().getReference("rooms")

    fun createRoom(room: Room, onComplete: (Boolean) -> Unit) {
        val key = db.push().key ?: return
        val roomWithId = room.copy(id = key)
        db.child(key).setValue(roomWithId)
            .addOnCompleteListener { onComplete(it.isSuccessful) }
    }
}
