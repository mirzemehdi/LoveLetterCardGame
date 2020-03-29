package com.mmk.Model

import com.mmk.Common
import java.util.*

class CardDeck {

    private var cardList: Stack<Card> = Stack()

    init {
        initCards()
        shuffle() //Shuffling Cards
    }

    private fun initCards() {
        /*
         * There are 16 cards in the deck:
         * 5 Guard,2 Priest, 2 Baron, 2 Handmaid, 2 Prince, 1 King, 1 Countess and 1 Princess
         */

        //Initialization Guard Cards
        for (i in 0 until 5) {
            val guard = Card(
                1,
                Card.TypeCard.GUARD,
                Common.msg_card_guard
            )
            cardList.push(guard)
        }

        //Initialization Priest,Baron,Handmaid,Prince Cards
        for (i in 0 until 2) {
            val priest = Card(
                2,
                Card.TypeCard.PRIEST,
                Common.msg_card_priest
            )
            val baron = Card(
                3,
                Card.TypeCard.BARON,
                Common.msg_card_baron
            )
            val handmaid = Card(
                4,
                Card.TypeCard.HANDMAID,
                Common.msg_card_handmaid
            )
            val prince = Card(
                5,
                Card.TypeCard.PRINCE,
                Common.msg_card_prince
            )
            cardList.push(priest)
            cardList.push(baron)
            cardList.push(handmaid)
            cardList.push(prince)
        }

        //Initialization King,Countess,Princess Cards
        val king = Card(
            6,
            Card.TypeCard.KING,
            Common.msg_card_king
        )
        val countess = Card(
            7,
            Card.TypeCard.COUNTESS,
            Common.msg_card_countess
        )
        val princess = Card(
            8,
            Card.TypeCard.PRINCESS,
            Common.msg_card_princess
        )
        cardList.push(king)
        cardList.push(countess)
        cardList.push(princess)


    }

    fun shuffle() {
        Collections.shuffle(cardList)
    }
    fun isEmpty():Boolean=cardList.size==0
    fun getNumberOfRemainingCards():Int=cardList.size

    //Takes one card from top if there is no card then return value is null
    fun takeCard(): Card?{
        if (!isEmpty())
            return cardList.pop()
        else
            return null
    }

    override fun toString(): String {
        cardList.forEach{card->
            println(card)
        }
        return ""
    }

}