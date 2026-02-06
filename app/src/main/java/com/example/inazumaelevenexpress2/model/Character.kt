package com.example.inazumaelevenexpress2.model

import com.example.inazumaelevenexpress2.model.enums.Element
import com.example.inazumaelevenexpress2.model.enums.Position
import com.example.inazumaelevenexpress2.model.enums.Sex
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    var characterId: Int? = null,

    var nickname: String = "",

    var name: String = "",

    var sex: Sex = Sex.UNKNOWN,
    var element: Element = Element.VOID,
    var position: Position = Position.ND,


    var kick: Int = 0,
    var dribble: Int = 0,
    var block: Int = 0,
    var catch: Int = 0,
    var technique: Int = 0,
    var speed: Int = 0,
    var stamina: Int = 0,
    var luck: Int = 0
)