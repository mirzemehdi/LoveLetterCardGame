package com.mmk


import com.mmk.Exceptions.NotEnoughPlayerException
import com.mmk.Model.Game
import com.mmk.Model.Player


fun main() {
    val game = Game()
    val player1 = Player(1, "Mirze")
    val player2 = Player(2, "Mehdi")
    val player3 = Player(3, "Mirzemehdi")

    game.addPlayer(player1)
    game.addPlayer(player2)
    game.addPlayer(player3)

    try {
        game.start()
    }
    catch (exception: NotEnoughPlayerException){
        println(exception.message)
    }
}