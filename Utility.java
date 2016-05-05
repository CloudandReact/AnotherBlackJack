import java.util.ArrayList;


public class Utility {

	//can just delete hand if player is busted
	private final int THREETOONE=3;
	private final int TWOTOONE=2;
	public void calculate(ArrayList<Player> players, Dealer d){
		Hand dealerHand = d.getHand();
		for(Player p: players){
			
			ArrayList<Hand> pHands = p.getHands();
			for(Hand h: pHands){
				int handBet = h.getBet();
				if(h.isBusted()){
					//increment losses
				}
				else if(h.value()==21 && h.getSize()==2 && dealerHand.value()!=21){
					//increment winnings
					
					p.addToWinning(handBet*THREETOONE);
				}
				else if(dealerHand.isBusted() || h.value()>dealerHand.value()){
					p.addToWinning(handBet*TWOTOONE);
				}
			}
		}
		clear(players,d);
	}
	public void clear(ArrayList<Player> players, Dealer d){
		d.clearHand();
		for(Player p: players){
			p.clearHands();
		}
	}
}
