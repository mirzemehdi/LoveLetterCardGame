package com.mmk.lovelettercardgame.ui.fragments.game

import com.github.nkzawa.emitter.Emitter
import com.mmk.lovelettercardgame.pojo.CardPojo
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.pojo.RoomPOJO
import com.mmk.lovelettercardgame.ui.BaseView
import com.mmk.lovelettercardgame.utils.Constants

interface GameContractor {

    interface View:BaseView<GamePresenter>{

        fun showPlayers(players:List<PlayerPOJO>)
        fun giveCardToPlayer(givenPlayerId: String?)
        fun giveCardToAllPlayers(players: List<PlayerPOJO>?)
        fun hideShowWaitingText(isWaitingPlayers:Boolean)
        fun saveMyOwnPlayer(playerPOJO: PlayerPOJO)
        fun makeTurnOfPlayer(playerId: String)
        fun showMessage(message:String?,messageType: Constants.MessageType)
        fun myCardsUpdated(cards:List<CardPojo>)
        fun swapCards(firstPlayerId:String,secondPlayerId:String?)
        fun addToDiscardedCard(playerId:String,cardType:Int)
        fun onCardPlayed(cardPojo: CardPojo,targetPlayerId: String?)
        fun playersStateUpdated(players: List<PlayerPOJO>)
        fun lookOtherPlayerCard(playerId: String,cardType: Int)
        fun newCardPlayed(playerId: String,cardType: Int,targetPlayerId: String?)
        fun roundFinished(playerPOJO: PlayerPOJO)
        fun gameFinished(playerPOJO: PlayerPOJO)

    }

    interface Presenter{

        fun getPlayers(roomItem:RoomPOJO?)
        fun joinGame(playerPOJO: PlayerPOJO)
        fun listenForPlayersUpdate(maxNbPlayers:Int?)
        fun getMyCards()
        fun playCard(cardPojo: CardPojo, targetPlayerId:String?=null)

        fun listenCardKingPlayed()
        fun listenCardPrincePlayed()
        fun listenCardPriestPlayed()

    }
}