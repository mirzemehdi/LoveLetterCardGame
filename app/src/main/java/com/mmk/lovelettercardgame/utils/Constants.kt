package com.mmk.lovelettercardgame.utils

class Constants {
    companion object {
        const val PREF_USER_EXIST = "userExist"
        const val PREF_USER_TOKEN = "userToken"
        const val BASE_URL = "https://gameoflife123.herokuapp.com/"
        const val CODE_PLAYER_PROTECTED = 300
        const val CODE_NOT_YOUR_TURN = 404
        const val CODE_PLAYER_OUT_OF_ROUND = 301
        const val CODE_CAN_NOT_PLAY_YOURSELF = 302
        const val CODE_WRONG_CARD_PLAYED = 500
        const val CODE_ALL_PLAYERS_PROTECTED = 303
        const val CODE_PLAY_OTHER_CARD = 309


    }

    enum class MessageType {
        TYPE_ERROR,
        TYPE_SUCCESS,
        TYPE_NORMAL,
        TYPE_WARNING
    }


}