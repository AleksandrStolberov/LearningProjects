package com.skillbox.homework

fun main() {

    println("Введите количество воинов: ")
    val teamOne = Team(readLine()?.toIntOrNull() ?: 0)
    val teamTwo = Team(readLine()?.toIntOrNull() ?: 0)
    println("Начало битвы")

    val battle = Battle(teamOne, teamTwo)

    while (!battle.gameOver) {
        battle.nextIterationBattle()
        battle.getBattleState().printMassage()
    }
}
