package com.blackjack.com.blackjack.pieces;

import com.blackjack.pieces.Card;
import com.blackjack.pieces.PlayingDeck;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Easy to manipulate deck
 */
public class TestDeck implements PlayingDeck
{
    public Queue<Card> fakeDeck;

    public TestDeck()
    {
        fakeDeck = new LinkedList<Card>();
    }

    @Override
    public void shuffle()
    {
        // do nothing
    }

    @Override
    public boolean hasMoreCards()
    {
        return true;
    }

    @Override
    public Card draw()
    {
        return fakeDeck.poll();
    }

    @Override
    public boolean needToShuffle()
    {
        return false;
    }
}
