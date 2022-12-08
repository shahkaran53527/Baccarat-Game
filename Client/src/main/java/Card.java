import java.io.Serializable;

// Card object
public class Card implements Serializable {
	private static final long serialVersionUID = -4523267684010785421L;
	String suite;  // spade, heart, diamond, or club
	int value;  // values from 1 to 13
	
	// constructor
	Card(String theSuite, int theValue) {
		this.suite = theSuite;
		this.value = theValue;
	}
}