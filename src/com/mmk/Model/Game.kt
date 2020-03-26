package com.mmk.Model

import com.mmk.Exceptions.NotEnoughPlayerException
import java.util.*


class Game {

    // This is played between 2-4 players
    private val NB_MAX_PLAYER = 4
    private val NB_MIN_PLAYER = 2

    val players: MutableList<Player> = mutableListOf()
    val cardDeck = CardDeck()
    var currentPlayer: Player? = null
    val discardedCards: MutableList<Card> = mutableListOf()//Only if there are 2 players in the game then 3 cards will be added to this list
    var asideCard: Card? = null //When game starts one card will be put to aside
    var lastWinner: Player? = null //This one is for who will start the next raund
    var activePlayersNumber = 0 //If Player is knocked out of round this will decrease
    var cheatingEnabled = false //Maybe there will be option to choose that Players can cheat

    /*
    * If there are enough players(2-4) in a game we can start the game
    * Only one person can start the game.
    * */

    //before calling this check game if full or not
    fun addPlayer(player: Player) {
        if (getNumberOfPlayers() < NB_MAX_PLAYER) {
            players.add(player)
            activePlayersNumber++
        }
    }

    @Throws(NotEnoughPlayerException:: class)
    fun start(){
        if (getNumberOfPlayers() in NB_MIN_PLAYER..NB_MAX_PLAYER) {
            cardDeck.shuffle()
            //We take top card and put it to aside after shuffle
            asideCard=cardDeck.takeCard()
            /*
            if there are 2 players in the game then 3 cards will be added to discarded list.
            These cards will not be used during round
             */
            if (getNumberOfPlayers() == 2) discardThreeCards()
            /*In first round current Player=First player who started a game
              In next rounds currentPlayer will be last Winner
            */
            currentPlayer = lastWinner ?: players[0]

            //Give one card to each player
            players.forEach{player->player.addCard(cardDeck.takeCard()!!)}

            currentPlayer?.addCard(cardDeck.takeCard()!!)
            selectCard() //Current Player selects Card
        }
        else throw NotEnoughPlayerException("There is not enough player in the game")
    }

     private fun selectCard() {


             val scanner = Scanner(System.`in`)
             println(currentPlayer?.displayName + ": Select one of cards: \n")
             println(currentPlayer?.currentCards.toString())
             val index = scanner.nextInt()
             val selectedCard: Card = currentPlayer?.currentCards!![index]
             //index can be only 0 or 1. Because Player can hold max 2 cards in his hand
             val otherCardOfPlayer: Card = currentPlayer?.currentCards!![if (index == 1) 0 else 1]

             if (!cheatingEnabled) {
                 if ((selectedCard.type == Card.TypeCard.KING || selectedCard.type == Card.TypeCard.PRINCE)
                     && otherCardOfPlayer.type == Card.TypeCard.COUNTESS
                 ) {
                     println("You can't chose King or Prince if you have Countess card.You must discard Countess")
                     selectCard() //Again selects a card
                     return
                 }
             }
             println("Selected Card Type: " + selectedCard.type)
             doEffectOfCard(selectedCard)

    }

    private fun doEffectOfCard(selectedCard: Card) {
        currentPlayer!!.discardCard(selectedCard)
        when (selectedCard.type) {
            Card.TypeCard.PRINCESS -> discardPlayer(currentPlayer!!)
            Card.TypeCard.COUNTESS -> doNothing()
            Card.TypeCard.KING -> tradeCard()
            Card.TypeCard.PRINCE -> discardCard()
            Card.TypeCard.HANDMAID -> currentPlayer?.isProtected=true
            Card.TypeCard.BARON -> compareHands()
            Card.TypeCard.PRIEST -> lookCardOfOtherPlayer()
            Card.TypeCard.GUARD -> checkPlayerHasCard()
        }
        nextTurn()
    }

    private fun nextTurn() {
        //If there is only one Player in the Round then round finishes and he takes tokens
        if (activePlayersNumber == 1 || cardDeck.isEmpty()) {
            finishRound()
            return
        }

        var index = players.indexOf(currentPlayer)
        index++
        currentPlayer = players[index % players.size] //This is for circular turn

        currentPlayer?.isProtected=false //When turn comes his protection goes

        //maybe player is knocked out from round then we will go to next Player
        if (!currentPlayer!!.isInGame) nextTurn()
        else {
            currentPlayer!!.addCard(cardDeck.takeCard()!!)
            selectCard()
        }
    }

    private fun finishRound() {
        println("Round is finished")
    }

