package com.example.projetoacademia.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val tituloAtual = obterTituloDaTela(currentRoute)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sistema Academia",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Use este menu para navegar entre as páginas.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    HorizontalDivider()

                    DrawerMenuItem("Início", currentRoute == Routes.HOME) {
                        navegarPeloMenu(navController, Routes.HOME)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Alunos", currentRoute == Routes.ALUNOS) {
                        navegarPeloMenu(navController, Routes.ALUNOS)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Planos", currentRoute == Routes.PLANOS) {
                        navegarPeloMenu(navController, Routes.PLANOS)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Exercícios", currentRoute == Routes.EXERCICIOS) {
                        navegarPeloMenu(navController, Routes.EXERCICIOS)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Treinos", currentRoute == Routes.TREINOS) {
                        navegarPeloMenu(navController, Routes.TREINOS)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Pagamentos", currentRoute == Routes.PAGAMENTOS) {
                        navegarPeloMenu(navController, Routes.PAGAMENTOS)
                        scope.launch { drawerState.close() }
                    }

                    DrawerMenuItem("Sobre", currentRoute == Routes.SOBRE) {
                        navegarPeloMenu(navController, Routes.SOBRE)
                        scope.launch { drawerState.close() }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Sistema Academia - $tituloAtual",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Text(text = "☰", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Routes.HOME) {
                    HomeScreen()
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
}

@Composable
fun DrawerMenuItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = text) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(top = 8.dp)
    )
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
        Routes.ALUNOS -> "Alunos"
        Routes.PLANOS -> "Planos"
        Routes.EXERCICIOS -> "Exercícios"
        Routes.TREINOS -> "Treinos"
        Routes.PAGAMENTOS -> "Pagamentos"
        Routes.SOBRE -> "Sobre"
        else -> "Início"
    }
}
