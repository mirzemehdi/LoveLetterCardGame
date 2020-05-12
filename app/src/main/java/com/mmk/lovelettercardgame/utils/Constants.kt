package com.mmk.lovelettercardgame.utils

 class Constants {
    companion object{
        val BASE_URL="https://gameoflife123.herokuapp.com/"
        val CODE_PLAYER_PROTECTED=300
        val CODE_NOT_YOUR_TURN=404

    }
     enum class MessageType{
         TYPE_ERROR,
         TYPE_SUCCESS,
         TYPE_NORMAL,
         TYPE_WARNING
     }


}