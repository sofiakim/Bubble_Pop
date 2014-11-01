import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** A help-screen demo that also shows how to draw images and move a single player using the keyboard.
  * NOTE: you must have the "Images" folder (and all its images) in the same folder as this program
  * @author: ICS3U
  * @version: 2009 ICS3U
 */
public class BoardFrame extends JFrame implements ActionListener
{
    // Program variables (declared at the top, these can be used or changed by any method)
    private DrawingPanel drawingArea;
    private Image imageBackground, sideImage, imageNewGame, imageNewGameBlack, imageHelp, imageHelpBlack,
	imageRank, imageRankBlack, imageAbout, imageAboutBlack, imageExit, imageExitBlack, introImageBackground;
    //private boolean helpScreen;
    private boolean highlightNewGame, highlightHelp, highlightRank, highlightAbout, highlightExit, gameOver;
    private JMenuItem newOption, exitOption, instructionsOption, aboutOption;
    private int gameStatus;
    private BubblePopBoard gameBoard;


    /** Constructs a HelpDemo frame and sets up the game to start.
      */
    public BoardFrame ()
    {

	// Sets up the game frame
	setTitle ("Bubble Pop menu");
	setLocation (100, 50);
	setResizable (false);

	// background image was made 550x350 pixels (to fit 11 columns by 7 rows, with each square being 50 pixels wide & high)
	introImageBackground = new ImageIcon ("project images/BubblePopStartMenu.png").getImage ();

	// load images
	// background image was made 550x350 pixels (to fit 11 columns by 7 rows, with each square being 50 pixels wide & high)
	sideImage = new ImageIcon ("project images 2/side bar.png").getImage ();
	imageBackground = new ImageIcon ("project images 2/circlebackground.png").getImage ();
	//imageHelp1 = new ImageIcon ("Images/HelpInstructions1.png").getImage ();
	imageNewGame = new ImageIcon ("project images 2/new game.png").getImage ();
	imageNewGameBlack = new ImageIcon ("project images 2/new game black.png").getImage ();
	imageHelp = new ImageIcon ("project images 2/help.png").getImage ();
	imageHelpBlack = new ImageIcon ("project images 2/help black.png").getImage ();
	imageRank = new ImageIcon ("project images 2/rank.png").getImage ();
	imageRankBlack = new ImageIcon ("project images 2/rank black.png").getImage ();
	imageAbout = new ImageIcon ("project images 2/about.png").getImage ();
	imageAboutBlack = new ImageIcon ("project images 2/about black.png").getImage ();
	imageExit = new ImageIcon ("project images 2/exit.png").getImage ();
	imageExitBlack = new ImageIcon ("project images 2/exit black.png").getImage ();




	// set size of the drawing panel to accommodate your images (in this case the clouds and the side images)
	Dimension imageSize = new Dimension (210, 600);

	//create and add the drawing panel.
	drawingArea = new DrawingPanel (imageSize);
	Container contentPane = getContentPane ();
	contentPane.add (drawingArea, BorderLayout.EAST);
	// Sets up the Bubble Pop board that plays most of the game
	// and adds it to the centre of this frame
	gameBoard = new BubblePopBoard ();
	getContentPane ().add (gameBoard, BorderLayout.CENTER);
    //    gameBoard.setVisible (false);
	gameStatus = 1;

	addMenus ();

    }


    public void actionPerformed (ActionEvent event)
    {
	// If the new option is selected, the board is reset and a new game begins.
	//if (event.getSource () == newOption)
	//    newGame (1);
	// Closes the game screen if the exit option is selected.
	if (event.getSource () == exitOption)
	{
	    dispose ();
	    System.exit (0);
	}
	//Display help screen if the instructions option was selected by user.
	// else if (event.getSource () == instructionsOption)
	// {
	//     helpScreen = true;
	//     repaint ();
	// }
	// Displays copyright information if the about option is selected.
	else if (event.getSource () == aboutOption)
	{
	    JOptionPane.showMessageDialog (this, "\u00a9 2009 By ... ",
		    "About HelpDemo", JOptionPane.INFORMATION_MESSAGE);
	}
    }


