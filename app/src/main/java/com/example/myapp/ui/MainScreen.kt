package com.example.myapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myapp.common.UIState
import com.example.myapp.data.model.MainData
import com.example.myapp.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onItemClick: (MainData) -> Unit
) {
    val state by mainViewModel.mainItem.collectAsStateWithLifecycle()

    when (state) {
        is UIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UIState.Success -> {
            LazyColumn {
                items((state as UIState.Success<List<MainData>>).data) { item ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(item) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = item.urlToImage,
                            contentDescription = item.title,
                            modifier = Modifier.size(100.dp),
                            placeholder = ColorPainter(Color.Gray),
                            error = ColorPainter(Color.Gray)
                        )
                        Text(
                            text = item.title,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        is UIState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (state as UIState.Failure).throwable.toString())
            }
        }
        is UIState.Empty -> {
            // Handle empty state if needed
        }
    }
}
