package com.example.blackjack.Views

import com.example.blackjack.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blackjack.Components.BotonPrincipal
import com.example.blackjack.Components.CartaView
import com.example.blackjack.Components.TitleBar
import com.example.blackjack.Models.Turno
import com.example.blackjack.ViewModels.JuegoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.blackjack.Components.DealerStatsCard
import com.example.blackjack.Components.JugadorStatsCard
import com.example.blackjack.Components.MainIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoView(viewModel: JuegoViewModel, navController: NavController) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TitleBar(name = "Blackjack")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1E293B),
                    titleContentColor = Color(0xFFFFD700)
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.AutoMirrored.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }

            )
        }
    ) { paddingValues ->
        ContenidoJuego(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenidoJuego(modifier: Modifier = Modifier, viewModel: JuegoViewModel, navController: NavController) {
    val jugadorIndexTurno = when(viewModel.turnoActual) {
        Turno.JUGADOR1 -> 0
        Turno.JUGADOR2 -> 1
        else -> -1
    }

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Dealer", color = Color.White, fontWeight = FontWeight.Bold)
                Row {
                    viewModel.dealer.cartas.forEachIndexed { i, carta ->
                        CartaView(
                            nombre = if (i == 0 && !viewModel.revelarDealer) "carta_oculta" else carta
                        )
                    }
                }
                DealerStatsCard(
                    puntos = viewModel.dealer.puntos,
                    banco = viewModel.banco,
                    mostrarPuntos = viewModel.revelarDealer
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            viewModel.jugadores.forEachIndexed { index, jugador ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = jugador.nombre + if (viewModel.turnoActual.ordinal == index) " (Tu turno)" else "",
                        color = if (viewModel.turnoActual.ordinal == index) Color(0xFFFFD700) else Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        jugador.cartas.forEach { carta ->
                            Log.d("CARTAS", "${jugador.nombre} tiene: $carta")
                            CartaView(nombre = carta)
                        }
                    }

                    JugadorStatsCard(
                        puntos = jugador.puntos,
                        saldo = jugador.saldo,
                        apuesta = jugador.apuesta,
                        resultado = jugador.resultado
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }


            Spacer(modifier = Modifier.height(20.dp))





            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonModifier = Modifier
                    .height(48.dp)
                    .weight(0.50f)
                if (jugadorIndexTurno in viewModel.jugadores.indices) {
                    var expanded by remember { mutableStateOf(false) }
                    val jugador = viewModel.jugadores[jugadorIndexTurno]
                    val opciones = listOf(100, 200, 300, 400, 500).filter { it <= jugador.saldo }
                    val seleccionActual = jugador.apuesta.toString()

                    BotonPrincipal(
                        nombre = "Pedir",
                        colorTexto = Color.Black,
                        colorFondo = Color(0xFFFFD700),
                        modifier = buttonModifier,
                        onClick = { viewModel.pedirCartaSegura(navController) },
                        enabled = jugadorIndexTurno != -1
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    BotonPrincipal(
                        nombre = "Plantarse",
                        colorTexto = Color.White,
                        colorFondo = Color(0xFFB22222),
                        modifier = buttonModifier,
                        onClick = { viewModel.plantarse(navController) },
                        enabled = jugadorIndexTurno != -1
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = buttonModifier
                    ) {
                        TextField(
                            value = "$$seleccionActual",
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFF2E7D32),
                                focusedContainerColor = Color(0xFF388E3C),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                disabledContainerColor = Color(0xFFAAAAAA),
                                disabledTextColor = Color.DarkGray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opciones.forEach { valor ->
                                DropdownMenuItem(
                                    text = { Text("\$$valor") },
                                    onClick = {
                                        viewModel.actualizarApuesta(jugadorIndexTurno, valor)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

        }

    }
}


