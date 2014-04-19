//*********************************************************************************
// Card.java					          Authors: Evan Jackson & Derek Corrigan
//
// Represents a single playing card.
//*********************************************************************************

import javax.swing.*;

public class Card
{
	private String suit, faceValue;
	private int value;
	private boolean faceUp;
	private ImageIcon image;
	private String imageLocation;

	//--------------------------------------------------------------------------
	// Constructor. Initializes card's value, suit, image and faceValue, which
	// is used in the card's toString method.
	//--------------------------------------------------------------------------
	public Card(int value, String suit, String faceValue, String imageLocation)
	{
		this.value = value;
		this.suit = suit;
		this.faceValue = faceValue;
		this.imageLocation = imageLocation;
		image = new ImageIcon(imageLocation);
		faceUp = true;
	}

	//------------------------------------------------------
	// Returns true if the card is face up, false otherwise
	//------------------------------------------------------
	public boolean isFaceUp()
	{
		return faceUp;
	}

	//------------------------------------------------------
	// Returns the ImageIcon assigned to this card.
	//------------------------------------------------------
	public ImageIcon getImage()
	{
		if (faceUp)
			return image;
		else
			return new ImageIcon("back.jpg");
	}

	//-------------------------------------------------------
	// "Flips" the card by changing the value of the boolean
	// variable isFaceUp and the card's ImageIcon.
	//-------------------------------------------------------
	public void flip()
	{
		if (isFaceUp())
		{
			faceUp = false;
			image = new ImageIcon("back.jpg");
		}
		else
		{
			faceUp = true;
			image = new ImageIcon(imageLocation);
		}
	}

	//-------------------------------------------
	// Returns the card's value.
	//-------------------------------------------
	public int value()
	{
		return value;
	}

	//--------------------------------------------
	// Compares this Card to another based on
	// each card's value.
	//--------------------------------------------
	public int compareTo(Card op2)
	{
		int value2 = op2.value();

		if (value < value2)
			return -1;
		else if (value > value2)
			return 1;
		else
			return 0;
	}

	//---------------------------------------------
	// Returns a one-line description of the card.
	//---------------------------------------------
	public String toString()
	{
		String str = "";
		if (faceUp)
		{
			str += faceValue + " - " + suit;
		}
		else
			str += "XXXXX";
		return str;
	}
}