
public class Card {

	private Suit suit;
	private Face face;

	public Card(Suit suit,Face face){
		this.face = face;
		this.suit = suit;
	}
	//how to format strings well
	public String toString(){
		return String.format("suit is %s face is %s",suit,face);
	}
	public int value(){
		return face.getValue();
	}
	public Face getFace(){
		return face;
	}
}
