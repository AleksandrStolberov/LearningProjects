package com.skillbox.homework

import kotlin.random.Random

class Team(var numberOfWarrior: Int) {

    val teamList = team()

    private fun team(): List<Warrior> {
        val listTeam = mutableListOf<Warrior>()
        while (listTeam.size != numberOfWarrior) {
            when {
                Random.nextInt(100) > 60 -> listTeam.add(Captain())
                Random.nextInt(100) > 50 -> listTeam.add(Lieutenant())
                Random.nextInt(100) > 40 -> listTeam.add(Sergeant())
                Random.nextInt(100) > 30 -> listTeam.add(Soldier())
            }
        }
        return listTeam
    }










}