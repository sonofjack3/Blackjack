//*********************************************************************************
// DeckOfCards.java					       Authors: Evan Jackson & Derek Corrigan
//
// Represents a deck of 52 cards and contains methods for using the deck.
//*********************************************************************************

import java.util.Random;
import java.util.ArrayList;

public class DeckOfCards
{
	private Card[] deck;
	private int cardCount;
	private final int ROYAL = 10, ACE = 11; //used for jack, queen, and king, and ace
	private final String J = "J", Q = "Q", K = "K", A = "A";

	//---------------------------------------------------
	// Constructor. Creates a deck of cards, shuffles it
	// and initializes the card count to 52.
	//---------------------------------------------------
	public DeckOfCards()
	{
		cardCount = 52;
		create();
		shuffle();
	}

	//------------------------------------------------------------------------
	// Creates and returns a deck of cards (an array of Card objects).
	//------------------------------------------------------------------------
	private Card[] create()
	{
		deck = new Card[52];
		String str1;
		String str2;

		//---------------------------------------------------------------------
		// Adds cards to the deck. Each suit's cards are added 2, 3, ..., 10
		// then if statements are used to handle the royal cards and the aces.
		//---------------------------------------------------------------------
		for (int count = 0; count < cardCount; count++)
		{
			//these Strings are used so that the toString method of Card
			//displays each card's face value properly
			str1 = Integer.toString(count + 2);
			str2 = Integer.toString((count % 13) + 2);

			if (count <= 8)
				deck[count] = new Card(count + 2, "Hearts", str1, count + 2 + "h.jpg");
			if (count == 9)
				deck[count] = new Card(ACE, "Hearts", A, "aceh.jpg");
			if (count == 10)
				deck[count] = new Card(ROYAL, "Hearts", J, "jackh.jpg");
			if (count == 11)
				deck[count] = new Card(ROYAL, "Hearts", Q, "queenh.jpg");
			if (count == 12)
				deck[count] = new Card(ROYAL, "Hearts", K, "kingh.jpg");
			if (count >= 13 && count <= 21)
				deck[count] = new Card((count % 13 + 2), "Diamonds", str2, (count % 13 + 2) + "d.jpg");
			if (count == 22)
				deck[count] = new Card(ACE, "Diamonds", A, "aced.jpg");
			if (count == 23)
				deck[count] = new Card(ROYAL, "Diamonds", J, "jackd.jpg");
			if (count == 24)
				deck[count] = new Card(ROYAL, "Diamonds", Q, "queend.jpg");
			if (count == 25)
				deck[count] = new Card(ROYAL, "Diamonds", K, "kingd.jpg");
			if (count >= 26 && count <= 34)
				deck[count] = new Card((count % 13) + 2, "Clubs", str2, (count % 13 + 2) + "c.jpg");
			if (count == 35)
				deck[count] = new Card(ACE, "Clubs", A, "acec.jpg");
			if (count == 36)
				deck[count] = new Card(ROYAL, "Clubs", J, "jackc.jpg");
			if (count == 37)
				deck[count] = new Card(ROYAL, "Clubs", Q, "queenc.jpg");
			if (count == 38)
				deck[count] = new Card(ROYAL, "Clubs", K, "kingc.jpg");
			if (count >= 39 && count <= 47)
				deck[count] = new Card((count % 13) + 2, "Spades", str2, (count % 13 + 2) + "s.jpg");
			if (count == 48)
				deck[count] = new Card(ACE, "Spades", A, "aces.jpg");
			if (count == 49)
				deck[count] = new Card(ROYAL, "Spades", J, "jacks.jpg");
			if (count == 50)
				deck[count] = new Card(ROYAL, "Spades", Q, "queens.jpg");
			if (count == 51)
				deck[count] = new Card(ROYAL, "Spades", K, "kings.jpg");
		}

		return deck;
	}

	//--------------------------------------------------------------
	// Shuffles the deck by swapping every card with another
	// randomly chosen card.
	//--------------------------------------------------------------
	private void shuffle()
	{
		ArrayList<Integer> randomInts = new ArrayList<Integer>();
		Random generator = new Random();
		int random;

		for (int i = 0; i < deck.length; i++)
		{
			random = generator.nextInt(cardCount);
			Card swap = deck[i];
			deck[i] = deck[random];
			deck[random] = swap;
		}
	}

	//--------------------------------------------------------------
	// Returns the card from the "top" of the deck (card at index 0)
	// and removes it from the deck by replacing every card with
	// the card above it (one index).
	//--------------------------------------------------------------
	public Card deal()
	{
		Card dealt = deck[0];
		for (int count = 0; count < deck.length; count++)
		{
			if (count == 51)
				break;
			else
				deck[count] = deck[count + 1];
		}
		cardCount--;
		return dealt;
	}

	//-----------------------------------
	// Deals a card, then flips it down.
	//-----------------------------------
	public Card dealDown()
	{
		Card dealtDown = deal();
		dealtDown.flip();
		return dealtDown;
	}

	//-----------------------------------------------
	// Returns the number of cards left in the deck.
	//-----------------------------------------------
	public int cardsLeft()
	{
		return cardCount;
	}
}