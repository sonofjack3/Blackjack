//***************************************************************************************
// CardGame.java					  Authors: Evan Jackson & Derek Corrigan
//
// This class is the driver for the blackjack game.  It adds all the panels to the frame
//***************************************************************************************

import java.awt.*;
import javax.swing.*;

public class CardGame
{
	//------------------------------------------------
	// This is a JFrame containing the blackjack game
	//------------------------------------------------
	public static void main (String[] args)
	{
		JFrame frame = new JFrame("Blackjack!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CardGamePanel panel = new CardGamePanel();
		frame.validate();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}