package com.blackjack.players;

import com.blackjack.game.Hand;
import com.blackjack.game.ReadonlyHand;
import com.blackjack.pieces.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a participant in the game of BlackJack, manages a players current hands and chips
 */
public final class Player
{
    private int balanceInChips;
    private List<Hand> activeHands;
    private List<ReadonlyHand> completeHands;
    private boolean isDealer;
    private StringBuffer buffer;
    public final String Name;

    public Player()
    {
        this( false );
    }

    public Player( boolean isDealer )
    {
        this.isDealer = isDealer;
        activeHands = new ArrayList<Hand>();
        completeHands = new ArrayList<ReadonlyHand>();
        balanceInChips = isDealer ? 0 : 100;
        buffer = new StringBuffer();
        Name = ! isDealer ? "You" : "Dealer";
    }

    /**
     * Reduces the player's current total chips by the bet
     * @param chipTotalToBet
     * @return
     */
    private boolean bet( int chipTotalToBet )
    {
        if ( chipTotalToBet > balanceInChips)
        {
            return false;
        }

        balanceInChips -= chipTotalToBet;

        return  true;
    }

    /**
     * Adds winnings to the player's totals
     * @param chipsTotal
     */
    public void addWinnings( int chipsTotal )
    {
        balanceInChips += chipsTotal;
    }

    /**
     * The current total non-bet chips the player holds
     * @return
     */
    public int getCurrentTotalChips()
    {
        return balanceInChips;
    }

    /**
     * Clears all the hands from a player
     */
    public void clearHands()
    {
        activeHands.clear();
        completeHands.clear();
    }

    /**
     * Does the player have any hands being actively played?
     * @return
     */
    public boolean hasActiveHands()
    {
        return activeHands.size() > 0;
    }

    /**
     * Finish the current hand the player is playing.
     */
    public void completeCurrentHand()
    {
        if ( activeHands.size() == 0 )
        {
            throw new UnsupportedOperationException("There are no current hands to complete");
        }
        completeHands.add( activeHands.remove(0) );
    }

    /**
     * Give the players current hand another card
     * @param card
     */
    public void addCardToCurrentHand(Card card)
    {
        getCurrentHandInternal().addCard(card);
    }

    /**
     * If the player is the dealer turn on the option to show everyone his cards
     */
    public void turnOffDealerPrinting()
    {
        if ( ! isDealer )
        {
            throw new UnsupportedOperationException("This player is not a dealer");
        }

        getCurrentHandInternal().IsDealerHand = false;
    }

    /**
     * Get a readonly list of the player's hands
     *
     * @return
     */
    public Iterator<ReadonlyHand> getCompleteHands()
    {
        return Collections.unmodifiableList(completeHands).iterator();
    }

    /**
     * Add another hand to this player
     * @param hand
     */
    public void addHand( Hand hand )
    {
        activeHands.add(hand);
        bet(hand.getBet());

        if ( isDealer )
        {
            hand.IsDealerHand = true;
        }
    }

    /**
     * Get the hand the player is currently playing in a readonly state
     * @return
     */
    public ReadonlyHand getCurrentHand()
    {
        if ( activeHands.size() == 0 ) {
            throw new IllegalArgumentException("There are no current active hands");
        }

        return activeHands.get(0);
    }

    private Hand getCurrentHandInternal()
    {
        return activeHands.get(0);
    }

    /**
     * Can the player double down on the current hand?
     *  1) Hand has only two cards.
     *  2) Player has enough chips to double his current hands bet.
     * @return
     */
    public boolean canDoubleDown()
    {
        ReadonlyHand hand = getCurrentHand();
        return hand.getTotalCards() == 2 && getCurrentTotalChips() >= getCurrentHand().getBet() && hand.canDraw();
    }

    /**
     * Increase the current hands bet by the passed in bet and give the hand one final card
     * @param card
     * @param bet
     */
    public void doubleDown(Card card, int bet)
    {
        if (! canDoubleDown() ) {
            throw new IllegalStateException("You cannot double down on this hand.");
        }

        Hand hand = getCurrentHandInternal();
        hand.addToBet( bet );
        hand.addCard( card );
        // reduce current chips by additional bet
        bet( bet );
        completeCurrentHand();
    }

    /**
     * Is the player allowed to draw a another card for the current hand
     * @return
     */
    public boolean canDraw()
    {
        return getCurrentHand().canDraw();
    }

    /**
     * Can the player split the current hand
     * @return
     */
    public boolean canSplit()
    {
        ReadonlyHand hand = getCurrentHand();
        return hand.canSplit() && getCurrentTotalChips() >= hand.getBet();
    }

    /**
     * Splits the current hand into two hands and doubles the player's total bet
     *
     * @param newCard1
     * @param newCard2
     */
    public void split( Card newCard1, Card newCard2 )
    {
        if ( ! canSplit() )
        {
            throw new IllegalStateException("You cannot split this hand.");
        }

        // split the cards
        Hand newHand = getCurrentHandInternal().split();

        // add card to first hand
        getCurrentHandInternal().addCard(newCard1);

        // add card to 2nd hand
        newHand.addCard( newCard2 );

        activeHands.add( newHand );
        // reduce current chips by additional bet
        bet( newHand.getBet() );
    }

    @Override
    public String toString()
    {
        if ( isDealer )
        {
            buffer.append("Dealer Hand: ").append(activeHands.size() > 0 ? activeHands.get(0) : "");
            if ( getCurrentHand().isBusted() )
            {
                buffer.append(" (Busted)");
            }
        }
        else
        {
            int handPos = 1;

            int totalHands = activeHands.size() + completeHands.size();

            for ( Hand hand : activeHands )
            {
                appendHandToString(hand, totalHands, handPos, false );
                handPos++;
            }

            for ( ReadonlyHand hand : completeHands )
            {
                appendHandToString(hand, totalHands, handPos, true );
                handPos++;
            }
        }

        String str = buffer.toString();
        buffer.delete(0, buffer.length());

        return str;
    }

    private void appendHandToString( ReadonlyHand hand, int totalCount, int position, boolean played )
    {
        buffer.append( "Hand: " ).append(hand);

        if ( totalCount > 1 && position == 1 && ! played )
        {
            buffer.append( " (Current)" );
        }
        else if ( hand.isBusted() )
        {
            buffer.append( " (Busted)" );
        }
        else if ( played )
        {
            buffer.append(" (Standing)");
        }
        else if ( !played && totalCount > 1 )
        {
            buffer.append(" (Unplayed)");
        }

        buffer.append("\n");
    }
}
