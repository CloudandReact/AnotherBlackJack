import java.util.ArrayList;

//do ace count in hand
public class Hand {

	private ArrayList<Card> cards = new ArrayList<Card>();
	private int bet;
	private final int MAX=5;
	private int aces=0;
	boolean finished=false;
	public Hand(Card c1,Card c2,int bet){
		cards.add(c1);
		cards.add(c2);
		if(bet>0){
			this.bet = bet;
		}
		else{
			bet = 0;
		}

	}
	public int getBet(){
		return bet;
	}
	public void addCard(Card c){
		if(getSize()<MAX){
			cards.add(c);
		}
		else{
			System.out.println("hand size is 5 cannot add a card");
			finished = true;
		}
	}
	public int getSize(){
		return cards.size();
	}
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	public boolean isFinished(){
		finished = finished || isBusted();
		return finished;
	}
	public int value(){
		int hValue=0;
		for(Card c:cards){
			hValue += c.value();
			if(c.getFace()==Face.ACE){
				aces++;
			}

		}
		while(hValue>21 && aces>0){
			hValue = hValue-10;
			aces--;
		}
		return hValue;
	}
	public boolean isBusted(){
		return value()>21;
	}
	//handling cases dealer and player states of game and string conversion
	public String toString(){
		String handInfo ="";

		for(int i=0;i<cards.size();i++){
			if(i==1 && bet==0 &&!isFinished()){

				continue;
			}
			handInfo+= cards.get(i).toString() + ", ";
		}
		//need to check all players finished game state
		handInfo +=  bet==0  &&!(CurrentState.s==State.FINISHED)? " card hidden ": ("\nvalue "+value());
		return handInfo;
	}

}
