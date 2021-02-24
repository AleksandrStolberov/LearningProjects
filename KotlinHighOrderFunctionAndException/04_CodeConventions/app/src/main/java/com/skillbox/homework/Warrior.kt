package com.skillbox.homework

interface Warrior {
    var isKilled: Boolean
    var chanceToAvoidBeingHit: Int
    var currentHealth: Int

    fun attack(warrior: Warrior)
    fun takeDamage(damage: Int)
}
