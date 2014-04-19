//*********************************************************************************
// BlackjackHand.java					  Authors: Evan Jackson & Derek Corrigan
//
// Represents a blackjack hand with at most 5 cards.
//*********************************************************************************

import java.awt.*;
import javax.swing.*;

public class BlackjackHand
{
	public Card[] hand;
	private int cardCount, result;

	//constructor for blackjackHand, creates an array that holds 5 cards,
	//initializes cardCount to 0
	public BlackjackHand()
	{
		hand = new Card[5];
		cardCount = 0;
	}

	//flips the second card in the hand
	public ImageIcon flip()
	{
		hand[1].flip();
		return hand[1].getImage();
	}

	//addCard method adds a card to the hand
	//and returns the image associated with that card
	public ImageIcon addCard(DeckOfCards deck)
	{
		hand[cardCount] = deck.deal();
		cardCount++;
		return hand[cardCount - 1].getImage();
	}

	//addCardDown method adds a card facedown to the hand
	//and returns the image associated with that card
	public ImageIcon addCardDown(DeckOfCards deck)
	{
		hand[cardCount] = deck.dealDown();
		cardCount++;
		return hand[cardCount - 1].getImage();
	}

	//adds up all the values of the cards and returns the result
	public int value()
	{
		result = 0;

		for(int count = 0; count < cardCount; count++)
		{
			result += hand[count].value();
		}

		reduceHand();

		return result;
	}

	//size method returns the number of cards in the hand
	public int size()
	{
		return cardCount;
	}

	//reduceHand method reduces the value of an ace from an
	//11 to a 1 in certain cases
	private void reduceHand()
	{
		int aceCount = 0;
		for(int count = 0; count < cardCount; count++)
		{
			if(hand[count].value() == 11)
				aceCount++;
		}

		//these are the cases where the aces are reduced to a value of 1
		if (aceCount == 0)
			result -= 0;
		else if(aceCount == 4)
			result -= 30;
		else if(aceCount == 3)
			result -= 20;
		else if(aceCount == 2 && result - 22 <= 9)
			result -= 10;
		else if(aceCount == 2)
			result -= 20;
		else if(aceCount == 1 && result > 21)
			result -= 10;
		else
			result -= 0;
	}

	//compareTo method compares the values of 2 hands of cards
	public int compareTo(Object obj)
	{
		BlackjackHand r = (BlackjackHand)obj;

		int target = r.value();

		if(target == result)
			return 0;
		else if(target < result)
			return 1;
		else
			return -1;
	}

	//-------------------------------------------------------------
	// Sorts the player's hand and returns a one-line description
	// of the hand (the cards).
	//-------------------------------------------------------------
	public String toString()
	{
		String str = "";

		int min;
		Card temp;

		//selection sort algorithm to sort the player's hand
		for (int index = 0; index < cardCount - 1; index++)
		{
			min = index;
			for (int scan = index + 1; scan < cardCount; scan++)
				if (hand[scan].compareTo(hand[min]) < 0)
					min = scan;

			temp = hand[min];
			hand[min] = hand[index];
			hand[index] = temp;
		}

		for (int i = 0; i < cardCount; i++)
			str += hand[i] + "\t";

		return str;
	}
}