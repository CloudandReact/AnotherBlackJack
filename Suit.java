
public enum Suit {
	HEARTS(0),DIAMONDS(1),SPADES(2),CLUBS(3);

	private int value;
	private String name;
	Suit(int value){
		this.value = value;
		//get the name string  what does substring return
		name = name().charAt(0)+ name().substring(1,name().length()).toLowerCase();
	}

	public int getValue(){
		return value;
	}
	public String toString(){
		return name;
	}
    

}
