package com.z3r08ug.vaxcareinterview.presentation.greeting

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.z3r08ug.vaxcareinterview.presentation.base.SIDE_EFFECTS_KEY

@Composable
fun GreetingScreen(
    viewModel: GreetingViewModel = hiltViewModel()
) {
    val state by viewModel.viewState
    val context = LocalContext.current

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GreetingContract.Effect.ShowToast -> {
                    Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        GreetingContent(
            name = state.name,
            modifier = Modifier.padding(innerPadding),
            onButtonClicked = {
                viewModel.setEvent(GreetingContract.Event.OnButtonClicked)
            }
        )
    }
}

@Composable
fun GreetingContent(
    name: String,
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Text(text = "Hello $name!")
        Button(onClick = onButtonClicked) {
            Text(text = "Click Me")
        }
    }
}
