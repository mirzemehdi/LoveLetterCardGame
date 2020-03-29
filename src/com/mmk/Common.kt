package com.mmk

object Common {
    //8 Card Effect Messages
    val msg_card_guard =
        "Player designates another player and names a type of card. If that player's hand matches the type of card specified, that player is eliminated from the round. However, Guard cannot be named as the type of card."
    val msg_card_priest = "Player is allowed to see another player's hand"
    val msg_card_baron =
        "Player will choose another player and privately compare hands. The player with the lower-strength hand is eliminated from the round."
    val msg_card_handmaid = "Player cannot be affected by any other player's card until the next turn."
    val msg_card_prince =
        "Player can choose any player (including themselves) to discard their hand and draw a new one. If the discarded card is the Princess, the discarding player is eliminated."
    val msg_card_king = "Player trades hands with any other player."
    val msg_card_countess =
        "If a player holds both this card and either the King or Prince card, this card must be played immediately."
    val msg_card_princess =
        "If a player plays this card for any reason, they are eliminated from the round."
}