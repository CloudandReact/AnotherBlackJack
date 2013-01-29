package com.blackjack.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A deck made up of multiple decks
 */
public final class MultiDeck implements PlayingDeck
{
    private List<Deck> decks;
    private List<Deck> usedDecks;
    private Random selector;
    private int totalCards;
    private int dealtCards;

    public MultiDeck( int decksToUse )
    {
        decks = new ArrayList<Deck>();
        usedDecks = new ArrayList<Deck>();
        selector = new Random(System.nanoTime());

        for ( int i = 0; i < decksToUse; i++ ) {
            decks.add( new Deck() );
        }

        // initialize to fully dealt state to signal need for shuffle
        dealtCards = totalCards = decksToUse * Deck.CARDS_IN_A_DECK;
    }

    /**
     * Shuffles all the decks
     */
    @Override
    public void shuffle()
    {
        dealtCards = 0;
        if ( usedDecks.size() > 0 )
        {
            decks.addAll(usedDecks);
            usedDecks.clear();
        }

        for ( Deck deck : decks )
        {
            deck.shuffle();
        }
    }

    /**
     * Is the multi deck exhausted?
     * @return
     */
    @Override
    public boolean hasMoreCards()
    {
        return decks.size() > 0;
    }

    /**
     * Gets a card from the multi deck
     * @return
     */
    @Override
    public Card draw()
    {
        if ( decks.size() > 0 )
        {
            int deckIndex = selector.nextInt( decks.size() );
            Deck deck = decks.get(deckIndex);
            Card card = deck.draw();
            dealtCards++;

            if ( ! deck.hasMoreCards() )
            {
                usedDecks.add(decks.remove(deckIndex));
            }

            return card;
        }
        else
        {
            throw new UnsupportedOperationException("There are no more cards to be drawn");
        }
    }

    /**
     * Does the multi deck need shuffling?
     * @return
     */
    @Override
    public boolean needToShuffle()
    {
        return (dealtCards / totalCards) > Deck.PERCENT_TO_SHUFFLE_AT;
    }
}
