import java.util.ArrayList;

//implement a current hand to add cards to or
// gui based pass hands to something and have them modified
public class Player {
	private int balance;
	private int winnings;
	private ArrayList<Hand> hands = new ArrayList<Hand>();
	private int currentBets;
	private int currentHand = 0;

	public Player(int balance) {
		this.balance = balance;
		currentBets = 0;
	}

	public int bet(int b) {
		if (balance >= b) {
			currentBets += b;
			balance = balance - b;
			addToWinning(-b);
			return b;
		} else {
			System.out.println("cannot bet bet exceeds balance");
			return -1;
		}
	}

	public Hand getCurrentHand() {
		return hands.get(currentHand);
	}

	public int getBalance() {
		return balance;
	}

	public void incrementHands() {
		currentHand++;
	}

	public boolean finished() {
		for (int i = 0; i < hands.size(); i++) {
			if (hands.get(i).isFinished()) {

			} else {
				return false;
			}
		}
		return true;
	}

	public void addToWinning(int w) {
		winnings += w;
		balance += w;
	}

	public ArrayList<Hand> getHands() {
		return hands;
	}

	public void addHand(Hand h) {
		hands.add(h);
	}

	public int getHandSize() {
		return hands.size();
	}

	public void clearHands() {
		hands.clear();
		currentHand = 0;
	}

}
