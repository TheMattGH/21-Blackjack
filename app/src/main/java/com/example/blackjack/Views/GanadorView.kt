package com.example.blackjack.Views

import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blackjack.Components.BotonPrincipal
import com.example.blackjack.Components.GanadorStatsCard
import com.example.blackjack.Components.TitleBar
import com.example.blackjack.Models.Jugador
import com.example.blackjack.R
import com.example.blackjack.ViewModels.JuegoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GanadorView(
    navController: NavController,
    viewModel: JuegoViewModel
) {
    val ganadores = viewModel.jugadores.filter {
        it.puntos <= 21 && (viewModel.dealer.puntos > 21 || it.puntos > viewModel.dealer.puntos)
    }

    Scaffold(
        containerColor = Color(0xFF1E293B),
        content = { paddingValues ->
            ContenidoGanadorView(
                modifier = Modifier.padding(paddingValues),
                ganadores = ganadores,
                navController = navController,
                mensaje = if (ganadores.isNotEmpty()) "¡Ganaste!" else "No hubo ganadores",
                viewModel = viewModel
            )
        }
    )
}


@Composable
fun ContenidoGanadorView(
    modifier: Modifier = Modifier,
    ganadores: List<Jugador>,
    navController: NavController,
    mensaje: String,
    viewModel: JuegoViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (mensaje.contains("Ganaste", true)) {
                Image(
                    painter = painterResource(id = R.drawable.ganador),
                    contentDescription = "Ganador",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    colorFilter = ColorFilter.tint(Color(0xFFFFD700))
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = mensaje,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ganadores.forEach { jugador ->
            GanadorStatsCard(jugador = jugador)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        BotonPrincipal(
            nombre = "Volver a jugar",
            colorTexto = Color.White,
            colorFondo = Color(0xFF4CAF50),
            onClick = {
                viewModel.repartirCartas()
                navController.popBackStack()
            }
        )
    }
}

