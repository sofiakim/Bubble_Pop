import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
//http://www.google.ca/imgres?imgurl=http://www.psdgraphics.com/wp-content/uploads/2010/09/red-crystal-ball.jpg&imgrefurl=http://www.psdgraphics.com/graphics/colorful-3d-crystal-balls/&usg=__aEzEP2trrvWea2f8oqKbH_urp0c=&h=458&w=610&sz=37&hl=en&start=5&zoom=1&tbnid=BURMvFyhaCl8cM:&tbnh=102&tbnw=136&ei=SP68T4LZIOeO6gG9rIVD&prev=/search%3Fq%3D3D%2Bball%26hl%3Den%26sa%3DX%26rls%3Dcom.microsoft:en-ca%26tbm%3Disch%26prmd%3Divns&itbs=1

/** The "Project_Methods" class.
  * A brief description of this class
  * @author your name
  * @version date
 */
public class BubblePopBoard extends JPanel implements MouseListener,
    KeyListener
{
    // Program constants (declared at the top, these can be used by any method)
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;
    private final int SEVEN = 7;
    private final int STONE = 8;
    private final int CRACKED = 9;

    private final int EMPTY = 0;

    private final int SQUARE_SIZE = 64;
    private final int DROPPING_SPEED = 10;
    private final int NO_OF_ROWS = 7;
    private final int NO_OF_COLUMNS = 7;
    private final boolean ANIMATION_ON = true;

    for (int i = 0 ; i <= 7 ; i++)
	private final String IMAGE_FILENAME_BALL [i] = "ball" + i + ".png";
    private final String IMAGE_FILENAME_BALL_BROKEN = "broken ball.png";

    public final Dimension BOARD_SIZE =
	new Dimension (NO_OF_COLUMNS * SQUARE_SIZE + 1,
	    (NO_OF_ROWS + 1) * SQUARE_SIZE + 1);

    // Program variables (declared at the top, these can be
    // used or changed by any method)
    private int[] [] board;
    private boolean droppingPiece;
    private int xFallingPiece, yFallingPiece;
    private int currentPlayer;
    private int currentColumn;
    private boolean gameOver;
    private int[] pieces = {1, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, STONE};
    private int level;
    private int levelBubbles;
    private int score;


    /** Constructs a new ConnectFourBoard object
      */
    public BubblePopBoard ()
    {
	// Sets up the board area, loads in piece images and starts a new game
	setPreferredSize (BOARD_SIZE);

	setBackground (new Color (200, 200, 200));
	// Add mouse listeners and Key Listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	requestFocusInWindow ();

	// Load up the images for the pieces
	for (int i = 0 ; i <= 7 ; i++)
	    ballImages [i] = new ImageIcon ("ball" + i + ".png").getImage ();
	crackedBallImage = new ImageIcon (IMAGE_FILENAME_BALL_BROKEN).getImage ();
	score = 0;

	// Sets up the board array and starts a new game

	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];
	gameOver = false;
	level = 1;
	levelBubbles = 30;
	newGame ();
    }


    /** Starts a new game
    */
    public void newGame ()
    {
	int player = (int) (Math.random () * 8 + 1);

	currentPlayer = pieces [player];

	clearBoard ();
	for (int randomPieces = 0 ; randomPieces <= 5 ; randomPieces++)
	{
	    int column = (int) (Math.random () * () * 7 + 1);
	    int row = findRow (column);
	    if (board [row] [column] == 0)
		board [row] [column] = (int) (Math.random () * () * 8 + 1);
	    else
		randomPieces--;
	}
	gameOver = false;
	currentColumn = NO_OF_COLUMNS / 2 + 1;
	droppingPiece = false;
	score = 0;
	repaint ();
    }


    // Your code for the clearBoard, findRow and checkForWinner method goes
    // below. Remember to include proper javadoc comments
    // Since board is an instance variable that is available to all methods
    // it no longer needs to be a parameter

    /* resets the board to Empty
     */
    private void clearBoard ()
    {
	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];
    }


    /* Finds and returns the row to place the piece in
     * @ param column the column of the item goes in
     * @ return the row to place the piece in
     */
    private int findRow (int column)
    {
	int row = board.length - 2;

	while (board [row] [column] != 0 && row > 0)
	{
	    row--;
	}

	if (row == 0)
	    return -1;
	return row;
    }


    private void gameOver ()
    {
	for (int row = 1 ; row < board.length - 1 ; row++)
	{
	    if (board [row].length > 8)
		return true;
	}
	return false;
    }


    /** Makes a move on the board (if possible)
      * @param selectedColumn the selected column to move in
      */
    private void makeMove (int selectedColumn)
    {
	if (gameOver)
	{
	    JOptionPane.showMessageDialog (this,
		    "Please Select Game...New to start a new game",
		    "Game Over", JOptionPane.WARNING_MESSAGE);
	    return;
	}

	int row = findRow (selectedColumn);
	if (row <= 0)
	{
	    JOptionPane.showMessageDialog (this,
		    "Please Select another Column",
		    "Column is Full", JOptionPane.WARNING_MESSAGE);
	    return;
	}
	noOfMove++;

	if (ANIMATION_ON)
	    animatePiece (currentPlayer, selectedColumn, row);

	board [row] [selectedColumn] = currentPlayer;



	if (gameOver)
	{
	    repaint (0);
	    JOptionPane.showMessageDialog (this, "Please select New Game",
		    "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
	}
	else  // Switch to the other player
	    currentPlayer *= -1;

	// Start piece in centre
	currentColumn = NO_OF_COLUMNS / 2 + 1;

	repaint ();
    }


    /** Animates a falling piece
      *@param player the player whoose piece is falling
      *@param column the column the piece is falling in
      *@param finalRow the final row the piece will fall to
      */
    private void animatePiece (int player, int column, int finalRow)
    {
	droppingPiece = true;
	for (double row = 0 ; row < finalRow ; row += 0.20)
	{
	    // Find the x and y positions for the falling piece
	    xFallingPiece = (column - 1) * SQUARE_SIZE;
	    yFallingPiece = (int) (row * SQUARE_SIZE);

	    // Update the drawing area
	    paintImmediately (0, 0, getWidth (), getHeight ());

	    delay (DROPPING_SPEED);

	}
	droppingPiece = false;
    }


    /** Delays the given number of milliseconds
    *@param milliSec The number of milliseconds to delay
    */
    private void delay (int milliSec)
    {
	try
	{
	    Thread.sleep (milliSec);
	}
	catch (InterruptedException e)
	{
	}
    }


    /** Repaint the board's drawing panel
      * @param g The Graphics context
      */
    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	// Redraw the board with current pieces
	for (int row = 1 ; row <= NO_OF_ROWS ; row++)
	    for (int column = 1 ; column <= NO_OF_COLUMNS ; column++)
	    {
		// Find the x and y positions for each row and column
		int xPos = (column - 1) * SQUARE_SIZE;
		int yPos = row * SQUARE_SIZE;

		// Draw the squares
		g.setColor (Color.BLUE);
		g.drawRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);

		// Draw each piece, depending on the value in board
		int piece = board [row] [column];
		if (piece == CRACKED)
		    g.drawImage (crackedBallImage, xPos, yPos, this);
		else
		    g.drawImage (ballImages [piece], xPos, yPos, this);
	    }

	// Draw moving piece if animating
	if (droppingPiece)
	{
	    g.drawImage (ballImages [currentPlayer], xFallingPiece, yFallingPiece, this);
	}

	else   // Draw next player
	{
	    if (!gameOver)
		g.drawImage (ballImages [currentPlayer], (currentColumn - 1) * SQUARE_SIZE, 0, this);
	}
    } // paint component method


    public void bubbleCrack (int lastRow, int lastCol)
    {
	// Checks for stones around the popped bubble and turns them into cracked stones
	if (board [lastRow] [lastCol + 1] == STONE)
	{
	    board [lastRow] [lastCol + 1] = CRACKED;
	    repaint ();
	}
	if (board [lastRow] [lastCol - 1] == STONE)
	{
	    board [lastRow] [lastCol - 1] = CRACKED;
	    repaint ();
	}
	if (board [lastRow + 1] [lastCol] == STONE)
	{
	    board [lastRow + 1] [lastCol] = CRACKED;
	    repaint ();
	}
	if (board [lastRow - 1] [lastCol] == STONE)
	{
	    board [lastRow - 1] [lastCol] = CRACKED;
	    repaint ();
	}

	// Checks for a cracked stone to the right the popped bubble and turns them into a numbered bubble
	if (board [lastRow] [lastCol + 1] == CRACKED)
	{
	    board [lastRow] [lastCol + 1] = pieces [(int) (Math.random () * 7 + 1)];
	    repaint ();
	}
	if (board [lastRow] [lastCol - 1] == CRACKED)
	{
	    board [lastRow] [lastCol - 1] = pieces [(int) (Math.random () * 7 + 1)];
	    repaint ();
	}
	if (board [lastRow + 1] [lastCol] == CRACKED)
	{
	    board [lastRow + 1] [lastCol] = pieces [(int) (Math.random () * 7 + 1)];
	    repaint ();
	}
	if (board [lastRow - 1] [lastCol] == CRACKED)
	{
	    board [lastRow - 1] [lastCol] = pieces [(int) (Math.random () * 7 + 1)];
	    repaint ();
	}
    }


    /** Checks for a game over
      * @return whether the game is over or not
      */
    private boolean checkForGameOver ()
    {
	int noOfPieces = 0;
	for (int row = 7 ; row > 0 ; row--)
	{
	    int col = 1;
	    while (board [row] [col] != EMPTY)
	    {
		noOfPieces++;
		col++;
	    }
	    if (noOfPieces >= 0)
		gameOver = true;

	    noOfPieces = 0;
	}
	return gameOver;
    }


    /** Increases the level
      */
    private void levelUp ()
    {
	if (levelBubbles == 0)
	{
	    for (int col = 1 ; col <= 7 ; col++)
		board [7] [col] = STONE;
	    level++;
	    levelBubbles = 30 - level;
	}
	else
	    levelBubbles--;
    }



    // Keyboard events you can listen for since this JPanel is a KeyListener

    /** Responds to a keyPressed event
    * @param event information about the key pressed event
    */
    public void keyPressed (KeyEvent event)
    {
	// Change the currentRow and currentColumn of the player
	// based on the key pressed
	if (event.getKeyCode () == KeyEvent.VK_LEFT)
	{
	    if (currentColumn > 1)
		currentColumn--;
	}
	else if (event.getKeyCode () == KeyEvent.VK_RIGHT)
	{
	    if (currentColumn < NO_OF_COLUMNS)
		currentColumn++;
	}
	// These keys indicate player's move
	else if (event.getKeyCode () == KeyEvent.VK_DOWN
		|| event.getKeyCode () == KeyEvent.VK_ENTER
		|| event.getKeyCode () == KeyEvent.VK_SPACE)
	{
	    makeMove (currentColumn);
	}

	// Repaint the screen after the change
	repaint ();
    }


    // Extra methods needed since this game board is a KeyListener
    public void keyReleased (KeyEvent event)
    {
    }


    public void keyTyped (KeyEvent event)
    {
    }


    // Mouse events you can listen for since this JPanel is a MouseListener

    /** Responds to a mousePressed event
    *@parameventinformation about the mouse pressed event
    */
    public void mousePressed (MouseEvent event)
    {
	// Calculate which column was clicked, then make
	// the player's move for that column
	int selectedColumn = event.getX () / SQUARE_SIZE + 1;
	makeMove (selectedColumn);
    }


    // Extra methods needed since this game board is a MouseListener

    public void mouseReleased (MouseEvent event)
    {
    }


    public void mouseClicked (MouseEvent event)
    {
    }


    public void mouseEntered (MouseEvent event)
    {
    }


    public void mouseExited (MouseEvent event)
    {
    }
}


