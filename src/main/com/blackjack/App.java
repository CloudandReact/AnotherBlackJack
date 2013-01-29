package com.blackjack;

import com.blackjack.game.GameEngine;
import com.blackjack.input.ConsoleHandler;
import com.blackjack.pieces.Deck;
import com.blackjack.pieces.MultiDeck;
import com.blackjack.pieces.PlayingDeck;
import com.blackjack.players.Player;

/**
 * App entry point
 */
public final class App
{
    public static void main(String[] args)
    {
        ConsoleHandler userInterface = new ConsoleHandler();
        userInterface.showTitle();
        int decksInPlay = userInterface.getDecks();

        PlayingDeck deck = decksInPlay > 1 ? new MultiDeck(decksInPlay) : new Deck();
        new GameEngine(userInterface, deck, new Player()).run();
    }
}
