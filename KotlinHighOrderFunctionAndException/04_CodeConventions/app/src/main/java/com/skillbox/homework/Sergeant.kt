package com.skillbox.homework

class Sergeant() : AbstractWarrior(
    maxHealth = 550,
    chanceToAvoidBeingHit = 35,
    accuracyProbabilityOfHit = 80,
    weapons = Weapons.rifle
) {
    override fun toString(): String {
        return "Sergeant"
    }
}