    // For the menu and menuitems
    private void addMenus ()
    {
	// Sets up the Game MenuItems.
	newOption = new JMenuItem ("New");
	newOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK));
	newOption.addActionListener (this);
	exitOption = new JMenuItem ("Exit");
	exitOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));
	exitOption.addActionListener (this);

	// Sets up the Help MenuItems.
	instructionsOption = new JMenuItem ("Instructions");
	instructionsOption.setMnemonic ('I');
	instructionsOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_I, InputEvent.CTRL_MASK));
	instructionsOption.addActionListener (this);
	aboutOption = new JMenuItem ("About");
	aboutOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_A, InputEvent.CTRL_MASK));
	aboutOption.setMnemonic ('I');
	aboutOption.addActionListener (this);

	// Sets up the Game and Help Menus.
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.setMnemonic ('G');
	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');

	// Add each MenuItem to the Game Menu (with a separator).
	gameMenu.add (newOption);
	gameMenu.addSeparator ();
	gameMenu.add (exitOption);

	// Add each MenuItem to the Help Menu (with a separator).
	helpMenu.add (instructionsOption);
	helpMenu.addSeparator ();
	helpMenu.add (aboutOption);

	// Add the GameMenu, GridMenu and HelpMenu to the top menu bar.
	JMenuBar mainMenu = new JMenuBar ();
	mainMenu.add (gameMenu);
	mainMenu.add (helpMenu);
	// Displays the menus.
	setJMenuBar (mainMenu);
    }


    // Creates and draws the main program panel that the user sees
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object of the specified dimension/size
	  */
	public DrawingPanel (Dimension size)
	{
	    setPreferredSize (size);  // set size of this panel
	    setFont (new Font ("Arial", Font.BOLD, 20));  // Font for drawing text in this panel with g.drawString()

	    // Add mouse listeners  to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());

	}

	/** Repaint the drawing panel.
	  * @param g The Graphics context.
	  */
	public void paintComponent (Graphics g)
	{

	    super.paintComponent (g);  // Tells java to do its preliminary work for drawing.
	    if (gameStatus == 1)
		g.drawImage (introImageBackground, 0, 0, this);
	    else
		g.drawImage (imageBackground, 0, 0, this);
	    g.drawImage (sideImage, 0, 0, this);
	    // g.setColor (Color.YELLOW);
	    // g.drawString ("Level: " + String.valueOf (level), 575, 25);
	    // g.drawString ("Moves: ", 575, 50);
	    // g.drawString ("Row: " + String.valueOf (currentRow), 575, 75);
	    // g.drawString ("Col: " + String.valueOf (currentColumn), 575, 100);



	    // Draw help image in the sidebar. If the mouse is over the help image, "highlight" it by drawing a RED "Help" image.
	    // The highlightHelp variable is set to true in the mouseMoved method when the mouse is over the "Help" image.
	    if (highlightNewGame)
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageNewGameBlack, 725, 110, 150, 60, this);
	    else
		g.drawImage (imageNewGame, 725, 110, 150, 60, this);

	    if (highlightHelp)
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageHelpBlack, 725, 190, 150, 60, this);
	    else
		g.drawImage (imageHelp, 725, 190, 150, 60, this);

	    if (highlightRank)
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageRankBlack, 725, 270, 150, 60, this);
	    else
		g.drawImage (imageRank, 725, 270, 150, 60, this);

	    if (highlightAbout)
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageAboutBlack, 725, 350, 150, 60, this);
	    else
		g.drawImage (imageAbout, 725, 350, 150, 60, this);

	    if (highlightExit)
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageExitBlack, 725, 430, 150, 60, this);
	    else
		g.drawImage (imageExit, 725, 430, 150, 60, this);


	    // Display a help screen, if user requested it. Draw helpscreen last so it appears over top everything else.
	    //if (helpScreen)
	    //{
	    //    g.drawImage (imageHelp1, 50, 25, this);
	    //    g.drawImage (imageExitHelp, 325, 275, this);
	    //}

	} // paintComponent Method
    } // DrawingPanel


    /** Responds when mouse is pressed
     */
    private class MouseHandler extends MouseAdapter
    {
	/** Responds to a mousePressed event
	 * @param event   Information about the the mouse when its button was pressed.
	 */
	public void mousePressed (MouseEvent event)
	{
	    Point pressed = event.getPoint ();
	    //Check if mouse was pressed over the Exit image
	    if (pressed.x >= 725 && pressed.x < 875 && pressed.y >= 430 && pressed.y < 490)
	    {
		dispose ();
		System.exit (0);
	    }
	    else
	    {
		gameStatus = 2;
		gameBoard.setVisible (true);
	    }
	    repaint ();
	    // // Check if mouse was pressed over the Help image
	    // if (pressed.x >= 600 && pressed.x < 750 && pressed.y >= 230 && pressed.y < 290)
	    // {
	    //     // Repaint the panel with help screen showing
	    //     helpScreen = true;
	    //     repaint ();
	    //     setCursor (Cursor.getDefaultCursor ()); // change mouse cursor from a hand to a regular pointer
	    // }
	    //
	    // // Check if mouse was pressed over the the Exit image of the help screen
	    // if (helpScreen == true && pressed.x >= 325 && pressed.x < 380 && pressed.y >= 275 && pressed.y < 299)
	    // {
	    //     helpScreen = false;  // don't show any help screen
	    //     repaint ();  //Repaint the screen to show any changes
	    //     setCursor (Cursor.getDefaultCursor ()); // change mouse cursor from a hand to a regular pointer
	    // }
	}
    }


    /** Monitors mouse movement over the game panel and responds
    */
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	/** Responds to mouse-movement inputs
	  * @param event   The event created by the mouse movement
	*/
	public void mouseMoved (MouseEvent event)
	{
	    Point pos = event.getPoint ();

	    // If mouse is over the "New game" image of the main screen, then highlight/change the "new game" image
	    if (pos.x >= 725 && pos.x < 875 && pos.y >= 110 && pos.y < 170) //&& helpScreen == false)
	    {
		highlightNewGame = true;

		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR)); // change mouse cursor to a hand
	    }
	    else
	    {
		highlightNewGame = false;
		setCursor (Cursor.getDefaultCursor ());  // change mouse cursor to its normal image
	    }


	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "Help" image of the main screen, then highlight/change the "help" image
	    if (pos.x >= 725 && pos.x < 875 && pos.y >= 190 && pos.y < 250) // && helpScreen == false)
	    {
		highlightHelp = true;
		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR)); // change mouse cursor to a hand
	    }
	    else
	    {
		highlightHelp = false;
		setCursor (Cursor.getDefaultCursor ());  // change mouse cursor to its normal image
	    }

	    // If mouse is over the help screen "exit", change the mouse cursor to a hand
	    // if (helpScreen == true && pos.x >= 325 && pos.x < 380 && pos.y >= 275 && pos.y < 299)
	    //     setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));

	    repaint (); //Repaint the screen to show any changes


	    // If mouse is over the "Rank" image of the main screen, then highlight/change the "rank" image
	    if (pos.x >= 725 && pos.x < 875 && pos.y >= 270 && pos.y < 330) // && helpScreen == false)
		highlightRank = true;
	    else
		highlightRank = false;

	    // If mouse is over the help screen "exit", change the mouse cursor to a hand
	    // if (helpScreen == true && pos.x >= 325 && pos.x < 380 && pos.y >= 275 && pos.y < 299)
	    //     setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));

	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "About" image of the main screen, then highlight/change the "about" image
	    if (pos.x >= 725 && pos.x < 875 && pos.y >= 350 && pos.y < 410) // && helpScreen == false)
		highlightAbout = true;
	    else
		highlightAbout = false;
	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "Exit" image of the main screen, then highlight/change the "exit" image
	    if (pos.x >= 725 && pos.x < 875 && pos.y >= 430 && pos.y < 490) // && helpScreen == false)
		highlightExit = true;
	    else
		highlightExit = false;

	    if (highlightNewGame || highlightHelp || highlightRank || highlightAbout || highlightExit)
		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR)); // change mouse cursor to a hand
	    else
		setCursor (Cursor.getDefaultCursor ());  // change mouse cursor to its normal image

	    repaint (); //Repaint the screen to show any changes
	}
    }


    // The main is the starting point of the program and constructs the game.
    public static void main (String[] args)
    {
	BoardFrame demo = new BoardFrame ();
	demo.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	demo.pack ();
	demo.setVisible (true);
    } // Main method
} //class
