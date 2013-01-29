package com.blackjack.pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Standard single deck
 */
public final class Deck implements PlayingDeck
{
    public static final int CARDS_IN_A_DECK = 52;
    public static final double PERCENT_TO_SHUFFLE_AT = .75;

    private ArrayList<Card> cards;
    private Random selector;
    private StringBuffer buffer;
    private Iterator<Card> iterator;
    private int dealtCards;

    public Deck()
    {
        // initialize to the fully dealt state
        dealtCards = CARDS_IN_A_DECK;
        cards = new ArrayList<Card>(CARDS_IN_A_DECK);
        buildDeck();
        selector = new Random(System.nanoTime());

        buffer = new StringBuffer();
    }

    private void buildDeck()
    {
        int index = 0;
        for ( Rank value : Rank.values())
        {
            for ( Suit suit : Suit.values() )
            {
                cards.add(index, new Card( suit, value ));
                index++;
            }
        }
    }

    private void resetDeck()
    {
        iterator = cards.iterator();
        dealtCards = 0;
    }

    /**
     * Randomize the deck
     */
    @Override
    public void shuffle()
    {
        for ( int i = 0; i < cards.size(); i++ )
        {
            int toSwap = selector.nextInt(51);
            Card tmpCard = cards.get(i);
            cards.set(i, cards.get(toSwap));
            cards.set(toSwap, tmpCard);
        }

        resetDeck();
    }

    /**
     * Are there more cards in the deck
     * @return
     */
    @Override
    public boolean hasMoreCards()
    {
        return iterator.hasNext();
    }

    /**
     * Get the next card from the deck
     * @return
     */
    @Override
    public Card draw()
    {
        if ( ! iterator.hasNext() )
        {
            throw new UnsupportedOperationException("There are no more cards to be drawn");
        }

        dealtCards++;
        return iterator.next();
    }

    /**
     * Is the deck passed the point where the majority of the cards have been played?
     * @return
     */
    @Override
    public boolean needToShuffle()
    {
        return (dealtCards / CARDS_IN_A_DECK) > PERCENT_TO_SHUFFLE_AT;
    }

    public String toString()
    {
        for ( int i = 0; i < CARDS_IN_A_DECK; i++ )
        {
            buffer.append(cards.get(i));

            if ( i != CARDS_IN_A_DECK- 1)
            {
                buffer.append(cards.get(i));
            }
        }
        String str = buffer.toString();
        buffer.delete(0, buffer.length());

        return str;
    }
}
