package com.skillbox.homework

interface Warrior {
    var currentHealth: Int

    var isKilled: Boolean
    var chanceToAvoidBeingHit: Int


    fun attack(warrior: Warrior)
    fun takeDamage(damage: Int)
}