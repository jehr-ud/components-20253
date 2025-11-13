package com.ud.myapplication.data.model

data class Card(
    val name: String,
    val type: String,
    val description: String,
    val attack: Int,
    val defense: Int,
    val imageUrl: String // You can use a URL for the card image
)
