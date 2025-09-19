package org.example.rickandmortykmp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import model.CharacterDTO
import model.CharacterID

class RickRepository(private val networkModule: HttpClient) {
    val urlScreenMain = "https://rickandmortyapi.com/api/character/?page=1"

    suspend fun getCharacterList(): CharacterDTO {
        return networkModule.get(urlScreenMain).body<CharacterDTO>()
    }

    suspend fun getCharacterDetail(id: Int): CharacterID {
        return networkModule.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "rickandmortyapi.com"
                encodedPath = "/api/character/$id"
            }
        }.body()
    }
}