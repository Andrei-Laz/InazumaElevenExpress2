package com.example.inazumaelevenexpress2.model

import kotlinx.serialization.Serializable
import com.example.inazumaelevenexpress2.model.enums.Element

@Serializable
data class Hissatsu(
    var hissatsuId: Int? = null,

    var name: String = "",

    var element: Element = Element.VOID,

    var description: String = "",

    var type: String = "",

    var power: Int = 0
)