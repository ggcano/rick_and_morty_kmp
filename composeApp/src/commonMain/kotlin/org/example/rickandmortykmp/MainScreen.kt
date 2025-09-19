package org.example.rickandmortykmp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import model.Result
import org.koin.compose.viewmodel.koinViewModel

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<MainViewModel>()
        val isLoading by viewModel.isLoading.collectAsState()
        val characters by viewModel.characterList.collectAsState()
        val error by viewModel.errorMessage.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                //LOADING CASE
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                //ERROR CASE
                error != null -> {
                    // Error en el centro
                    Text(
                        text = error ?: "Error desconocido al cargar los datos",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // Si hay datos válidos, los muestra
                characters.isNotEmpty() -> {
                    // Lista de personajes
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        //padding de arriba del listado

                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(characters) { character ->
                            ItemRow(result = character)

                        }
                    }
                }
                //Si NO se cumple ninguna de las anteriores, solicita los datos
                else -> {
                    viewModel.getCharacterList()
                }
            }
        }
    }


    @Composable
    fun ItemRow(result: Result) {
        val navigator: Navigator? = LocalNavigator.currentOrThrow
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(3.dp, Color.Yellow, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable(onClick = {
                    navigator?.push(SecondScreen(id = result.id))
                })
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
                    text = result.name,
                    color = Color.DarkGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }


}

