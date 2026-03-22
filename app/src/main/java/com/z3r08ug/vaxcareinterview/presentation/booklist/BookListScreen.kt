package com.z3r08ug.vaxcareinterview.presentation.booklist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.z3r08ug.vaxcareinterview.domain.model.Book
import com.z3r08ug.vaxcareinterview.presentation.base.SIDE_EFFECTS_KEY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    onBookClick: (Int) -> Unit,
    viewModel: BookListViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState
    val context = LocalContext.current

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
            TopAppBar(
                title = { Text(text = "Book List") },
                actions = {
                    TextButton(onClick = { viewModel.setEvent(BookListContract.Event.ToggleSortOrder) }) {
                        Text(
                            text = if (state.sortOrder == BookListContract.SortOrder.ASCENDING) "A-Z" else "Z-A",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                },
            )
        }
    ) { padding ->
        BookListContent(
            state = state,
            modifier = Modifier.padding(padding),
            onRefresh = { viewModel.setEvent(BookListContract.Event.LoadBooks) },
            onFavoriteClick = { viewModel.setEvent(BookListContract.Event.OnFavoriteClicked(it)) },
        ) { viewModel.setEvent(BookListContract.Event.OnBookClicked(it)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListContent(
    state: BookListContract.State,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onFavoriteClick: (Book) -> Unit,
    onBookClick: (Book) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        modifier = modifier.fillMaxSize(),
    ) {
        if ((state.error != null) && !state.isLoading && state.books.isEmpty()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center),
            )
        } else if (state.isLoading && state.books.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.books) { book ->
                    BookItem(
                        book = book,
                        onFavoriteClick = { onFavoriteClick(book) },
                    ) { onBookClick(book) }
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${book.status.displayText}", style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (book.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (book.isFavorite) Color.Red else Color.Gray,
            )
        }
    }
}
