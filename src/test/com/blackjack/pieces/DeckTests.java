package com.blackjack.pieces;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DeckTests
{
    @Test
    public void testBasicDeck()
    {
        Deck d = new Deck();

        d.shuffle();

        for (int i = 0; i < Deck.CARDS_IN_A_DECK - 1; i++)
        {
            d.draw();
        }

        assertTrue( d.hasMoreCards() );

        d.draw();

        assertFalse(d.hasMoreCards());

        assertTrue( d.needToShuffle() );

        d.shuffle();

        assertTrue(d.hasMoreCards());

        assertFalse( d.needToShuffle() );
    }

    @Test
    public void testMultiDeck()
    {
        PlayingDeck d = new MultiDeck(3);

        d.shuffle();

        for (int i = 0; i < Deck.CARDS_IN_A_DECK * 3 - 1; i++)
        {
            d.draw();
        }

        assertTrue( d.hasMoreCards() );

        d.draw();

        assertFalse(d.hasMoreCards());

        assertTrue( d.needToShuffle() );

        d.shuffle();

        assertTrue(d.hasMoreCards());

        assertFalse( d.needToShuffle() );
    }
}
