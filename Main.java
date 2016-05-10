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
		Scanner keyboard = new Scanner(System.in);
		Deck deck = new Deck();
		Dealer dealer = new Dealer();

		ArrayList<Player> players = new ArrayList<Player>();
		System.out.println("enter number of players");
		int pCount = keyboard.nextInt();
		for (int i = 0; i < pCount; i++) {
			Player p = new Player(100);
			players.add(p);
		}

		// could make it static will see
		Utility util = new Utility();

		boolean exit = false;
		CurrentState.s = State.DEALING;
		// deck will eventually become empty
		while (!exit) {
			Hand dHand = new Hand(deck.deal(), deck.deal(), 0);
			dealer.setHand(dHand);
			for (Player pDeal : players) {

				System.out.println("Number of player hands :");
				int nHands = keyboard.nextInt();
				for (int i = 0; i < nHands; i++) {
					Hand pHand = new Hand(deck.deal(), deck.deal(),
							pDeal.bet(10));
					pDeal.addHand(pHand);
				}
			}

			// loop for multiple players

			// add while loop here for all hands
			for (Player pPlay : players) {

				while (!pPlay.isFinished()) {
					System.out.println(pPlay);
					System.out
							.println("Player what would you like to do 0 stay 1 for hit");
					int response = keyboard.nextInt();
					Hand pHand = pPlay.getCurrentHand();
					if (response == 0) {
						pPlay.incrementHands();
					} else {
						pHand.addCard(deck.deal());
						if (pHand.isFinished()) {
							pPlay.incrementHands();
						}

					}
				}
			}

			CurrentState.s = State.FINISHED;
			// handle loop through multiple hands
			boolean handAlive = false;
			for (Player p : players) {
				if (p.isFinished() && !p.isBusted()) {
					handAlive = true;
				}
			}

			if (handAlive) {
				// dealer time dealer vs dHand how to handle 17 check
				while (!dHand.isFinished()) {
					dealer.addCard(deck.deal());

				}

				System.out.println("dealer hand " + dHand);
				// loop for multiple players
				System.out.println(" player hand " + players.get(0));
				util.calculate(players, dealer);

			} else {
				// make another while loop
				System.out.println("dealer hand " + dHand);
				// loop for multiple players
				for (Player p : players) {
					System.out.println(" player hand " + p);
				}

				util.calculate(players, dealer);
				for (Player p : players) {
					System.out.println("player balance " + p.getBalance());
				}
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
