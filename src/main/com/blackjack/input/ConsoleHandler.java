package com.blackjack.input;

import com.blackjack.game.Action;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Handles gathering and processing user input as well as communicating with the user
 */
public final class ConsoleHandler implements GameInterface
{
    private interface NumericChecker
    {
        boolean isValid(int number);
    }

    private BufferedReader reader;
    private StringBuffer buffer;

    public ConsoleHandler()
    {
        reader = new BufferedReader(new InputStreamReader(System.in));
        buffer = new StringBuffer();
    }

    public void showTitle()
    {
        // text generated at http://patorjk.com/software/taag/
        displayOutput(
                "__________.__                 __          \n" +
                "\\______   \\  | _____    ____ |  | __     |A_ _ |\n" +
                " |    |  _/  | \\__  \\ _/ ___\\|  |/ /     |( v )|\n" +
                " |    |   \\  |__/ __ \\  \\___|    <       | \\ / |\n" +
                " |______  /____(____  /\\___  >__|_ \\     |  .  |\n" +
                "        \\/          \\/     \\/     \\/     |____V|\n" +
                "                      ____.              __\n" +
                "        |10 v |      |    |____    ____ |  | __\n" +
                "        |v v v|      |    \\__  \\ _/ ___\\|  |/ /\n" +
                "        |v v v|  /\\__|    |/ __ \\  \\____|     <\n" +
                "        |v v v|  \\_______(____  /\\___  >__|_  \\\n" +
                "        |   01|                \\/     \\/     \\/\n" +
                "\nWelcome to Black Jack!\n");
    }

    private String readInput( String prompt )
    {
        System.out.print(prompt);
        String str = "";
        try {
            str = reader.readLine();
        }
        catch (IOException ex) {
            System.out.println("I'm sorry I was unable to read that.");
        }

        return str.trim();
    }

    /**
     * Displays a message to the user
     * @param format
     * @param args
     */
    public void displayOutput(String format, Object ... args)
    {
        System.out.println(String.format(format, args));
    }

    /**
     * Gets the total decks to play a game with
     * @return
     */
    public int getDecks()
    {
        return getInteger("How many decks would you like to play with? ", new NumericChecker()
        {
            @Override
            public boolean isValid(int number) {
                return number > 0 && number < 11;
            }
        },
        "The number of decks must be between 1 and 10.");
    }

    /**
     * Handles getting a double down bet
     * @param maxBet
     * @return
     */
    public int getDoubleDownBet(final int maxBet)
    {
        return getBet( maxBet, String.format("What would you like to bet (min bet is 1) you can bet up to your current bet of %d? ", maxBet));
    }

    /**
     * Handles getting a initial bet for a hand
     * @param maxBet
     * @return
     */
    public int getInitialBet(final int maxBet)
    {
        return getBet(maxBet, String.format("What would you like to bet (min bet is 1) you have %d chip(s)? ", maxBet));
    }

    private int getBet(final int maxBet, String prompt)
    {
        return getInteger(prompt, new NumericChecker() {
            @Override
            public boolean isValid(int number) {
                return number > 0 && number <= maxBet;
            }
        },
        String.format("Your bet must be between 1 and your max bet %d.", maxBet));
    }

    /**
     * Processes which option the user has selected from the current options
     * @param options
     * @return
     */
    public Action getOption(final List<Action> options)
    {
        final HashMap<Integer,Action> optionMap = new HashMap<Integer, Action>(options.size());
        String prompt = getPromptForOptions(options, optionMap);

        int action = getInteger(prompt, new NumericChecker(){
            @Override
            public boolean isValid(int number) {
                return optionMap.containsKey(number);
            }
        },
                "That does not appear to be a valid option.\n");

        return optionMap.get(action);
    }

    private String getPromptForOptions( List<Action> options, HashMap<Integer,Action> validOptions )
    {
        int count = options.size();
        buffer.append("What would you like to do? \n");

        for (int i = 0; i < count; i++) {
            validOptions.put(i + 1, options.get(i));
            buffer.append(String.format("%d) %s\n", i + 1, options.get(i)));
        }
        buffer.append(": ");

        String str = buffer.toString();
        buffer.delete(0, buffer.length());
        return str;
    }

    private int getInteger(String prompt, NumericChecker inputValidator, String errorTxt)
    {
        while (true) {
            boolean isValid = false;
            String str = readInput(prompt);
            int value = 1;
            try {
                value = Integer.parseInt(str);
                isValid = inputValidator.isValid(value);
            }
            catch ( NumberFormatException ex ) {}

            if ( ! isValid ) {
                displayOutput(errorTxt);
            }
            else {
                return value;
            }
        }
    }

    @Override
    public void close() throws IOException
    {
         if ( reader != null )
         {
             reader.close();
             reader = null;
         }
    }
}
