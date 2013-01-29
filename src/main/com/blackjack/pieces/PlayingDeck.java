package com.blackjack.pieces;

/**
 * The basic operations a deck needs to support
 */
public interface PlayingDeck
{
    void shuffle();
    boolean hasMoreCards();
    Card draw();
    boolean needToShuffle();
}