    private fun checkPlayerHasCard() {
        val chosenPlayer: Player? = choosePlayer()
        if (chosenPlayer != null) {
            val typeCard: Card.TypeCard = chooseCardType()
            if (chosenPlayer.hasCardType(typeCard)) {
                discardPlayer(chosenPlayer)
            }
        }
    }

    private fun chooseCardType(): Card.TypeCard {
        val typeCards = Card.TypeCard.values()
        println("Card Types: \n")
        typeCards.forEach {card-> println("$card\n") }

        val scanner = Scanner(System.`in`)
        val index = scanner.nextInt()
        if (typeCards[index] == Card.TypeCard.GUARD) {
            println("You can't choose Guard")
            return chooseCardType() //Again selects a card
        }
        println(typeCards[index].toString() + "  is selected")
        return typeCards[index]
    }

    private fun lookCardOfOtherPlayer() {
        val chosenPlayer: Player? = choosePlayer()
        if (chosenPlayer != null) {
            val cardChosenPlayer = chosenPlayer.getCard()
            println("Card of choosenPlayer: $cardChosenPlayer")
        }
    }

    private fun compareHands() {
        val chosenPlayer: Player? = choosePlayer()
        if (chosenPlayer != null) {
            val cardCurrentPlayer: Card = currentPlayer!!.getCard()
            val cardChosenPlayer = chosenPlayer.getCard()
            if (cardCurrentPlayer.strength < cardChosenPlayer.strength) discardPlayer(currentPlayer!!)
            else if (cardCurrentPlayer.strength > cardChosenPlayer.strength) discardPlayer(chosenPlayer)
        }
    }

    private fun discardCard() {
        /*
        * After choosing a player if cardDeck is empty,
        * then selectedPlayer takes aside card that when game started it was put aside
        * */
        val chosenPlayer: Player? = choosePlayer()
        if (chosenPlayer != null) {
            chosenPlayer.discardCard(chosenPlayer.getCard())
            if (!cardDeck.isEmpty()) chosenPlayer.addCard(cardDeck.takeCard()!!)
            else chosenPlayer.addCard(asideCard!!)
        }
    }

    private fun tradeCard() {
        val chosenPlayer: Player? = choosePlayer()
        //Means couldn't chose any Player for some reason
        if (chosenPlayer == null) doNothing()
        else {
            val cardOfChosenPlayer = chosenPlayer.getCard()
            val cardOfCurrentPlayer = currentPlayer!!.getCard()
            currentPlayer!!.removeCard(cardOfCurrentPlayer)
            currentPlayer!!.addCard(cardOfChosenPlayer)
            chosenPlayer.removeCard(cardOfChosenPlayer)
            chosenPlayer.addCard(cardOfCurrentPlayer)
        }
    }

    private fun choosePlayer(): Player? {
        println("Choose a player: \n")
        players.forEach{player->println(player)}
        //If all other Players has HandMaid CardType or is not in game then can't choose any Player
        var hasChance = false
        players.forEach{ player-> if (player.isInGame && !player.isProtected) hasChance = true }

        if (!hasChance) return null

        val scanner = Scanner(System.`in`)
        val index = scanner.nextInt()
        var chosenPlayer: Player? = players[index]
        if (!chosenPlayer!!.isInGame) {
            println("Player is not in a game.Choose another Player")
            chosenPlayer = choosePlayer() //Choose Again
            return chosenPlayer
        } else {
            if (chosenPlayer.isProtected) {
                println("Player has protected.Choose another Player")
                chosenPlayer = choosePlayer()
                return chosenPlayer
            }
            else return chosenPlayer
        }
    }

    private fun doNothing() {
        println("Nothing happened")
    }

    private fun discardPlayer(player: Player) {
        player.isInGame=false
        activePlayersNumber--
        println("$currentPlayer are discarded from game")
    }

    //If there are only 2 players in a game then this function will be called
    private fun discardThreeCards() {
        discardedCards.add(cardDeck.takeCard()!!)
        discardedCards.add(cardDeck.takeCard()!!)
        discardedCards.add(cardDeck.takeCard()!!)
    }

    private fun getNumberOfPlayers(): Int =players.size

    override fun toString(): String {
        println("Players in the game:\n")
        for (player in players) println(player)
        println("Current Player: $currentPlayer")
        println("Last Winner: $lastWinner")
        println("Discarded Cards: $discardedCards")
        println("Card Deck : " + cardDeck.getNumberOfRemainingCards() + " cards\n")
        println(cardDeck.toString())
        return ""
    }

}