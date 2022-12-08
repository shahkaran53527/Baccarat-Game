import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	// This tests the constructor for Card class.
	@Test
	void CardConstructor() {
		Card c1 = new Card("King", 13);
		assertEquals(c1.suite, "King");
		assertEquals(c1.value, 13);
	}

}
