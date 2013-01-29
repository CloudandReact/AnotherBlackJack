package com.blackjack.game;

import com.blackjack.com.blackjack.pieces.TestDeck;
import com.blackjack.input.TestInterface;
import com.blackjack.pieces.Card;
import com.blackjack.pieces.Rank;
import com.blackjack.pieces.Suit;
import com.blackjack.players.Player;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class EngineTests
{
    @Test
    public void testBlackJack()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));

        tInterface.InitialBet.add(10);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 115 );
    }

    @Test
    public void testBlackJackPush()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.TEN));

        tInterface.InitialBet.add(20);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 100 );
    }

    @Test
    public void testNaturalPush()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.FIVE));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.FOUR));

        tInterface.InitialBet.add(20);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.STAND);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 100 );
    }

    @Test
    public void testBlackJackLoss()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));

        tInterface.InitialBet.add(10);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 90 );
    }

    @Test
    public void testNaturalBlackJackWin()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.NINE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));

        tInterface.InitialBet.add(10);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.HIT);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 110 );
    }

    @Test
    public void testSplit()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.EIGHT));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.NINE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));

        tInterface.InitialBet.add(10);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.SPLIT);
        tInterface.DesiredActions.add(Action.STAND);
        tInterface.DesiredActions.add(Action.STAND);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 120 );
    }

    @Test
    public void testSplitWithDouble()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.EIGHT));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.NINE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.ACE));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.FIVE));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.FOUR));

        tInterface.InitialBet.add(10);
        tInterface.DoubleDownBet.add(8);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.SPLIT);
        tInterface.DesiredActions.add(Action.STAND);
        tInterface.DesiredActions.add(Action.DOUBLEDOWN);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 128 );
    }

    @Test
    public void testTripleSplitWith2Losses()
    {
        TestInterface tInterface = new TestInterface();
        TestDeck deck = new TestDeck();
        Player player = new Player();
        GameEngine engine = new GameEngine(tInterface, deck, player);

        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.HEARTS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.EIGHT));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TWO));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.FIVE));
        deck.fakeDeck.add(new Card(Suit.DIAMONDS, Rank.TEN));
        deck.fakeDeck.add(new Card(Suit.CLUBS, Rank.TEN));

        // 10, 10
        // 10, 10 ; 10, 2
        // 10, 10 ; 10, 5; 10, 2
        // 10, 10 ; 10, 5, 10; 10, 2, 10

        tInterface.InitialBet.add(10);
        tInterface.DesiredActions.add(Action.DEAL);
        tInterface.DesiredActions.add(Action.SPLIT);
        tInterface.DesiredActions.add(Action.SPLIT);
        tInterface.DesiredActions.add(Action.STAND);
        tInterface.DesiredActions.add(Action.HIT);
        tInterface.DesiredActions.add(Action.HIT);
        tInterface.DesiredActions.add(Action.QUIT);

        engine.run();

        assertTrue( player.getCurrentTotalChips() == 90 );
    }
}
