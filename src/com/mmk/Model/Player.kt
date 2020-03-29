package com.mmk.Model

import java.util.*

data class Player(var  id:Int,var displayName:String) {
    var nbTokens = 0
    val currentCards:MutableList<Card> = mutableListOf()
    val discardedCards=Stack<Card>()
    var isInGame = true
    var isProtected = false

    fun addCard(card: Card)=currentCards.add(card)
    fun removeCard(card: Card)=currentCards.remove(card)
    fun getCard(): Card = currentCards[0]

    //Discarding Card means Player played this card
    fun discardCard(card: Card){
        currentCards.remove(card)
        discardedCards.add(card)
    }

    //This function checks that Player has this card in his hand or not
    fun hasCardType(cardType: Card.TypeCard):Boolean{
        currentCards.forEach{card->
            if (card.type==cardType) return true
        }
        return false
    }

    override fun toString(): String {
        return "Player{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", nbTokens=" + nbTokens +
                ", currentCards=" + currentCards +
                ", discardedCards=" + discardedCards +
                ", isInGame=" + isInGame +
                ", isProtected=" + isProtected +
                '}'
    }
}