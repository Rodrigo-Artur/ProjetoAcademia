package com.example.projetoacademia.navigation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projetoacademia.screens.AlunosScreen
import com.example.projetoacademia.screens.ExerciciosScreen
import com.example.projetoacademia.screens.HomeScreen
import com.example.projetoacademia.screens.PagamentosScreen
import com.example.projetoacademia.screens.PlanosScreen
import com.example.projetoacademia.screens.SobreScreen
import com.example.projetoacademia.screens.TreinosScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val tituloAtual = obterTituloDaTela(currentRoute)

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "Sistema Academia - $tituloAtual",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )

                NavigationCarousel(
                    currentRoute = currentRoute,
                    onNavigate = { route -> navegarPeloMenu(navController, route) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onNavigateToAlunos = { navegarPeloMenu(navController, Routes.ALUNOS) },
                    onNavigateToPlanos = { navegarPeloMenu(navController, Routes.PLANOS) },
                    onNavigateToPagamentos = { navegarPeloMenu(navController, Routes.PAGAMENTOS) }
                )
            }

            composable(Routes.ALUNOS) {
                AlunosScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }

            composable(Routes.PLANOS) {
                PlanosScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }

            composable(Routes.EXERCICIOS) {
                ExerciciosScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }

            composable(Routes.TREINOS) {
                TreinosScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }

            composable(Routes.PAGAMENTOS) {
                PagamentosScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }

            composable(Routes.SOBRE) {
                SobreScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
            }
        }
    }
}

@Composable
fun NavigationCarousel(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val itens = listOf(
        "Dashboard" to Routes.HOME,
        "Alunos" to Routes.ALUNOS,
        "Planos" to Routes.PLANOS,
        "Exercícios" to Routes.EXERCICIOS,
        "Treinos" to Routes.TREINOS,
        "Pagamentos" to Routes.PAGAMENTOS,
        "Sobre" to Routes.SOBRE
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itens.forEach { item ->
            FilterChip(
                selected = currentRoute == item.second,
                onClick = { onNavigate(item.second) },
                label = {
                    Text(
                        text = item.first,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    }
}

fun navegarPeloMenu(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        popUpTo(Routes.HOME) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun obterTituloDaTela(route: String?): String {
    return when (route) {
        Routes.HOME -> "Dashboard"
        Routes.ALUNOS -> "Alunos"
        Routes.PLANOS -> "Planos"
        Routes.EXERCICIOS -> "Exercícios"
        Routes.TREINOS -> "Treinos"
        Routes.PAGAMENTOS -> "Pagamentos"
        Routes.SOBRE -> "Sobre"
        else -> "Dashboard"
    }
}
