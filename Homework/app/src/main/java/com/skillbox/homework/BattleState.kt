package com.skillbox.homework

sealed class BattleState {

    abstract fun printMassage()

    class Progress(private val sumOfHealth1: Int, private val sumOfHealth2: Int) : BattleState() {
        override fun printMassage() {
            println("Здоровье команды 1 = $sumOfHealth1 и команды 2 = $sumOfHealth2")
        }
    }

    class TeamOneWin(private val sumOfHealth2: Int) : BattleState() {
        override fun printMassage() {
            if (sumOfHealth2 <= 0) {
                println("Победила первая команда")
            }
        }

    }

    class TeamTwoWin(private val sumOfHealth1: Int) : BattleState() {
        override fun printMassage() {
            if (sumOfHealth1 <= 0) {
                println("Победила вторая команда")
            }
        }
    }

    class Draw(private val sumOfHealth1: Int, private val sumOfHealth2: Int) : BattleState() {
        override fun printMassage() {
            if (sumOfHealth1 == sumOfHealth2) {
                println("Ничья")
            }
        }
    }


}