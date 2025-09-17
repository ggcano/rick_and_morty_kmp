package org.example.rickandmortykmp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.CharacterDTO
import model.Result
import org.example.rickandmortykmp.NetWorkingUtils.httpClient
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    val scope = rememberCoroutineScope()
    var results by remember { mutableStateOf<List<Result>>(emptyList()) }
    MaterialTheme {

        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .build()
        }
        LaunchedEffect(Unit) {

            getListFromDTO(
                scope = scope,
                onSuccessResponse = { list -> results = list },
                onError = { e -> println("Error: $e") }
            )
        }

        if (results.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Cargando personajes...")
            }
        } else {
            LazyColumn {
                items(results) { result ->
                    ItemRow(result)
                }
            }
        }
    }
}


@Composable
fun ItemRow(result: Result) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(3.dp, Color.Yellow, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = result.image,
            contentDescription = result.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Yellow),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = result.name, // aquí mostramos el nombre
                color = Color.DarkGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun getListFromDTO(
    scope: CoroutineScope,
    onSuccessResponse: (List<Result>) -> Unit,
    onError: (Throwable) -> Unit = {}
) {
    val url = "https://rickandmortyapi.com/api/character/?page=1"

    scope.launch {
        try {
            val response = withContext(Dispatchers.IO) {
                httpClient.get(url).body<CharacterDTO>()
            }
            // ya estamos en Main dispatcher aquí
            onSuccessResponse(response.results)
        } catch (e: Exception) {
            onError(e)
        }
    }
}
