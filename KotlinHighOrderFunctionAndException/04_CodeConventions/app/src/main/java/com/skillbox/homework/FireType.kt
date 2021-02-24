package com.skillbox.homework

sealed class FireType(
    val shotQuantity: Int
) {
    object Single : FireType(1)
    object Automatic : FireType(3)
}
