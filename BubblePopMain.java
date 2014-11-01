import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** The "BubblePopMain" class.
  * This class runs the game Bubble Pop
  * @author Sofia Kim, Tracy Lei, Jessie Ma
  * @version June 18, 2012
 */
public class BubblePopMain extends JFrame// implements ActionListener
{
    // Program variable for the game board
    private BubblePopBoard gameBoard;
   
    /** Constructs the BubblePop main frame
      */
    public BubblePopMain ()
    {
	// Sets up the frame for the game
	super ("Bubble Pop");
	setResizable (false);

	// Creates a bubble icon for the game
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("Icon.png"));

	// Sets up the Bubble Pop board that plays most of the game
	// and adds it to the centre of this frame
	gameBoard = new BubblePopBoard ();
	getContentPane ().add (gameBoard, BorderLayout.CENTER);

	// Centre the frame in the middle (almost) of the screen
	Dimension screen = Toolkit.getDefaultToolkit ().getScreenSize ();
	setLocation ((screen.width - gameBoard.BOARD_SIZE.width) / 2,
		(screen.height - gameBoard.BOARD_SIZE.height) / 2 - 100);
    }


    /** Responds to a Menu Event.  This method is needed since our
	  * Connect Four frame implements ActionListener
	  * @param event the event that triggered this method
	  */
   // public void actionPerformed (ActionEvent event)
   // {
	// if (event.getSource () == newOption)   // Selected "New"
	// {
	//     gameBoard.newGame ();
	// }
	// else if (event.getSource () == exitOption)  // Selected "Exit"
	// {
	//     hide ();
	//     System.exit (0);
	// }
	// else if (event.getSource () == soundOption) // Selected "Sound"
	// {
	//     songs.hyunaPop ();
	// }
	// else if (event.getSource () == rulesMenuItem)  // Selected "Rules"
	// {
	//     JOptionPane.showMessageDialog (this,
	//             "Player's turn is depicted by the image at top." +
	//             "\n\nPlayer selects a column by clicking the mouse," +
	//             "\nor by pressing the arrow keys and/or Enter key." +
	//             "\n\nFirst player to get 4-in-a-row in any direction " +
	//             "wins. \n\nGood luck!",
	//             "Rules",
	//             JOptionPane.INFORMATION_MESSAGE);
	// }
	// else if (event.getSource () == aboutMenuItem)  // Selected "About"
	// {
	//     JOptionPane.showMessageDialog (this,
	//             "by JESSIE MA" +
	//             "\n\u00a9 2012", "About Connect Four",
	//             JOptionPane.INFORMATION_MESSAGE);
	// }
   // }


    public static void main (String[] args)
    {
	// Starts up the BubblePopMain frame
	BubblePopMain frame = new BubblePopMain ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // BubblePopMain class
