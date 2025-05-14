package com.example.blackjack.Models

data class Jugador(
    val nombre: String,
    val cartas: List<String> = emptyList(),
    val puntos: Int = 0,
    val resultado: String = "",
    val saldo: Int = 1000,
    val apuesta: Int = 100
)
