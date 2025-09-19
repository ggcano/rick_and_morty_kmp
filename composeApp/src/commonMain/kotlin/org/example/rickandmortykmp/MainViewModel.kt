package org.example.rickandmortykmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.CharacterID

class MainViewModel(
    private val repository: RickRepository
) : ViewModel() {

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Lista de personajes (Result)
    private val _characterList = MutableStateFlow<List<model.Result>>(emptyList())
    private val _characterDetail = MutableStateFlow<CharacterID?>(null)
    val characterDetail: StateFlow<CharacterID?> = _characterDetail
    val characterList: StateFlow<List<model.Result>> = _characterList

    // Posible error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getCharacterList() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getCharacterList() // CharacterDTO Object
                _characterList.value = response.results      // solo la lista de de Personajes
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCharacterDetail(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getCharacterDetail(id)
                _characterDetail.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
