package com.blackjack.game;

import com.blackjack.pieces.Card;
import com.blackjack.pieces.Rank;
import com.blackjack.pieces.Suit;
import com.blackjack.players.Player;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HandTests
{
    @Test
    public void testBlackJack()
    {
        Hand h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.JACK)), 10);

        assertTrue( h.isBlackJack() );

        h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.QUEEN), new Card(Suit.SPADES, Rank.JACK)), 10);

        assertFalse( h.isBlackJack() );

        h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.QUEEN), new Card(Suit.DIAMONDS, Rank.FIVE), new Card(Suit.HEARTS, Rank.SIX)), 10);

        assertFalse( h.isBlackJack() );
    }

    @Test
    public void testSplit()
    {
        Hand h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.JACK)), 10);

        assertFalse( h.canSplit() );

        Card card1 =  new Card(Suit.CLUBS, Rank.QUEEN);
        Card card2 =  new Card(Suit.SPADES, Rank.TEN);

        h = new Hand(Arrays.asList(card1, card2), 10);

        assertTrue( h.canSplit() );

        Hand newHand = h.split();

        assertTrue( h.getTotalCards() == 1 && h.eval() == card1.value() );
        assertTrue( newHand.getTotalCards() == 1 && newHand.eval() == card2.value() );
    }

    @Test
    public void testSplitAces()
    {
        Hand h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.ACE)), 10);

        assertTrue(h.canSplit());

        Hand newHand = h.split();

        h.addCard(new Card(Suit.DIAMONDS, Rank.ACE));

        assertFalse(h.canSplit());
        assertFalse(h.canDraw());
    }

    @Test
    public void testDoubleDown()
    {
        Player player = new Player();
        Hand h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.TWO)), 10);
        player.addHand(h);

        assertTrue(player.canDoubleDown());

        player.addCardToCurrentHand( new Card(Suit.DIAMONDS, Rank.FIVE) );

        assertFalse(player.canDoubleDown());

        player.clearHands();
        h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.TWO)), 90);
        player.addHand(h);

        // player doesn't have enough money
        assertFalse(player.canDoubleDown());

        player = new Player();
        h = new Hand(Arrays.asList(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.SPADES, Rank.ACE)), 10);
        player.addHand(h);
        player.split( new Card(Suit.DIAMONDS, Rank.FIVE), new Card(Suit.HEARTS, Rank.NINE) );

        // can't double down on split aces
        assertFalse(player.canDoubleDown());
    }
}
