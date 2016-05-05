import java.util.ArrayList;
import java.util.Random;


public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	Random r = new Random();
	private int cDecks=0;
	public Deck(){
		cDecks=1;
		buildDeck(cDecks);
	}
	public Deck(int cDecks){
		if(cDecks>1 && cDecks<10){
			this.cDecks= cDecks;
		}
		else{
			cDecks=1;
		}
		buildDeck(cDecks);
	}
	private void buildDeck(int count){
		for (int i=0;i<count;i++){
			buildDeck();
		}
	}
	private void buildDeck(){
		//need to shuffle
		//should they have same name or not
		//what does values return
		for(Suit suit:Suit.values()){
			for(Face face:Face.values()){
				Card c = new Card(suit, face);
				cards.add(c);

			}
		}

	}
	public Card deal(){
		int size = cards.size();
		if(size>0){
			int topCard = r.nextInt(size);
			return cards.remove(topCard);
		}
		else{
			System.out.println("deck is empty");
			return null;
		}

	}
	public void shuffle(){
		clear();
		buildDeck(cDecks);
	}
	private void clear(){
		cards.clear();
	}
}
