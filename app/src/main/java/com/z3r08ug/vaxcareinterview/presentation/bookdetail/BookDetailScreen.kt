package com.z3r08ug.vaxcareinterview.presentation.bookdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.z3r08ug.vaxcareinterview.presentation.base.SIDE_EFFECTS_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val state by viewModel.viewState

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BookDetailContract.Effect.NavigateBack -> onBackClick()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Book Details") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.setEvent(BookDetailContract.Event.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        BookDetailContent(
            state = state,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun BookDetailContent(
    state: BookDetailContract.State,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            state.book?.let { book ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Author: ${book.author}", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Status: ${book.status.displayText}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Fee: $${book.fee}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Last Edited: ${book.lastEdited}", style = MaterialTheme.typography.bodySmall)

                    book.status.timeCheckedIn?.let {
                        Text(text = "Checked In: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    book.status.timeCheckedOut?.let {
                        Text(text = "Checked Out: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                    book.status.dueDate?.let {
                        Text(text = "Due Date: $it", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
