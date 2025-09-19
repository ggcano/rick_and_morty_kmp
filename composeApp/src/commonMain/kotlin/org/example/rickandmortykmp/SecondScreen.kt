package org.example.rickandmortykmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

class SecondScreen(val id: Int) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<MainViewModel>()
        val isLoading by viewModel.isLoading.collectAsState()
        val character by viewModel.characterDetail.collectAsState()
        val error by viewModel.errorMessage.collectAsState()
        val navigator: Navigator? = LocalNavigator.currentOrThrow
        Spacer(modifier = Modifier.height(16.dp))

        LaunchedEffect(id) {
            viewModel.getCharacterDetail(id)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Detalle del personaje", fontSize = 20.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (character == null) {
                    Text("Cargando...", fontSize = 18.sp)
                } else {
                    // 👇 Imagen antes de los textos
                    AsyncImage(
                        model = character?.image,
                        contentDescription = "Imagen de ${character?.name}",
                        contentScale = ContentScale.FillWidth, // asegura que se recorte bien al llenar el ancho
                        modifier = Modifier
                            .fillMaxWidth()                // ocupa el ancho total
                            .height(220.dp)
                            .padding(16.dp)                 // margen externo
                            .clip(RoundedCornerShape(16.dp))
                    )

                    character?.name?.let {
                        Text(
                            text = it,
                            fontSize = 30.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    character?.species?.let { Text(text = it, fontSize = 18.sp) }
                    character?.status?.let { Text(text = it, fontSize = 18.sp) }
                    character?.created?.let { Text(text = it, fontSize = 18.sp) }
                    Text(
                        text = character?.episode?.firstOrNull() ?: "Sin episodios",
                        fontSize = 18.sp
                    )
                    character?.gender?.let { Text(text = it, fontSize = 18.sp) }
                }
            }
        }
    }
}

