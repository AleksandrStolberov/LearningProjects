package com.skillbox.homework

object Weapons {
    val pistol = object : AbstractWeapon(20, FireType.Single) {
        override fun creatingBullet(): Ammo {
            return Ammo.PistolBullet
        }
    }
    val rifle = object : AbstractWeapon(10, FireType.Single) {
        override fun creatingBullet(): Ammo {
            return Ammo.RifleBullet
        }
    }
    val revolver = object : AbstractWeapon(12, FireType.Single) {
        override fun creatingBullet(): Ammo {
            return Ammo.RevolverBullet
        }
    }
    val machineGun = object : AbstractWeapon(40, FireType.Automatic) {
        override fun creatingBullet(): Ammo {
            return Ammo.MachineGunBullet
        }
    }
}
