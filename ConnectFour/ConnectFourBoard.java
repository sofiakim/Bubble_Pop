import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "ConnectFourBoard" class.
  * Handles the board play for a simple game of Connect 4
  * @author ICS3U
  * @version November 2011
  */
public class ConnectFourBoard extends JPanel implements MouseListener,
    KeyListener
{
    // Program constants (declared at the top, these can be used by any method)
    private final int PENGUIN = -1;
    private final int TURTLE = 1;
    private final int EMPTY = 0;

    private final int SQUARE_SIZE = 64;
    private final int DROPPING_SPEED = 10;
    private final int NO_OF_ROWS = 6;
    private final int NO_OF_COLUMNS = 7;
    private final boolean ANIMATION_ON = true;

    // Artwork by iconshock.com www.iconshock.com
    private final String IMAGE_FILENAME_PLAYER1 = "penguin.png";
    private final String IMAGE_FILENAME_PLAYER2 = "turtle.png";
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
    private Image firstImage, secondImage;
    private boolean gameOver;
    
    private int noOfMove = 0;

    /** Constructs a new ConnectFourBoard object
      */
    public ConnectFourBoard ()
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
	firstImage = new ImageIcon (IMAGE_FILENAME_PLAYER1).getImage ();
	secondImage = new ImageIcon (IMAGE_FILENAME_PLAYER2).getImage ();

	// Sets up the board array and starts a new game
	
	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];
	newGame ();
    }


    /** Starts a new game
    */
    public void newGame ()
    {
	currentPlayer = TURTLE;
	noOfMove = 0;
	clearBoard ();
	gameOver = false;
	currentColumn = NO_OF_COLUMNS / 2 + 1;
	droppingPiece = false;
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


    /* Checks to see if there is a winning combination on the board
     */
    private int checkForWinner (int lastRow, int lastColumn)
    {
	// updown
	int noOfPiece = 0;
	int instRow = lastRow;
	int instColumn = lastColumn;
	int item = board [lastRow] [lastColumn];

	while (item == board [instRow] [lastColumn] && instRow < board.length - 1)
	{
	    instRow++;
	    noOfPiece++;
	}
	if (noOfPiece >= 4)
	    return item;

	//rightLeft
	noOfPiece = 0;
	instRow = lastRow;
	instColumn = lastColumn;

	while (item == board [lastRow] [instColumn] && instColumn < board [lastRow].length - 1)
	{
	    instColumn++;
	    noOfPiece++;
	}
	instRow = lastRow;
	instColumn = lastColumn;
	while (item == board [lastRow] [instColumn - 1] && instColumn > 0)
	{
	    instColumn--;
	    noOfPiece++;
	}
	if (noOfPiece >= 4)
	    return item;

	//right diagonal
	noOfPiece = 0;
	instRow = lastRow;
	instColumn = lastColumn;

	while (item == board [instRow] [instColumn] && instRow > 0 && instColumn < board [lastRow].length - 1)
	{
	    instRow--;
	    instColumn++;
	    noOfPiece++;
	}
	instRow = lastRow;
	instColumn = lastColumn;
	while (item == board [instRow + 1] [instColumn - 1] && instRow < board.length - 1 && instColumn > 0)
	{
	    instRow++;
	    instColumn--;
	    noOfPiece++;
	}
	if (noOfPiece >= 4)
	    return item;

	//left diagonal
	noOfPiece = 0;
	instRow = lastRow;
	instColumn = lastColumn;
	while (item == board [instRow] [instColumn] && instRow > 0 && instColumn > 0)
	{
	    instRow--;
	    instColumn--;
	    noOfPiece++;
	}
	instRow = lastRow;
	instColumn = lastColumn;
	while (item == board [instRow + 1] [instColumn + 1] && instRow < board.length - 1 && instColumn < board [lastRow].length - 1)
	{
	    instRow++;
	    instColumn++;
	    noOfPiece++;
	}
	if (noOfPiece >= 4)
	    return item;

	return EMPTY;
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
	noOfMove ++;

	if (ANIMATION_ON)
	    animatePiece (currentPlayer, selectedColumn, row);
	    
	board [row] [selectedColumn] = currentPlayer;

	int winner = checkForWinner (row, selectedColumn);

	if (winner == PENGUIN)
	{
	    gameOver = true;
	    repaint (0);
	    JOptionPane.showMessageDialog (this, "Penguin Wins!!!",
		    "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
	}
	else if (winner == TURTLE)
	{
	    gameOver = true;
	    repaint (0);
	    JOptionPane.showMessageDialog (this, "Turtle Wins!!!",
		    "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
	}
	else if (noOfMove == 42)
	{
	    gameOver = true;
	    repaint (0);
	    JOptionPane.showMessageDialog (this, "Tie Game!!!",
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
		if (board [row] [column] == PENGUIN)
		    g.drawImage (firstImage, xPos, yPos, this);
		else if (board [row] [column] == TURTLE)
		    g.drawImage (secondImage, xPos, yPos, this);
	    }

	// Draw moving piece if animating
	if (droppingPiece)
	{
	    if (currentPlayer == PENGUIN)
		g.drawImage (firstImage, xFallingPiece, yFallingPiece, this);
	    else
		g.drawImage (secondImage, xFallingPiece, yFallingPiece, this);
	}
	else   // Draw next player
	{
	    if (!gameOver)
		if (currentPlayer == PENGUIN)
		    g.drawImage (firstImage, (currentColumn - 1) * SQUARE_SIZE, 0, this);
		else
		    g.drawImage (secondImage, (currentColumn - 1) * SQUARE_SIZE, 0, this);
	}
    } // paint component method


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

