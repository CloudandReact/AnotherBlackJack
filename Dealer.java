
public class Dealer {
	private Hand h;
	private int maxVal=17;
	public void setHand(Hand h){
		this.h =h;
		if(h.value()>=17){
			//System.out.println("cannot hit hand value max");
			h.setFinished(true);
		}
		
	}
	public void addCard(Card c){
		if(h.value()>=17){
			System.out.println("cannot hit hand value greater than or equal to  " + maxVal);
			h.setFinished(true);
		}
		else{
			h.addCard(c);
		}
		
	}
	public boolean canHit(){
		return h.isFinished();
	}
	public void clearHand(){
		h=null;
	}
	public Hand getHand(){
		return h;
	}
}
