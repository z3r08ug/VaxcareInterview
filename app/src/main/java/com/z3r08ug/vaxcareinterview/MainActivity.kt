package com.z3r08ug.vaxcareinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.z3r08ug.vaxcareinterview.presentation.navigation.AppNavigation
import com.z3r08ug.vaxcareinterview.ui.theme.VaxcareInterviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VaxcareInterviewTheme {
                AppNavigation()
            }
        }
    }
}
