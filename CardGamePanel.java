//*********************************************************************************
// CardGamePanel.java					  Authors: Evan Jackson & Derek Corrigan
//
// Represents the card games playing surface.  It creates all the buttons and
// button listeners to drive the game
//*********************************************************************************

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CardGamePanel extends JPanel
{
	private PlayingCardPanel panel1, panel2;
	private JButton start, fold, hit, stick, showdown, newGame;
	private JLabel chipLabel, resultLabel, playerLabel, dealerLabel;
	private JPanel resultPanel;
	private PlayBlackjack game =  new PlayBlackjack();

	//-----------------------------------------------------------------------
	// This constructor creates the buttons and adds the button listeners to
	// them.  it also adds the panels that displays the cards in play.
	//-----------------------------------------------------------------------
	public CardGamePanel()
	{
		setPreferredSize(new Dimension(1000,600));
		setBackground(Color.GREEN);

		ButtonListener listener = new ButtonListener();

		start = new JButton("Start");
		start.addActionListener(listener);
		add(start);

		fold = new JButton("Fold");
		fold.addActionListener(listener);
		add(fold);

		hit = new JButton("Hit");
		hit.addActionListener(listener);
		add(hit);

		stick = new JButton("Stick");
		stick.addActionListener(listener);
		add(stick);

		showdown = new JButton("Showdown");
		showdown.addActionListener(listener);
		add(showdown);

		newGame = new JButton("New Game");
		newGame.addActionListener(listener);
		add(newGame);

		//------------------------------------------------------
		// adds all the labels, and panels to the current panel
		//------------------------------------------------------
		panel1 = new PlayingCardPanel();
		panel2 = new PlayingCardPanel();
		resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension (500, 600));
		resultPanel.setBackground(Color.green);
		resultLabel = new JLabel("");
		resultLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		resultPanel.add(resultLabel);
		chipLabel = new JLabel("You have " + game.chips + " chips");
		playerLabel = new JLabel("Player");
		playerLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		dealerLabel = new JLabel("Dealer");
		dealerLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		add(chipLabel);
		add(panel1);
		add(playerLabel);
		add(panel2);
		add(dealerLabel);
		add(resultPanel);

		//---------------------------------------------------------------------------
		//This is used to refresh the panel every time something is added or removed
		//---------------------------------------------------------------------------
		revalidate();
		repaint();
	}

	//-----------------------------------------------------------------------
	// This class contains all the information to drive the game.  It drives
	// what happens when one of the buttons are pushed
	//-----------------------------------------------------------------------
	private class ButtonListener implements ActionListener
	{
		private int playerCount = 2; //used to keep track of the current card being displayed
		private int dealerCount = 2;
		private int result;
		private boolean gameStart = false;
		private JLabel flippedCard;//used to flip the dealers second card

		public void actionPerformed(ActionEvent event)
		{
			//-------------------------------------------------------------------------------
			// This is what happens when the start button is pushed. The players and dealers
			// first cards are revealed.
			//-------------------------------------------------------------------------------
			if(event.getSource() == start)
			{
				gameStart = true;
				for (int i = 0; i < 2; i++)
					panel1.add(new JLabel(game.getPlayerImages()[i]));
				panel2.add(new JLabel(game.getDealerImages()[0]));
				flippedCard = new JLabel(game.getDealerImages()[1]);
				panel2.add(flippedCard);
				start.setEnabled(false);

				if (game.gameOver)
				{
					hit.setEnabled(false);//the set enable method greys out the buttons so the cannot be used
					fold.setEnabled(false);
					stick.setEnabled(false);
					showdown.setEnabled(false);
					resultLabel.setText(game.gameResult);//displays game results
					flippedCard.setIcon(game.getDealerImages()[1]);//flips the dealers card
				}

				revalidate();
				repaint();
			}

			//----------------------------------------------------------
			// This drives what happens when the fold button is pressed
			//----------------------------------------------------------
			else if(event.getSource() == fold)
			{
				if (gameStart)
				{
					game.fold();
					resultLabel.setText(game.gameResult);
					fold.setEnabled(false);
					hit.setEnabled(false);
					stick.setEnabled(false);
					showdown.setEnabled(false);
				}

				revalidate();
				repaint();
			}

			//---------------------------------------------------------
			// This drives what happens when the hit button is pressed
			// A card is added to the players hand until he sticks or
			// is busted
			//---------------------------------------------------------
			else if(event.getSource() == hit)
			{
				if (gameStart)
				{
					result = game.hit();
					chipLabel.setText("You have " + game.chips + " chips");
					panel1.add(new JLabel(game.getPlayerImages()[playerCount]));
					playerCount++;

					//------------------------------------------------
					// If the player reaches 21 it stops the gameplay
					// and displays the results
					//------------------------------------------------
					if (result == -2)
					{
						panel1.add(new JLabel("You have reached 21"));
						hit.setEnabled(false);
						fold.setEnabled(false);
						stick.setEnabled(false);

						int temp = game.stick();
						flippedCard.setIcon(game.getDealerImages()[1]);
						if (temp > 0)
							for (int i = dealerCount; i <= dealerCount + temp; i++)
								panel2.add(new JLabel(game.getDealerImages()[i]));

						chipLabel.setText("You have " + game.chips + " chips");
						resultLabel.setText(game.gameResult);
					}

					//--------------------------------------------------------------
					// If the player has five cards in his hand gameplay is stopped
					// and the dealer continues then the results are displayed
					//--------------------------------------------------------------
					if (result == -1)
					{
						panel1.add(new JLabel("You have reached the limit of 5 cards"));
						hit.setEnabled(false);
						fold.setEnabled(false);
						stick.setEnabled(false);
						showdown.setEnabled(false);

						int temp = game.stick();
						flippedCard.setIcon(game.getDealerImages()[1]);
						if (temp > 0)
							for (int i = dealerCount; i <= dealerCount + temp; i++)
								panel2.add(new JLabel(game.getDealerImages()[i]));

						chipLabel.setText("You have " + game.chips + " chips");
						resultLabel.setText(game.gameResult);
					}

					//---------------------------------------------------------
					// If the player has more then 21 in his hand he loses and
					// gameplay is stopped and the results are displayed
					//---------------------------------------------------------
					if (result == 0)
					{
						resultLabel.setText("Bust! You lose...");
						flippedCard.setIcon(game.getDealerImages()[1]);
						hit.setEnabled(false);
						fold.setEnabled(false);
						stick.setEnabled(false);
						showdown.setEnabled(false);
					}
				}

				revalidate();
				repaint();
			}

			//-----------------------------------------------------------
			// This drives what happens when the stick button is pressed
			// the player stops playing and the dealer goes then results
			// are displayed.
			//-----------------------------------------------------------
			else if(event.getSource() == stick)
			{
				if (gameStart)
				{
					hit.setEnabled(false);
					showdown.setEnabled(false);
					fold.setEnabled(false);
					stick.setEnabled(false);
					int temp = game.stick();
					flippedCard.setIcon(game.getDealerImages()[1]);
					if (temp > 0)
						for (int i = dealerCount; i <= dealerCount + temp; i++)
							panel2.add(new JLabel(game.getDealerImages()[i]));

					chipLabel.setText("You have " + game.chips + " chips");
					resultLabel.setText(game.gameResult);
				}

				revalidate();
				repaint();
			}

			//--------------------------------------------------------------------
			// This drives what happens whne the showdown button is pressed both
			// the player and the dealer flip both there card and whoever has the
			// better hand wins
			//--------------------------------------------------------------------
			else if(event.getSource() == showdown)
			{
				if (gameStart)
				{
					hit.setEnabled(false);
					fold.setEnabled(false);
					stick.setEnabled(false);
					showdown.setEnabled(false);
					game.showdown();
					flippedCard.setIcon(game.getDealerImages()[1]);
					resultLabel.setText(game.gameResult);
					chipLabel.setText("You have " + game.chips + " chips");
				}

				revalidate();
				repaint();
			}

			//--------------------------------------------------------------------------------------
			// This drives what happens whne the newgame button is pressed.  It removes everything
			// from the panel and then adds a brand new hand of cards to the dealer and player
			//--------------------------------------------------------------------------------------
			else if(event.getSource() == newGame)
			{
				if (gameStart)
				{
					//------------------------------------------------------------------
					// If you run out of chips a message is displayed and you must quit
					//------------------------------------------------------------------
					if (game.chips == 0)
					{
						resultLabel.setText("You're done buddy!");
						fold.setEnabled(false);
						hit.setEnabled(false);
						showdown.setEnabled(false);
						newGame.setEnabled(false);
					}

					//----------------------------------------------------------------
					// This removes and then adds panels and labels to the main panel
					// and sets it up as it was when the start button was pushed
					//----------------------------------------------------------------
					else
					{
						playerCount = 2;
						dealerCount = 2;
						game.newGame();
						hit.setEnabled(true);
						stick.setEnabled(true);
						fold.setEnabled(true);
						showdown.setEnabled(true);
						resultLabel.setText("");
						remove(panel1);//all of these are removed from the panel
						remove(panel2);
						remove(chipLabel);
						remove(resultPanel);
						remove(playerLabel);
						remove(dealerLabel);

						//new hands are given the the player and dealer
						panel1 = new PlayingCardPanel();
						panel2 = new PlayingCardPanel();
						resultPanel = new JPanel();
						resultPanel.setPreferredSize(new Dimension(500, 600));
						resultPanel.setBackground(Color.green);
						resultLabel = new JLabel("");
						resultLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
						resultPanel.add(resultLabel);
						chipLabel = new JLabel("You have " + game.chips + " chips");
						playerLabel = new JLabel("Player");
						playerLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
						dealerLabel = new JLabel("Dealer");
						dealerLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
						add(chipLabel);
						add(panel1);
						add(playerLabel);
						add(panel2);
						add(dealerLabel);
						add(resultPanel);

						gameStart = true;
						for (int i = 0; i < 2; i++)
							panel1.add(new JLabel(game.getPlayerImages()[i]));

						panel2.add(new JLabel(game.getDealerImages()[0]));
						flippedCard = new JLabel(game.getDealerImages()[1]);
						panel2.add(flippedCard);//flips the dealers cards

						//sets the button so they cannot be pushed
						if (game.gameOver)
						{
							hit.setEnabled(false);
							fold.setEnabled(false);
							stick.setEnabled(false);
							showdown.setEnabled(false);
							resultLabel.setText(game.gameResult);
							flippedCard.setIcon(game.getDealerImages()[1]);
						}
					}
				}

				revalidate();
				repaint();
			}
		}
	}
}