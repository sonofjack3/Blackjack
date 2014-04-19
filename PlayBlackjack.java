//*********************************************************************************
// PlayBlackjack.java					Authors: Evan Jackson & Derek Corrigan
//
// Simulates a game of blackjack, with appropriate methods for each move.
//*********************************************************************************

import java.awt.*;
import javax.swing.*;

public class PlayBlackjack
{
	protected BlackjackHand dealer;
	protected BlackjackHand player;
	protected DeckOfCards deck;
	protected String gameResult;
	protected int chips = 100; //player starts with 100 chips
	protected boolean gameOver;
	private int hitCount = 0;
	private ImageIcon[] playerImages;
	private ImageIcon[] dealerImages;
	private int playerImageCount = 0; //tracks # of cards in player's hand
	private int dealerImageCount = 0; //tracks # of cards in dealer's hand

	//-----------------------------------------------------------------------
	// Sets up this game by dealing two cards each to the dealer and the
	// player, and automatically checks to see if either has blackjack.
	//-----------------------------------------------------------------------
	public PlayBlackjack()
	{
		gameOver = false;
		gameResult = "";
		deck = new DeckOfCards();
		player = new BlackjackHand();
		dealer = new BlackjackHand();
		playerImages = new ImageIcon[10];
		dealerImages = new ImageIcon[10];

		playerImages[playerImageCount] = player.addCard(deck);
		playerImageCount++;
		playerImages[playerImageCount] = player.addCard(deck);
		playerImageCount++;
		dealerImages[dealerImageCount] = dealer.addCard(deck);
		dealerImageCount++;
		dealerImages[dealerImageCount] = dealer.addCardDown(deck);
		dealerImageCount++;

		if (player.value() == 21 && dealer.value() != 21)
		{
			gameResult = "Blackjack! You win!";
			gameOver = true;
		}
		else if (player.value() != 21 && dealer.value() == 21)
		{
			gameResult = "Dealer blackjack! You lose...";
			dealerImages[1] = dealer.flip();
			gameOver = true;
		}
		else if (player.value() == 21 && dealer.value() == 21)
		{
			gameResult = "Tie game. House rules, dealer wins, you lose...";
			dealerImages[1] = dealer.flip();
			gameOver = true;
		}
	}

	//----------------------------------------------------------------
	// Deals a card to the player. Returns an int based on the
	// player's hand value or size. Decides the game result if the
	// player busts.
	//----------------------------------------------------------------
	public int hit()
	{
		if (hitCount == 0)
			chips -= 2; //chips are only deducted on the first move

		hitCount++;

		playerImages[playerImageCount] = player.addCard(deck);
		playerImageCount++;

		if (player.value() > 21) //bust returns 0
		{
			gameResult = "Bust! You lose...";
			dealerImages[1] = dealer.flip();
			return 0;
		}

		else if (player.size() == 5) //limit reached returns -1
			return -1;

		else if (player.value() == 21) //a hand of 21 returns -2
			return -2;

		else
			return 1;
	}

	//--------------------------------------------------------------
	// Causes the dealer to deal cards to itself until its value is
	// 16 and reveals its cards to the player. Returns an int
	// representing the number of times the dealer hits. Decides
	// the game winner.
	//--------------------------------------------------------------
	public int stick()
	{
		if (hitCount == 0)
			chips -= 2; //chips are only deducted on the first move

		dealerImages[1] = dealer.flip();

		int result = 0;

		while (dealer.value() < 17 && dealer.size() < 5)
		{
			dealerImages[dealerImageCount] = dealer.addCard(deck);
			dealerImageCount++;
			result++;
		}
		if (dealer.value() > 21)
		{
			gameResult = "Dealer bust! You win!";
			chips += 4;
		}
		else
		{
			if (player.compareTo(dealer) == -1)
				gameResult = "You lose...";
			else if (player.compareTo(dealer) == 1)
			{
				gameResult = "You win!";
				chips += 4;
			}
			else if (player.compareTo(dealer) == 0)
				gameResult = "Tie game. House rules, dealer wins, you lose...";
		}

		return result;
	}

	//----------------------------------------------------------------------
	// Immediately compares the player and dealer's hands at the moment
	// it is called, reveals the dealer's hand and decides the game winner.
	//----------------------------------------------------------------------
	public void showdown()
	{
		if (hitCount == 0)
			chips -= 2; //chips are only deducted on the first move

		dealerImages[1] = dealer.flip();

		if (player.compareTo(dealer) == -1)
			gameResult = "You lose...";
		else if (player.compareTo(dealer) == 1)
		{
			gameResult = "You win!";
			chips += 4;
		}
		else
			gameResult = "Tie game. House rules, dealer wins, you lose...";
	}

	//------------------------------------------------------------------------
	// Starts a new game and creates a new deck if there are less than 30
	// cards remaining in the deck. Checks immediately to see if either
	// player has blackjack.
	//------------------------------------------------------------------------
	public void newGame()
	{
		if (deck.cardsLeft() < 30)
			deck = new DeckOfCards();

		player = new BlackjackHand();
		dealer = new BlackjackHand();

		playerImageCount = 0;
		dealerImageCount = 0;

		playerImages = new ImageIcon[10];
		dealerImages = new ImageIcon[10];

		hitCount = 0;

		gameOver = false;

		playerImages[playerImageCount] = player.addCard(deck);
		playerImageCount++;
		playerImages[playerImageCount] = player.addCard(deck);
		playerImageCount++;
		dealerImages[dealerImageCount] = dealer.addCard(deck);
		dealerImageCount++;
		dealerImages[dealerImageCount] = dealer.addCardDown(deck);
		dealerImageCount++;

		if (player.value() == 21 && dealer.value() != 21)
		{
			gameResult = "Blackjack! You win!";
			gameOver = true;
		}
		else if (player.value() != 21 && dealer.value() == 21)
		{
			gameResult = "Dealer blackjack! You lose...";
			gameOver = true;
			dealerImages[1] = dealer.flip();
		}
		else if (player.value() == 21 && dealer.value() == 21)
		{
			gameResult = "Tie game. House rules, dealer wins, you lose...";
			gameOver = true;
			dealerImages[1] = dealer.flip();
		}
	}

	//-----------------------------------------------
	// Folds the player's hand.
	//-----------------------------------------------
	public void fold()
	{
		gameResult = "Player folds. You lose...";
	}

	//------------------------------------------------------
	// Returns an array of the images of the player's hand.
	//------------------------------------------------------
	public ImageIcon[] getPlayerImages()
	{
		return playerImages;
	}

	//-------------------------------------------------------
	// Returns an array of the images of the dealer's cards.
	//-------------------------------------------------------
	public ImageIcon[] getDealerImages()
	{
		return dealerImages;
	}
}