package model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<Result>
)