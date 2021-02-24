package com.skillbox.homework

class Soldier() : AbstractWarrior(
    maxHealth = 500,
    chanceToAvoidBeingHit = 30,
    accuracyProbabilityOfHit = 70,
    weapons = Weapons.pistol
) {
    override fun toString(): String {
        return "Soldier"
    }
}
