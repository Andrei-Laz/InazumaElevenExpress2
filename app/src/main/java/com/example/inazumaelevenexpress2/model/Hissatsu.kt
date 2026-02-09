package com.example.inazumaelevenexpress2.model

import kotlinx.serialization.Serializable
import com.example.inazumaelevenexpress2.model.enums.Element
import com.example.inazumaelevenexpress2.model.enums.HissatsuType

@Serializable
data class Hissatsu(
    var hissatsuId: Int? = null,

    var name: String = "",

    var element: Element,

    var description: String = "",

    var type: HissatsuType,

    var power: Int = 0
)