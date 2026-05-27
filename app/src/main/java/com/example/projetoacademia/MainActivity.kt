package com.example.projetoacademia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projetoacademia.navigation.AppNavigation
import com.example.projetoacademia.ui.theme.ProjetoAcademiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProjetoAcademiaTheme {
                AppNavigation()
            }
        }
    }
}