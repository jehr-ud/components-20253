package com.ud.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.ud.myapplication.SessionManager
import com.ud.myapplication.data.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().getReference("rooms")

    private val _currentRoom = MutableStateFlow<Room?>(null)
    val currentRoom: StateFlow<Room?> = _currentRoom

    fun createRoom(roomCode: String, session: SessionManager, onResult: (Boolean) -> Unit) {
        val host = session.getUserId().toString()
        val room = Room(id = roomCode, hostPlayer = host, playerTurn = host)

        database.child(roomCode)
            .setValue(room)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun joinRoom(roomCode: String, session: SessionManager, onResult: (Boolean) -> Unit) {
        val userId = session.getUserId()
        val roomRef = database.child(roomCode)

        roomRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                roomRef.child("guestPlayer").setValue(userId)
                    .addOnSuccessListener { onResult(true) }
                    .addOnFailureListener { onResult(false) }
            } else {
                onResult(false)
            }
        }.addOnFailureListener {
            onResult(false)
        }
    }

    fun listenForGuest(roomCode: String, onGuestJoined: () -> Unit) {
        val roomRef = database.child(roomCode)
        roomRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val room = snapshot.getValue(Room::class.java)
                if (room?.guestPlayer != null) {
                    onGuestJoined()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun fetchRoom(roomCode: String) {
        viewModelScope.launch {
            database.child(roomCode).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val room = snapshot.getValue(Room::class.java)
                    _currentRoom.value = room
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}
