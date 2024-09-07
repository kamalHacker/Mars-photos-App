package com.example.marsphotosapp.ui.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotosapp.R
import com.example.marsphotosapp.network.MarsPhoto
import com.example.marsphotosapp.ui.theme.MarsPhotosTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    marsViewModel: MarsViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    var marsUiState by remember { mutableStateOf<MarsUiState>(MarsUiState.Loading)}

    LaunchedEffect(Unit) {
        marsViewModel.marsUiState.collect { newState ->
            marsUiState = newState // Update the value within the State
        }
    }

    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxWidth())
        is MarsUiState.Success -> PhotosGridScreen(
            photo = (marsUiState as MarsUiState.Success).photos,
            modifier = modifier.fillMaxWidth(),
            contentPadding = contentPadding
        )
        else -> ErrorScreen(
            retryAction = retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}


@Composable
fun PhotosGridScreen(
    photo: List<MarsPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ){
        items(items = photo, key = {photo -> photo.id}) { photo ->
            MarsPhotoCard(
                photo = photo,
                modifier = modifier.padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
        }
    }
}


@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Log.d("MarsPhotoCard", "Photo URL: ${photo.img_src}")
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.img_src)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.mars_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
            onError = { exception ->
                Log.e("AsyncImage", "Error loading image: $exception")
            }
        )
    }
}

@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    MarsPhotosTheme {
        ResultScreen(
            stringResource(R.string.placeholder_result)
        )
    }
}