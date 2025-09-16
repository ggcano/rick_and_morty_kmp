package org.example.rickandmortykmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import model.CharacterDTO
import model.Result
import org.example.rickandmortykmp.NetWorkingUtils.httpClient

import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var superheroName by remember { mutableStateOf("") }
        var superheroList by remember{ mutableStateOf<List<Result>>(emptyList()) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                TextField(value = superheroName, onValueChange = { superheroName = it })
                Button(onClick = { getSuperheroList(superheroName){superheroList = it} }) {
                Text("Load")
                }
            }
            LazyColumn {
                items(superheroList){hero ->
                    Text(hero.name)
                }
            }
        }
    }


}
fun getSuperheroList(superheroName: String, onSuccessResponse: (List<Result>) -> Unit) {
    if (superheroName.isBlank()) return
    val url =
        "https://rickandmortyapi.com/api/character/?page=1"

    CoroutineScope(Dispatchers.IO).launch {
        val response = httpClient.get(url).body<CharacterDTO>()
        onSuccessResponse(response.results)
    }

}