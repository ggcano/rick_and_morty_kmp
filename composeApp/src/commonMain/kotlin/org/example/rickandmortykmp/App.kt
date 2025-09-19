package org.example.rickandmortykmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@Composable

fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Navigator(screen = MainScreen()) { navigator ->
                    FadeTransition(navigator)
                }
            }
        }
    }
}

val appModule = module {
    single { RickRepository(networkModule = NetWorkingUtils.httpClient) }
    viewModel { MainViewModel(get()) }
}

/*@Composable
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
}*/

/*fun getListFromDTO(
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
}*/
