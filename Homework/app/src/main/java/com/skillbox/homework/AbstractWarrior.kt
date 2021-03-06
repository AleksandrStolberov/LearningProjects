package com.skillbox.homework

import kotlin.random.Random

abstract class AbstractWarrior(
    val maxHealth: Int,
    override var chanceToAvoidBeingHit: Int,
    var accuracyProbabilityOfHit: Int,
    val weapons: AbstractWeapon

): Warrior {

    override var currentHealth: Int = maxHealth


    override var isKilled: Boolean = false



    override fun attack(warrior: Warrior) {
        if (weapons.listAmmo.size <= 0) {
            weapons.recharge(weapons.creatingBullet())
            return
        } else {
            val shots = weapons.gettingBullet()
            var damage = 0
            for (i in 1..shots) {
                if (Random.nextInt(100) >= warrior.chanceToAvoidBeingHit && Random.nextInt(100) >= accuracyProbabilityOfHit) {
                    damage += weapons.creatingBullet().calculateDamage()
                }
            }
            warrior.takeDamage(damage)
        }
    }
    override fun takeDamage(damage: Int) {
        this.currentHealth -= damage
        if (currentHealth <= 0) isKilled = true
    }




}