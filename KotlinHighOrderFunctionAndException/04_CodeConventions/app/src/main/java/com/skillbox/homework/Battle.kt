package com.skillbox.homework

class Battle(val teamOne: Team, val teamTwo: Team) {

    var gameOver: Boolean = false

    fun getBattleState(): BattleState {
        var health1 = 0
        var health2 = 0
        teamOne.teamList.forEach { health1 += it.currentHealth }
        teamTwo.teamList.forEach { health2 += it.currentHealth }
        return if (health1 <= 0 && health2 <= 0) {
            gameOver = true
            BattleState.Draw(health1, health2)
        } else if (health1 <= 0) {
            gameOver = true
            BattleState.TeamTwoWin(health1)
        } else if (health2 <= 0) {
            gameOver = true
            BattleState.TeamOneWin(health2)
        } else {
            BattleState.Progress(health1, health2)
        }
    }

    fun nextIterationBattle() {
        teamOne.teamList.shuffled()
        teamTwo.teamList.shuffled()
        for (humanOne in teamOne.teamList) {
            if (!humanOne.isKilled) {
                for (humanTwo in teamTwo.teamList) {
                    if (!humanTwo.isKilled) {
                        humanOne.attack(humanTwo)
                        break
                    }
                }
            }
        }
        for (humanTwo in teamTwo.teamList) {
            if (!humanTwo.isKilled) {
                for (humanOne in teamOne.teamList) {
                    if (!humanOne.isKilled) {
                        humanTwo.attack(humanOne)
                        break
                    }
                }
            }
        }
    }
}
