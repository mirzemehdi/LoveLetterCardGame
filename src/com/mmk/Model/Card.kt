package com.mmk.Model

data class Card(val strength:Int, val type: TypeCard, val effectMessage:String) {
    enum class TypeCard{
        GUARD,
        PRIEST,
        BARON,
        HANDMAID,
        PRINCE,
        KING,
        COUNTESS,
        PRINCESS
    }

    override fun toString(): String {
        return "Card{" +
                "strength=" + strength +
                ", type=" + type +
                ", effectMessage='" + effectMessage + '\'' +
                "}\n"
    }

}