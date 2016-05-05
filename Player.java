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
		if (!(currentHand < hands.size())) {
			System.err.println("out of bounds");
		}
		hands.get(currentHand).setFinished(true);
		currentHand++;
	}

	public boolean isFinished() {
		for (int i = 0; i < hands.size(); i++) {
			if (hands.get(i).isFinished()) {

			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isBusted() {
		for (int i = 0; i < hands.size(); i++) {
			if (!hands.get(i).isBusted()) {
				return false;
			} else {

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

	public void addCard(Card c) {
		hands.get(currentHand).addCard(c);
	}

	public int getHandSize() {
		return hands.size();
	}

	public void clearHands() {
		hands.clear();
		currentHand = 0;
		currentBets = 0;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Hand h : hands) {
			sb.append(h.toString());
		}
		return sb.toString();
	}

}
