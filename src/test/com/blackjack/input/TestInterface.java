package com.blackjack.input;

import com.blackjack.game.Action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Simulate user input
 */
public class TestInterface implements GameInterface
{
    public Queue<Integer> DoubleDownBet;
    public Queue<Integer> InitialBet;
    public Queue<Action> DesiredActions;

    public TestInterface()
    {
        DoubleDownBet = new LinkedList<Integer>();
        InitialBet = new LinkedList<Integer>();
        DesiredActions = new LinkedList<Action>();
    }

    @Override
    public void showTitle()
    {
        // do nothing
    }

    @Override
    public void displayOutput(String format, Object... args)
    {
        // do nothing
    }

    @Override
    public int getDecks()
    {
        return 0;  //do nothing
    }

    @Override
    public int getDoubleDownBet(int maxBet)
    {
        return DoubleDownBet.poll();
    }

    @Override
    public int getInitialBet(int maxBet)
    {
        return InitialBet.poll();
    }

    @Override
    public Action getOption(List<Action> options)
    {
        return DesiredActions.poll();
    }

    @Override
    public void close() throws IOException
    {
        //do nothing
    }
}
