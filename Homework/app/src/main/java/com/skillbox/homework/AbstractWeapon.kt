package com.skillbox.homework

abstract class AbstractWeapon(
    private val maxBullet: Int,
    private val fireType: FireType
) {
    var listAmmo: MutableList<Ammo> = mutableListOf()

    var isAvailabilityBullet: Boolean = true

    abstract fun creatingBullet(): Ammo

    fun recharge(ammo: Ammo) {
        while (listAmmo.size <= maxBullet) {
            listAmmo.add(ammo)
        }
        isAvailabilityBullet = true
}

    fun gettingBullet(): Int{
        for (number in 1..fireType.shotQuantity ){
            if (listAmmo.size > 0) {
                listAmmo.removeAt(listAmmo.size - 1)
            } else isAvailabilityBullet = false
        }
        return listAmmo.size
    }

}



