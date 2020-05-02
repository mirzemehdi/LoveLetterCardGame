package com.mmk.lovelettercardgame.ui.fragments.game

import android.os.Handler
import com.mmk.lovelettercardgame.pojo.PlayerPOJO
import com.mmk.lovelettercardgame.utils.animations.CardAnimations

class GamePresenter(private val mView: GameContractor.View) : GameContractor.Presenter {

    init {
        mView.setPresenter(this)
    }

    val playersList = mutableListOf(
        PlayerPOJO("1", "Anday")
        , PlayerPOJO("2", "Sirin")
        , PlayerPOJO("3", "Amine")
    )

    override fun getPlayers() {

        mView.showPlayers(playersList)
        if (playersList.size == 4) {
            mView.giveCardToAllPlayers(playersList)
            mView.hideShowWaitingText(false)
            Handler().postDelayed(Runnable {
                mView.giveCardToPlayer(playersList[0])
            }
                ,
                4 * (CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION + CardAnimations.DURATION_DEAL_CARD_ANIMATION))
        }

    }

    override fun joinGame(name: String) {
        val newPlayer = PlayerPOJO("4", name)
        playersList.add(newPlayer)
        mView.saveMyOwnPlayer(newPlayer)
        getPlayers()
    }
}