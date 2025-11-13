package com.ud.myapplication.data.model

data class Room(
    val id: String = "",
    val hostPlayer: String = "",
    val guestPlayer: String? = null,
    val isActive: Boolean = true,
    val playerTurn: String? = null
)
