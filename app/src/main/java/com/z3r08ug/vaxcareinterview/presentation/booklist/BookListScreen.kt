package com.z3r08ug.vaxcareinterview.presentation.booklist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.presentation.base.SIDE_EFFECTS_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    onBookClick: (Int) -> Unit,
    viewModel: BookListViewModel = hiltViewModel()
) {
    val state by viewModel.viewState
    val context = LocalContext.current
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is BookListContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                is BookListContract.Effect.NavigateToDetails -> {
                    onBookClick(effect.bookId)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Book List") })
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.setEvent(BookListContract.Event.LoadBooks) },
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.error != null && !state.isLoading) {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.books) { book ->
                        BookItem(
                            book = book,
                            onClick = {
                                viewModel.setEvent(BookListContract.Event.OnBookClicked(book))
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = book.title, style = MaterialTheme.typography.titleMedium)
        Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Status: ${book.status.displayText}", style = MaterialTheme.typography.bodySmall)
    }
}
