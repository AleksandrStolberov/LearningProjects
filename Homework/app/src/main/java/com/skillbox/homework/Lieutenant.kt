package com.skillbox.homework

class Lieutenant(): AbstractWarrior(
    maxHealth = 600,
    chanceToAvoidBeingHit = 40,
    accuracyProbabilityOfHit = 90,
    weapons = Weapons.revolver
)