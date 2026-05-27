package com.example.projetoacademia.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projetoacademia.R
import com.example.projetoacademia.screens.AlunosScreen
import com.example.projetoacademia.screens.ExerciciosScreen
import com.example.projetoacademia.screens.HomeScreen
import com.example.projetoacademia.screens.PagamentosScreen
import com.example.projetoacademia.screens.PlanosScreen
import com.example.projetoacademia.screens.SobreScreen
import com.example.projetoacademia.screens.TreinadoresScreen
import com.example.projetoacademia.screens.TreinosScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val tituloAtual = obterTituloDaTela(currentRoute)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF07131C),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_gym_logo),
                                contentDescription = "Logo GymControl",
                                modifier = Modifier.size(42.dp)
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Column {
                                Text(
                                    text = "GymControl",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Black
                                )

                                Text(
                                    text = tituloAtual.uppercase(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
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
                    onNavigateToPagamentos = { navegarPeloMenu(navController, Routes.PAGAMENTOS) },
                    onNavigateToTreinadores = { navegarPeloMenu(navController, Routes.TREINADORES) }
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

            composable(Routes.TREINADORES) {
                TreinadoresScreen(onVoltarClick = { navegarPeloMenu(navController, Routes.HOME) })
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
        NavigationItem("Dashboard", Routes.HOME, R.drawable.ic_gym_logo),
        NavigationItem("Alunos", Routes.ALUNOS, R.drawable.ic_gym_users),
        NavigationItem("Planos", Routes.PLANOS, R.drawable.ic_gym_plan),
        NavigationItem("Exercícios", Routes.EXERCICIOS, R.drawable.ic_gym_dumbbell),
        NavigationItem("Treinadores", Routes.TREINADORES, R.drawable.ic_gym_trainer),
        NavigationItem("Treinos", Routes.TREINOS, R.drawable.ic_gym_dumbbell),
        NavigationItem("Pagamentos", Routes.PAGAMENTOS, R.drawable.ic_gym_money),
        NavigationItem("Sobre", Routes.SOBRE, R.drawable.ic_gym_plan)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itens.forEach { item ->
            val selected = currentRoute == item.route

            FilterChip(
                selected = selected,
                onClick = { onNavigate(item.route) },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.72f),
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                    selectedLabelColor = MaterialTheme.colorScheme.primary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selected,
                    borderColor = Color.White.copy(alpha = 0.10f),
                    selectedBorderColor = MaterialTheme.colorScheme.primary,
                    borderWidth = 1.dp,
                    selectedBorderWidth = 1.dp
                )
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val route: String,
    val icon: Int
)

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
        Routes.TREINADORES -> "Treinadores"
        Routes.TREINOS -> "Treinos"
        Routes.PAGAMENTOS -> "Pagamentos"
        Routes.SOBRE -> "Sobre"
        else -> "Dashboard"
    }
}
