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
                mView.giveCardToPlayer(playersList[3])
                mView.makeTurnOfPlayer(playersList[3])
            }
                ,
                4 * (CardAnimations.DURATION_ARRANGE_CARDS_ANIMATION + CardAnimations.DURATION_DEAL_CARD_ANIMATION))

            Handler().postDelayed({
                mView.makeTurnOfPlayer(playersList[0])
            },6000)
        }

    }

    override fun joinGame(playerPOJO: PlayerPOJO) {
        playersList.add(playerPOJO)
        mView.saveMyOwnPlayer(playerPOJO)
        getPlayers()
    }
}