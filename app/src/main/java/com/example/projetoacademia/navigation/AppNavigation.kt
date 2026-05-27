package com.example.projetoacademia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projetoacademia.screens.AlunosScreen
import com.example.projetoacademia.screens.HomeScreen
import com.example.projetoacademia.screens.PagamentosScreen
import com.example.projetoacademia.screens.PlanosScreen
import com.example.projetoacademia.screens.SobreScreen
import com.example.projetoacademia.screens.TreinosScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        // aqui diz que a tela principal é a HOME
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                // basicamente quando clica usa a função navigate para enviar para a tela alunos
                onAlunosClick = {
                    navController.navigate(Routes.ALUNOS)
                },
                onPlanosClick = {
                    navController.navigate(Routes.PLANOS)
                },
                onTreinosClick = {
                    navController.navigate(Routes.TREINOS)
                },
                onPagamentosClick = {
                    navController.navigate(Routes.PAGAMENTOS)
                },
                onSobreClick = {
                    navController.navigate(Routes.SOBRE)
                }
            )
        }

        composable(Routes.ALUNOS) {
            AlunosScreen(
                onVoltarClick = {
                    //"popBackStack()" faz voltar para a tela anterior
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.PLANOS) {
            PlanosScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.TREINOS) {
            TreinosScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.PAGAMENTOS) {
            PagamentosScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.SOBRE) {
            SobreScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}