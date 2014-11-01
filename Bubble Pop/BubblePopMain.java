import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** The "BubblePopMain" class.
  * This class runs the game Bubble Pop
  * @author Sofia Kim, Tracy Lei, Jessie Ma
  * @version June 18, 2012
 */
public class BubblePopMain extends JFrame
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


    public static void main (String[] args)
    {
	// Starts up the BubblePopMain frame
	BubblePopMain frame = new BubblePopMain ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // BubblePopMain class
