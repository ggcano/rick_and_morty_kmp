package org.example.rickandmortykmp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import model.CharacterDTO
import org.example.rickandmortykmp.NetWorkingUtils.httpClient

class RickRepository(private val networkModule: HttpClient) {
    val url = "https://rickandmortyapi.com/api/character/?page=1"

    suspend fun getCharacterList(): CharacterDTO {
        return networkModule.get(url).body<CharacterDTO>()
    }
}