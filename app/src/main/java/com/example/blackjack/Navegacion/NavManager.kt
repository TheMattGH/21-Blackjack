package com.example.blackjack.Navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blackjack.ViewModels.JuegoViewModel
import com.example.blackjack.Views.ContenidoGanadorView
import com.example.blackjack.Views.GanadorView
import com.example.blackjack.Views.JuegoView
import com.example.blackjack.Views.PantallaPrincipalView

@Composable
fun NavManager(viewModel: JuegoViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable ("Home"){
            PantallaPrincipalView(navController)
        }
        composable("juego") {
            JuegoView(viewModel, navController)
        }
        composable("ganador") {
            GanadorView(navController = navController, viewModel = viewModel)
        }

    }

}