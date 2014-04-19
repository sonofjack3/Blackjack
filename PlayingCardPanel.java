//*********************************************************************************
// PlayingCardPanel.java					  Authors: Evan Jackson & Derek Corrigan
//
// Represents a panel containing the playing cards for the player or dealer
//*********************************************************************************

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PlayingCardPanel extends JPanel
{
	//-------------------------------------------------------
	//sets the dimensions and background colour of the panel
	//-------------------------------------------------------
	public PlayingCardPanel()
	{
		setPreferredSize(new Dimension(600,200));
		setBackground(Color.RED);
	}
}