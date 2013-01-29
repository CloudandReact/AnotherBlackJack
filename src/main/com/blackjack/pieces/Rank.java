package com.blackjack.pieces;

/**
 * Represents the rank of a playing card
 */
public enum Rank
{
    TWO (2),
    THREE (3),
    FOUR (4),
    FIVE (5),
    SIX (6),
    SEVEN (7),
    EIGHT (8),
    NINE (9),
    TEN (10),
    JACK (12),
    QUEEN (13),
    KING (14),
    ACE (11);

    public final int value;
    private final String str;

    Rank(int value)
    {
        this.value = value;
        str = name().charAt(0) + name().substring(1).toLowerCase();
    }

    @Override
    public String toString()
    {
         return str;
    }

    /**
     * Is the card a face card?
     * @return
     */
    public boolean isFace()
    {
        switch ( value )
        {
            case 12:
            case 13:
            case 14:
                return true;
            default:
                return false;
        }
    }
}
