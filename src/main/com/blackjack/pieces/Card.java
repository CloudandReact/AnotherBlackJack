package com.blackjack.pieces;

/**
 * Represents a single black jack card
 */
public final class Card
{
    public static final int FACE_VALUE = 10;

    public final Suit suit;
    public final Rank rank;
    private final String string;

    public Card( Suit suit, Rank rank )
    {
        this.suit = suit;
        this.rank = rank;

        // pre-calc string
        string = rank + " (" + suit.toString().charAt(0) + ")";
    }

    public int value()
    {
        return rank.isFace() ? FACE_VALUE : rank.value;
    }

    public String toString()
    {
        return string;
    }
}
