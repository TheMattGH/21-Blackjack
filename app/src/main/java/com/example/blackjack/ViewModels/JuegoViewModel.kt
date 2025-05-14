package com.example.blackjack.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.blackjack.Models.Jugador
import com.example.blackjack.Models.Turno
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JuegoViewModel : ViewModel() {

    var estaProcesando by mutableStateOf(false)

    var banco by mutableStateOf(1_000_000)
    var jugadores by mutableStateOf(listOf<Jugador>())
        private set

    var dealer by mutableStateOf(Jugador("Dealer"))
        private set

    var turnoActual by mutableStateOf(Turno.JUGADOR1)
        private set

    var revelarDealer by mutableStateOf(false)
        private set

    private val mazo = mutableListOf<String>()

    init {
        iniciarMazo()
        repartirCartas()
    }

    private fun iniciarMazo() {
        val palos = listOf("corazones", "picas", "trebol", "diamantes")
        val valores = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        mazo.clear()
        for (palo in palos) {
            for (valor in valores) {
                val carta = if (valor in listOf("A", "J", "Q", "K")) {
                    "${valor.lowercase()}_$palo"
                } else {
                    "c${valor}_$palo"
                }
                mazo.add(carta)
            }
        }
        mazo.shuffle()
    }

    private fun sacarCarta(): String {
        if (mazo.isEmpty()) iniciarMazo()
        return mazo.removeAt(0)
    }

    fun repartirCartas() {
        val j1 = Jugador(
            nombre = "Jugador 1",
            cartas = listOf(sacarCarta())
        )
        val j2 = Jugador(
            nombre = "Jugador 2",
            cartas = listOf(sacarCarta())
        )
        jugadores = listOf(
            j1.copy(puntos = calcularPuntaje(j1.cartas)),
            j2.copy(puntos = calcularPuntaje(j2.cartas))
        )

        dealer = Jugador(
            nombre = "Dealer",
            cartas = listOf(sacarCarta())
        ).copy(puntos = calcularPuntaje(dealer.cartas))

        turnoActual = Turno.JUGADOR1
        revelarDealer = false
    }

    fun pedirCarta(navController: NavController) {
        val turnoIndex = when (turnoActual) {
            Turno.JUGADOR1 -> 0
            Turno.JUGADOR2 -> 1
            else -> return
        }

        val jugador = jugadores[turnoIndex]
        val nuevaMano = jugador.cartas + sacarCarta()
        val nuevosPuntos = calcularPuntaje(nuevaMano)

        jugadores = jugadores.toMutableList().also {
            it[turnoIndex] = jugador.copy(
                cartas = nuevaMano,
                puntos = nuevosPuntos,
                resultado = if (nuevosPuntos > 21) "Perdiste (te pasaste)" else jugador.resultado
            )
        }

        if (nuevosPuntos > 21) {
            if (turnoActual == Turno.JUGADOR1) turnoActual = Turno.JUGADOR2
            else jugarDealer(navController)
        }
    }

    fun plantarse(navController: NavController) {
        turnoActual = when (turnoActual) {
            Turno.JUGADOR1 -> Turno.JUGADOR2
            Turno.JUGADOR2 -> {
                viewModelScope.launch {
                    delay(1000)
                    jugarDealer(navController)
                }
                Turno.FINAL
            }

            else -> turnoActual
        }
    }

    private fun jugarDealer(navController: NavController) {
        revelarDealer = true
        var cartas = dealer.cartas
        var puntos = dealer.puntos
        while (puntos < 17) {
            cartas = cartas + sacarCarta()
            puntos = calcularPuntaje(cartas)
        }
        dealer = dealer.copy(cartas = cartas, puntos = puntos)
        evaluarResultados(navController)
    }

    private fun calcularPuntaje(cartas: List<String>): Int {
        var puntos = 0
        var ases = 0

        for (carta in cartas) {
            val valor = carta.split("_")[0]
            puntos += when (valor) {
                "a" -> {
                    ases += 1
                    11
                }

                "j", "q", "k" -> 10
                else -> valor.removePrefix("c").toInt()
            }
        }

        while (puntos > 21 && ases > 0) {
            puntos -= 10
            ases--
        }

        return puntos
    }


    private fun evaluarResultados(navController: NavController) {
        jugadores = jugadores.map { jugador ->
            val tieneBJ = tieneBlackjack(jugador.cartas)
            val resultado: String
            val nuevoSaldo: Int
            var ganancia = 0

            if (tieneBJ) {
                resultado = "¡Blackjack!"
                ganancia = jugador.apuesta * 2
                nuevoSaldo = jugador.saldo + ganancia
                banco -= ganancia
            } else if (jugador.puntos > 21) {
                resultado = "Perdiste"
                ganancia = -jugador.apuesta
                nuevoSaldo = jugador.saldo + ganancia
                banco += jugador.apuesta
            } else if (dealer.puntos > 21 || jugador.puntos > dealer.puntos) {
                resultado = "¡Ganaste!"
                ganancia = jugador.apuesta
                nuevoSaldo = jugador.saldo + ganancia
                banco -= jugador.apuesta
            } else if (jugador.puntos == dealer.puntos) {
                resultado = "Empate"
                nuevoSaldo = jugador.saldo
            } else {
                resultado = "Perdiste"
                ganancia = -jugador.apuesta
                nuevoSaldo = jugador.saldo + ganancia
                banco += jugador.apuesta
            }

            jugador.copy(resultado = resultado, saldo = nuevoSaldo)

        }
        viewModelScope.launch {
            delay(3000)
            navController.navigate("ganador")
        }


    }


    private fun tieneBlackjack(cartas: List<String>): Boolean {
        if (cartas.size != 2) return false

        val valores = cartas.map { it.split("_")[0] }

        return (
                "a" in valores &&
                        (listOf("10", "j", "q", "k", "c10") intersect valores.toSet()).isNotEmpty()
                )
    }

    fun actualizarApuesta(index: Int, nuevaApuesta: Int) {
        jugadores = jugadores.toMutableList().also {
            val jugador = it[index]
            it[index] = jugador.copy(apuesta = nuevaApuesta)
        }
    }



    fun pedirCartaSegura(navController: NavController) {
        if (estaProcesando) return

        estaProcesando = true
        viewModelScope.launch {
            delay(300)
            pedirCarta(navController)
            estaProcesando = false
        }
    }


}



