package com.blackjack.pieces;

/**
 * Represents the suit of a playing card
 */
public enum Suit
{
    HEARTS (1),
    DIAMONDS (2),
    CLUBS (3),
    SPADES (4);

    public final int value;
    private final String str;

    Suit(int value)
    {
        this.value = value;
        // pre-calc the string
        str = name().charAt(0) + name().substring(1).toLowerCase();
    }

    @Override
    public String toString()
    {
         return str;
    }
}
