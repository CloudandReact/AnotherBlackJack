package com.blackjack.game;

import com.blackjack.input.GameInterface;
import com.blackjack.pieces.Card;
import com.blackjack.pieces.PlayingDeck;
import com.blackjack.players.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The primary event loop for running the game.
 */
public final class GameEngine
{
    private final PlayingDeck deck;
    private GameInterface userInterface;
    private final Player player;
    private final Player dealer;
    private final List<Action> initialActions;
    private Action currentAction;

    public GameEngine( final GameInterface userInterface, final PlayingDeck deck, final Player player )
    {
        this.userInterface = userInterface;
        this.deck = deck;
        this.player = player;
        dealer = new Player(true);

        initialActions =  Arrays.asList(Action.DEAL, Action.QUIT);

        // cleanup resources in the event something doesn't go as planned
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try {
                    if ( userInterface != null ) {
                        userInterface.close();
                    }
                }
                catch (IOException e) {}
            }
        });
    }

    /**
     * Game loop
     */
    public void run()
    {
        currentAction = Action.RESTART;

        while ( currentAction != Action.QUIT )
        {
            switch ( currentAction )
            {
                case DEAL:
                    deal();
                    break;
                case PLAY:
                    play();
                    break;
                case HIT:
                    hit();
                    break;
                case STAND:
                    player.completeCurrentHand();
                    currentAction = Action.PLAY;
                    showHands();
                    break;
                case SPLIT:
                    player.split( deck.draw(), deck.draw() );
                    showHand(player);
                    currentAction = Action.PLAY;
                    break;
                case DOUBLEDOWN:
                    doubleDown();
                    break;
                case RESTART:
                    userInterface.displayOutput("");
                    currentAction = userInterface.getOption( initialActions );
                    break;
            }
        }

        shutDown();
    }

    /**
     * Processes a double down action
     */
    private void doubleDown()
    {
        int bet = userInterface.getDoubleDownBet(player.getCurrentHand().getBet());
        Card card = deck.draw();
        outputDraw( player, card );
        player.doubleDown( card, bet );
        currentAction = Action.PLAY;
    }

    /**
     * Processes a deal action
     */
    private void deal()
    {
        if ( deck.needToShuffle() )
        {
            shuffle();
        }

        if ( player.getCurrentTotalChips() == 0 )
        {
            userInterface.displayOutput("You are out of chips, time to check out the craps table?");
            currentAction = Action.QUIT;
            return;
        }

        int bet = userInterface.getInitialBet(player.getCurrentTotalChips());
        Hand currentHand = new Hand( Arrays.asList(deck.draw(), deck.draw()), bet );
        player.addHand( currentHand );

        Hand dealerHand = new Hand( Arrays.asList( deck.draw(), deck.draw() ), 0 );
        dealer.addHand( dealerHand );

        showHands();

        currentAction = Action.PLAY;
        // if someone or both have a black jack this round is done
        if ( dealerHand.isBlackJack() || currentHand.isBlackJack() )
        {
            player.completeCurrentHand();
            finalizeHand();
            currentAction = Action.RESTART;
        }
    }

    /**
     * Determines the next action to be processed and sets it
     */
    private void play()
    {
        if ( player.hasActiveHands() )
        {
            List<Action> actions = new ArrayList<Action>();
            actions.add(Action.STAND);

            if ( player.canDraw() ) {
                actions.add( Action.HIT );
            }

            if ( player.canDoubleDown() ) {
                actions.add( Action.DOUBLEDOWN );
            }

            if ( player.canSplit() ) {
                actions.add( Action.SPLIT);
            }
            actions.add( Action.QUIT );

            currentAction = userInterface.getOption( actions );
        }
        else
        {
            ReadonlyHand dealerHand = dealer.getCurrentHand();
            while ( dealerHand.eval() < Hand.DEALER_HARD_STOP )
            {
                Card card = deck.draw();
                dealer.addCardToCurrentHand(card);
                outputDraw(dealer, card);
            }

            if ( dealerHand.eval() > Hand.BLACK_JACK )
            {
                userInterface.displayOutput("Dealer busted!!");
            }

            finalizeHand();
            currentAction = Action.RESTART;
        }
    }

    /**
     * Processes a hit action
     */
    private void hit()
    {
        Card card = deck.draw();
        player.addCardToCurrentHand(card);
        ReadonlyHand currentHand = player.getCurrentHand();
        outputDraw(player, card);
        if ( currentHand.isBusted() )
        {
            userInterface.displayOutput("You busted!!");
            player.completeCurrentHand();
        }
        else if ( currentHand.eval() == Hand.BLACK_JACK )
        {
            player.completeCurrentHand();
        }

        showHands();

        currentAction = Action.PLAY;
    }

    /**
     * Completes a round of play and moves the game to the next round options
     */
    private void finalizeHand()
    {
        dealer.turnOffDealerPrinting();
        userInterface.displayOutput("\nRound Summary:");
        showHands();

        outputHandResults( BetTotaller.tallyUpResults(player, dealer) );
        player.clearHands();
        dealer.clearHands();
        outputCurrentTotals();
    }

    /**
     * Displays the dealer and player's current hands
     */
    private void showHands()
    {
        userInterface.displayOutput( dealer.toString() );
        userInterface.displayOutput( player.toString() );
    }

    /**
     * Displays a players hand
     *
     * @param player
     */
    private void showHand( Player player )
    {
        userInterface.displayOutput( "\n" + player.toString() );
    }

    /**
     * Shows the card just drawn by a player
     *
     * @param player
     * @param card
     */
    private void outputDraw( Player player, Card card )
    {
        userInterface.displayOutput("%s draw %s", player.Name, card);
    }

    /**
     * Shows the end result of a hand
     * @param totalChips
     */
    private void outputHandResults( int totalChips )
    {
        userInterface.displayOutput("%s %s %d chip(s)", player.Name, totalChips > 0 ? "won" : "lost", totalChips);
    }

    /**
     * Shows the total winnings of the player and the house
     */
    private void outputCurrentTotals()
    {
        userInterface.displayOutput("%sr Total of Chips: %d House Total Chips: %d", player.Name,
                player.getCurrentTotalChips(), dealer.getCurrentTotalChips());
    }

    /**
     * Alerts the player that the deck is being shuffled
     */
    private void shuffle()
    {
        userInterface.displayOutput("Shuffling...\n");
        deck.shuffle();
    }

    /**
     * Turns the game off
     */
    private void shutDown()
    {
        userInterface.displayOutput("\nThanks for playing!");

        try {
            userInterface.close();
            userInterface = null;
        }
        catch (IOException ex) {}
    }
}
