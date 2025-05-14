package com.example.blackjack.Views

import com.example.blackjack.R
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blackjack.Components.BotonPrincipal
import com.example.blackjack.Components.DialogoInstrucciones
import com.example.blackjack.Components.TitleBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Brush
import com.example.blackjack.Components.BotonPrincipal2


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaPrincipalView(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    TitleBar(name = "BLACKJACK")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color(0xFFFFD700)
                )
            )
        }
    ) { padding ->
        ContenidoPantallaPrincipal(
            modifier = Modifier.padding(padding),
            navController = navController
        )
    }
}

@Composable
fun ContenidoPantallaPrincipal(modifier: Modifier = Modifier, navController: NavController) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B6623),
                        Color(0xFF1B4D3E)
                    )
                )
            )
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_blackjack),
                contentDescription = "Logo Blackjack",
                modifier = Modifier
                    .size(180.dp)
            )

            BotonPrincipal2(
                nombre = "Jugar",
                colorTexto = Color.Black,
                colorFondo = Color(0xFFDAA520),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp)
            ) {
                navController.navigate("juego")
            }

            BotonPrincipal2(
                nombre = "Instrucciones",
                colorTexto = Color.White,
                colorFondo = Color(0xFFB22222),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp)
            ) {
                mostrarDialogo = true
            }

        }

        DialogoInstrucciones(
            mostrar = mostrarDialogo,
            cerrarDialogo = { mostrarDialogo = false }
        )
    }
}



