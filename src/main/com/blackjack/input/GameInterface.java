package com.blackjack.input;

import com.blackjack.game.Action;

import java.io.Closeable;
import java.util.List;

/**
 * Defines input entry points
 */
public interface GameInterface extends Closeable
{
    void showTitle();
    void displayOutput(String format, Object... args);
    int getDecks();
    int getDoubleDownBet(final int maxBet);
    int getInitialBet(final int maxBet);
    Action getOption(final List<Action> options);
}
