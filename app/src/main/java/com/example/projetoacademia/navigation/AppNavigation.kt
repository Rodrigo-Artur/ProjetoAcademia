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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Projeto Academia",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Menu de navegação",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    HorizontalDivider()

                    DrawerMenuItem(
                        text = "Início",
                        selected = currentRoute == Routes.HOME,
                        onClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    DrawerMenuItem(
                        text = "Alunos",
                        selected = currentRoute == Routes.ALUNOS,
                        onClick = {
                            navegarPeloMenu(navController, Routes.ALUNOS)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    DrawerMenuItem(
                        text = "Planos",
                        selected = currentRoute == Routes.PLANOS,
                        onClick = {
                            navegarPeloMenu(navController, Routes.PLANOS)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    DrawerMenuItem(
                        text = "Treinos",
                        selected = currentRoute == Routes.TREINOS,
                        onClick = {
                            navegarPeloMenu(navController, Routes.TREINOS)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    DrawerMenuItem(
                        text = "Pagamentos",
                        selected = currentRoute == Routes.PAGAMENTOS,
                        onClick = {
                            navegarPeloMenu(navController, Routes.PAGAMENTOS)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    DrawerMenuItem(
                        text = "Sobre",
                        selected = currentRoute == Routes.SOBRE,
                        onClick = {
                            navegarPeloMenu(navController, Routes.SOBRE)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Sistema Academia")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Text(text = "☰")
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
                    AlunosScreen(
                        onVoltarClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                        }
                    )
                }

                composable(Routes.PLANOS) {
                    PlanosScreen(
                        onVoltarClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                        }
                    )
                }

                composable(Routes.TREINOS) {
                    TreinosScreen(
                        onVoltarClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                        }
                    )
                }

                composable(Routes.PAGAMENTOS) {
                    PagamentosScreen(
                        onVoltarClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                        }
                    )
                }

                composable(Routes.SOBRE) {
                    SobreScreen(
                        onVoltarClick = {
                            navegarPeloMenu(navController, Routes.HOME)
                        }
                    )
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
        label = {
            Text(text = text)
        },
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
        popUpTo(Routes.HOME) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}