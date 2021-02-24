package com.skillbox.homework

import kotlin.random.Random

enum class Ammo(
    var damage: Int,
    val chanceOfCriticalDamage: Int,
    var ratioOfCriticalDamage: Int

) {
    PistolBullet(damage = 30, chanceOfCriticalDamage = 40, ratioOfCriticalDamage = 4),
    RifleBullet(damage = 40, chanceOfCriticalDamage = 60, ratioOfCriticalDamage = 4),
    RevolverBullet(damage = 50, chanceOfCriticalDamage = 70, ratioOfCriticalDamage = 6),
    MachineGunBullet(damage = 70, chanceOfCriticalDamage = 80, ratioOfCriticalDamage = 8);


    fun calculateDamage(): Int {
        return if (Random.nextInt(100) > chanceOfCriticalDamage) {
            damage * ratioOfCriticalDamage
        } else damage
    }

}