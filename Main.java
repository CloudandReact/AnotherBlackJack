import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * for(Suit i:Suit.values()){ System.out.println(i); }
		 * 
		 * Deck d = new Deck(5); for(int i=0;i<52*5;i++){
		 * System.out.println(d.deal()); }
		 */

		Deck deck = new Deck();
		Dealer dealer = new Dealer();
		Player p = new Player(100);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);
		// could make it static will see
		Utility util = new Utility();
		Scanner keyboard = new Scanner(System.in);
		boolean exit = false;
		CurrentState.s = State.DEALING;
		// deck will eventually become empty
		while (!exit) {
			System.out.println("Number of player hands :");
			int nHands = keyboard.nextInt();
			for (int i = 0; i < nHands; i++) {
				Hand pHand = new Hand(deck.deal(), deck.deal(), p.bet(10));
				p.addHand(pHand);
			}
			Hand dHand = new Hand(deck.deal(), deck.deal(), 0);

			dealer.setHand(dHand);

			System.out.println("dealer hand " + dHand);
			// loop for multiple players

			// add while loop here for all hands
			while (!players.get(0).isFinished()) {
				System.out.println(players.get(0));
				System.out
				.println("Player what would you like to do 0 stay 1 for hit");
				int response = keyboard.nextInt();
				Hand pHand = players.get(0).getCurrentHand();
				if (response == 0) {
					players.get(0).incrementHands();
				} else {
					pHand.addCard(deck.deal());
					if(pHand.isFinished()){
						players.get(0).incrementHands();
					}

				}
			}
			CurrentState.s = State.FINISHED;
			// handle loop through multiple hands
			if (players.get(0).isFinished() && !players.get(0).isBusted()) {
				// dealer time dealer vs dHand how to handle 17 check
				while (!dHand.isFinished()) {
					dealer.addCard(deck.deal());

				}

				System.out.println("dealer hand " + dHand);
				// loop for multiple players
				System.out.println(" player hand " + players.get(0));
				util.calculate(players, dealer);
				System.out.println("player balance " + p.getBalance());

			} else {
				// make another while loop
				System.out.println("dealer hand " + dHand);
				// loop for multiple players
				System.out.println(" player hand " + players.get(0));
				util.calculate(players, dealer);
				System.out.println("player balance " + p.getBalance());
			}
			System.out.println("would you like to exit ? write exit");
			keyboard.nextLine();
			String finish = keyboard.nextLine();
			exit = exit || finish.equalsIgnoreCase("exit");
		}
		System.out.println("game is over results todo");

	}

	public static void printP(Hand pHand) {
		System.out.println(ANSI_BLUE + "player hand " + pHand + " "
				+ ANSI_RESET);
	}

}
