package com.example.inazumaelevenexpress2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.inazumaelevenexpress2.model.enums.Element
import com.example.inazumaelevenexpress2.model.enums.Position
import com.example.inazumaelevenexpress2.model.enums.Sex

@Serializable
data class InazumaCharacter(
    val characterId: Int?,
    val nickname: String,
    val name: String,
    val sex: Sex,
    val element: Element,
    val position: Position,
    val kick: Int,
    val dribble: Int,
    val block: Int,

    @SerialName("catch")
    val catching: Int,

    val technique: Int,
    val speed: Int,
    val stamina: Int,
    val luck: Int,

    val assignedHissatsus: List<Hissatsu> = emptyList()
)