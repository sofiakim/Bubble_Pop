import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "Project_Methods" class.
  * This class contains all the methods that run the game Bubble Pop
  * @author Sofia Kim, Tracy Lei, Jessie Ma
  * @version June 18, 2012
 */
public class BubblePopBoard extends JPanel implements MouseListener,
    KeyListener
{
    // Set constant values to all the bubbles, stones, cracked stones, and empty spaces
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

    // Sets the size of the board to play and other constants during the game including
    // the speed of the bubble/stone dropping
    private final int SQUARE_SIZE = 64;
    private final int DROPPING_SPEED = 20;
    private final int NO_OF_ROWS = 7;
    private final int NO_OF_COLUMNS = 7;
    private final boolean ANIMATION_ON = true;
    public final Dimension BOARD_SIZE =
	new Dimension (NO_OF_COLUMNS * SQUARE_SIZE + 210,
	    (NO_OF_ROWS + 1) * SQUARE_SIZE + 1);

    //private final String IMAGE_FILENAME_BALL_BROKEN = "broken ball.png";

    // Program variables
    // Creates variables that keep track of various game statuses such as the
    // level the player is on, the position, whether the game is over, the score
    private int[] [] board;
    private boolean droppingPiece;
    private int xFallingPiece, yFallingPiece;
    private int currentColumn;
    private boolean gameOver;
    private int[] pieces = {1, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, STONE};
    private int level;
    private int levelBubbles;
    private int score;
    private int chain;
    private int longestChain;
    private int randomPiece;
    private int gameStatus;
    private Point testPos;
    private long startTime;

    // Keeps track of the images of all the bubbles, including popping bubbles
    private Image[] ballImages;
    private Image[] poppingBallImages;
    private Image firstBallImage, secondBallImage, thirdBallImage, fourthBallImage,
	fifthBallImage, sixthBallImage, seventhBallImage, crackedBallImage, stoneBallImage;

    //Keeps track of the number of bubbles popped
    private int noOfBubblesPopped;

    // Calls the audio class so that game can play sounds, and keeps track of
    // variables for the audio
    private BubblePopAudio audio;
    // Calls the BubblePopRank class so that the ranks can be read from the
    // file
    private BubblePopRank BubblePopRank;
    private Image soundOffImage, soundHighlightedImage;
    private boolean soundOn;

    // Keeps track of images for the menu screen, game background, as well as
    // the buttons within the game, and what the buttons look like when highlighted
    private Image imageBackground, sideImage, imageNewGame, imageNewGameBlack, imageHelp, imageHelpBlack,
	imageRank, imageRankBlack, imageAbout, imageAboutBlack, imageExit, imageExitBlack, introImageBackground,
	imageRightButton, imageLeftButton, imageRightButtonClicked, imageLeftButtonClicked, imageHelpExit,
	imageHelpExitBlack, aboutInstruction;
    private boolean highlightNewGame, highlightHelp, highlightRank, highlightAbout, highlightExit,
	highlightSound, highlightRightButton, highlightLeftButton, highlightHelpExit;
    private boolean levelBonus, clearBonus;
    private Image levelImage, clearImage, gameOverImage;


    // Keeps track of the images and booleans of the help, about, and rank screens
    private Image[] helpInstructionImages;
    private Image helpInstructionOne, helpInstructionTwo, helpInstructionThree, helpInstructionFour,
	helpInstructionFive, helpInstructionSix, helpInstructionSeven;
    private boolean helpScreen;
    private int currentPage;
    private boolean showRank;
    private boolean showAbout;


    // Keeps track of the position of the bubble popped
    private boolean bubblePopped;
    private int theBubblePopped;
    private int xPopped;
    private int yPopped;


    // Arrays that keep track of the name and score of the highest scores
    private int[] highestScores = new int [5];
    private String[] highestNames = new String [5];

    /** Constructs a new BubblePopBoard object
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
	this.addMouseMotionListener (new MouseMotionHandler ());

	// Load up the images for the pieces (bubbles, stones) & the help and about screens
	ballImages = new Image [10];
	for (int i = 1 ; i < ballImages.length ; i++)
	{
	    ballImages [i] = new ImageIcon ("ball" + i + ".png").getImage ();
	}
	poppingBallImages = new Image [8];
	for (int i = 1 ; i < poppingBallImages.length ; i++)
	{
	    poppingBallImages [i] = new ImageIcon ("PoppingBall" + i + ".gif").getImage ();

	}
	helpInstructionImages = new Image [7];
	for (int i = 1 ; i < helpInstructionImages.length ; i++)
	{
	    helpInstructionImages [i] = new ImageIcon ("project images 2/Help" + i + ".png").getImage ();
	}

	// Sets up the board array and starts a new game
	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];
	gameOver = false;
	gameStatus = 1;
	newGame ();

	// Refer to the audio class
	audio = new BubblePopAudio ();
	// Refer to the BubblePopRank class
	BubblePopRank = new BubblePopRank ();


	// Load images
	// Background images were made 550x350 pixels (to fit 11 columns by 7 rows, with each square being 50 pixels wide & high)
	introImageBackground = new ImageIcon ("project images 2/BubblePopStartMenu.png").getImage ();
	sideImage = new ImageIcon ("project images 2/sidebar.png").getImage ();
	imageBackground = new ImageIcon ("project images 2/circlebackground.png").getImage ();

	// Images loaded for the buttons within the game
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

	soundHighlightedImage = new ImageIcon ("soundHighlightedImage.png").getImage ();
	soundOffImage = new ImageIcon ("soundOffImage.png").getImage ();
	soundOn = true;

	clearImage = new ImageIcon ("clearBonus.png").getImage ();
	levelImage = new ImageIcon ("levelUp.png").getImage ();
	gameOverImage = new ImageIcon ("game over.png").getImage ();


	// Set variables to zero or false
	bubblePopped = false;
	theBubblePopped = 0;
	xPopped = 0;
	yPopped = 0;

	helpScreen = false;
	showRank = false;
	showAbout = false;

	// Load up images for the buttons inside the help and about screens
	aboutInstruction = new ImageIcon ("project images 2/aboutInstruction.png").getImage ();
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
	imageHelpExit = new ImageIcon ("project images 2/exitImage.png").getImage ();
	imageHelpExitBlack = new ImageIcon ("project images 2/exitImageClicked.png").getImage ();
	imageRightButton = new ImageIcon ("project images 2/rightButton.png").getImage ();
	imageRightButtonClicked = new ImageIcon ("project images 2/rightButtonClicked.png").getImage ();
	imageLeftButton = new ImageIcon ("project images 2/leftButton.png").getImage ();
	imageLeftButtonClicked = new ImageIcon ("project images 2/leftButtonClicked.png").getImage ();
    }


    /** Starts a new game
      */
    public void newGame ()
    {
	// Creates a random piece to be dropped down at the beginning of the game
	randomPiece = pieces [(int) (Math.random () * 8 + 1)];

	// Clears the board and creates a new board with four to six randomly-
	// placed pieces within it
	clearBoard ();
	for (int randomPieces = 0 ; randomPieces <= 5 ; randomPieces++)
	{
	    int column = (int) (Math.random () * 7 + 1);
	    int row = findRow (column);
	    if (board [row] [column] == 0)
	    {
		board [row] [column] = (int) (Math.random () * 8 + 1);
		checkForBubblesToPop ();
		if (bubblePopped)
		{
		    randomPieces--;
		    bubblePopped = false;
		}
	    }
	    else
		randomPieces--;
	}

	// Sets the variables to their default settings
	gameOver = false;
	currentColumn = NO_OF_COLUMNS / 2 + 1;
	droppingPiece = false;
	level = 1;
	levelBubbles = 29;
	levelBonus = false;
	clearBonus = false;
	score = 0;
	chain = 0;
	longestChain = 0;
	if (soundOn)
	    audio.backgroundMusic ();
	repaint ();
    }


    /* Resets the board to Empty
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
	while (board [row] [column] != 0)
	{
	    row--;
	}
	if (row <= 0)
	    return -1;
	return row;
    }


    /** Makes a move on the board (if possible)
      * @param colToMove the selected column to move in
      */
    private void makeMove (int colToMove)
    {
	// Does not make a move if the game is over
	if (gameOver)
	{
	    paintImmediately (0, 0, getWidth (), getHeight ());
	}
	else  // Create a new piece (bubble or stone) to be dropped
	{
	    // Finds the row to drop the piece
	    int row = findRow (colToMove);
	    if (row <= 0)
	    {
		JOptionPane.showMessageDialog (this,
			"Please Select another Column pleasee",
			"Column is Full", JOptionPane.WARNING_MESSAGE);
		return;
	    }
	    // Shows the piece dropping
	    if (ANIMATION_ON)
		animatePiece (randomPiece, colToMove, row);
	    board [row] [colToMove] = randomPiece;
	    repaint ();

	    // Checks for bubbles to pop after the move is completed
	    chain = 0;
	    do
	    {
		checkForBubblesToPop ();
		chain++;
		if (chain > longestChain)
		    longestChain = chain;
	    }
	    while (noOfBubblesPopped != 0);

	    // Checks for a game over (because if the last piece dropped is
	    // still able to be popped, then the game continues)
	    if (checkForGameOver ())
		paintImmediately (0, 0, getWidth (), getHeight ());
	    else
		levelUp ();

	    // Generates a new random piece to be dropped next move
	    randomPiece = pieces [(int) (Math.random () * 8 + 1)];

	}
	// Start piece in centre
	currentColumn = NO_OF_COLUMNS / 2 + 1;
	repaint ();
    }


    /** Animates a falling piece
      *@param piece the piece that is falling
      *@param column the column the piece is falling in
      *@param finalRow the final row the piece will fall to
      */
    private void animatePiece (int piece, int column, int finalRow)
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

	// Draws the main menu screen of the game with the Bubble Pop logo
	if (gameStatus == 1)
	{
	    g.drawImage (introImageBackground, 0, 0, this);
	    g.drawImage (sideImage, 448, 0, this);
	}

	// Draws the actual game board & game
	else
	{
	    g.drawImage (imageBackground, 0, 0, this);
	    g.drawImage (sideImage, 448, 0, this);

	    for (int row = 1 ; row <= NO_OF_ROWS ; row++)
		for (int column = 1 ; column <= NO_OF_COLUMNS ; column++)
		{
		    // Find the x and y positions for each row and column
		    int xPos = (column - 1) * SQUARE_SIZE;
		    int yPos = row * SQUARE_SIZE;

		    // Draw each piece, depending on the value in board
		    int piece = board [row] [column];
		    if (piece == CRACKED)
			g.drawImage (ballImages [9], xPos, yPos, this);
		    else if (board [row] [column] != EMPTY)
		    {
			g.drawImage (ballImages [piece], xPos, yPos, this);

		    }
		}

	    // Shows the gif image of the bubble that are popped
	    if (bubblePopped)
	    {
		g.drawImage (poppingBallImages [theBubblePopped], xPopped, yPopped, this);
		if (System.currentTimeMillis () - startTime > 450) //instead of 1000 put the time it takes to disappear
		{
		    bubblePopped = false;
		}
		repaint ();
	    }

	    // Draw moving piece if animating
	    if (droppingPiece)
	    {
		g.drawImage (ballImages [randomPiece], xFallingPiece, yFallingPiece, this);
	    }


	    else   // Draw next piece
	    {
		if (!gameOver)
		    g.drawImage (ballImages [randomPiece], (currentColumn - 1) * SQUARE_SIZE, 0, this);
	    }

	    // Shows whether the sound has been turned on or off
	    if (soundOn == false)
		g.drawImage (soundOffImage, 448, 0, this);
	    if (highlightSound)
		g.drawImage (soundHighlightedImage, 448, 0, this);

	    // Displays the score and the level of the current game being played
	    setFont (new Font ("Broadway", Font.BOLD, 36));
	    g.setColor (Color.BLACK);
	    g.drawString (String.valueOf (score), 480, 50);
	    if (level < 10)
		g.drawString ("level 0" + String.valueOf (level), 475, 100);
	    else
		g.drawString ("level " + String.valueOf (level), 475, 100);

	    // Shows a level bonus and clear bonus when they occur
	    if (levelBonus && !gameOver)
	    {
		g.drawImage (levelImage, 0, 0, this);
		levelBonus = false;
	    }
	    if (clearBonus)
	    {
		g.drawImage (clearImage, 0, 0, this);
		clearBonus = false;
	    }

	}

	// Draw help image in the sidebar. If the mouse is over the help image, "highlight" it by drawing a BLACK "Help" image.
	// The highlightHelp variable is set to true in the mouseMoved method when the mouse is over the "Help" image.
	if (highlightNewGame)
	    g.drawImage (imageNewGameBlack, 480, 120, 150, 40, this);
	else
	    g.drawImage (imageNewGame, 480, 120, 150, 40, this);

	if (highlightHelp)
	    g.drawImage (imageHelpBlack, 480, 175, 150, 40, this);
	else
	    g.drawImage (imageHelp, 480, 175, 150, 40, this);

	if (highlightRank)
	    g.drawImage (imageRankBlack, 480, 230, 150, 40, this);
	else
	    g.drawImage (imageRank, 480, 230, 150, 40, this);

	if (highlightAbout)
	    g.drawImage (imageAboutBlack, 480, 285, 150, 40, this);
	else
	    g.drawImage (imageAbout, 480, 285, 150, 40, this);

	if (highlightExit)
	    g.drawImage (imageExitBlack, 480, 340, 150, 40, this);
	else
	    g.drawImage (imageExit, 480, 340, 150, 40, this);

	// Shows a game over image if the game is over
	if (gameOver)
	{
	    BubblePopRank.saveScore (score);
	    g.drawImage (gameOverImage, 0, 0, this);
	}

	// Displays the help screen pages if the help button was pressed
	if (helpScreen)
	{
	    g.drawImage (helpInstructionImages [currentPage], 63, 60, 530, 380, this);
	    if (highlightRightButton)
		g.drawImage (imageRightButtonClicked, 490, 80, 30, 30, this);
	    else
		g.drawImage (imageRightButton, 490, 80, 30, 30, this);

	    if (highlightLeftButton)
		g.drawImage (imageLeftButtonClicked, 330, 80, 30, 30, this);
	    else
		g.drawImage (imageLeftButton, 330, 80, 30, 30, this);

	    if (highlightHelpExit)
		g.drawImage (imageHelpExitBlack, 400, 80, 50, 30, this);
	    else
		g.drawImage (imageHelpExit, 400, 80, 50, 30, this);
	}

	// Displays the highest scores of the game so far if "rank" was pressed
	if (showRank)
	{
	    setFont (new Font ("Broadway", Font.BOLD, 20));
	    g.drawString ("High Scores", 110, 100);
	    int[] rank = new int [5];
	    rank = BubblePopRank.showRankings ();
	    int y = 200;
	    for (int person = 0 ; person < 5 ; person++)
	    {
		String string = (person + 1) + "." + rank [person];
		//System.out.println (string);
		g.drawString (string, 10, y);
		y += 30;
	    }
	    showRank = false;

	}

	// Displays the "about" if "about" was pressed
	if (showAbout)
	{
	    g.drawImage (aboutInstruction, 63, 60, 530, 380, this);
	    if (highlightHelpExit)
		g.drawImage (imageHelpExitBlack, 400, 80, 50, 30, this);
	    else
		g.drawImage (imageHelpExit, 400, 80, 50, 30, this);
	}

    } // paint component method


    /* Checks the entire game board for bubbles to pop (bubbles whose value
     * matches either the number of bubbles in its column or the number of
     * bubbles in its row
     */
    private void checkForBubblesToPop ()
    {
	//Keeps track of the number of bubbles popped in this run of the method
	noOfBubblesPopped = 0;
	//Keeps track of the number of pieces in each column
	//Dummy variable used
	int[] columnHeights = new int [8];

	//Goes through each column and counts the number of pieces
	for (int col = 1 ; col <= NO_OF_COLUMNS ; col++)
	{
	    int inColumn = 0;
	    int checkRow = NO_OF_ROWS;
	    while (board [checkRow] [col] != EMPTY && checkRow > 0)
	    {
		checkRow--;
		inColumn++;
	    }
	    columnHeights [col] = inColumn;
	}

	//Goes through each column and each row
	for (int row = NO_OF_ROWS ; row > 0 ; row--)
	{
	    int checkColumn = 1;
	    while (checkColumn <= NO_OF_COLUMNS)
	    {
		int inARow = 0;
		//finds next starting piece (starting as in it is beside a
		//space) in that row
		while (board [row] [checkColumn] == EMPTY && checkColumn <= NO_OF_COLUMNS)
		    checkColumn++;
		int col = checkColumn;

		//Counts the number of pieces in a row (with no empty spaces in
		//between) from the starting piece
		while (board [row] [checkColumn] != EMPTY && checkColumn <= NO_OF_COLUMNS)
		{
		    inARow++;
		    checkColumn++;
		}

		//Going back to the starting piece and looking at all the
		//pieces in a row with it, all pieces with the same value
		//as the number of pieces in a row is popped
		for (; col < checkColumn ; col++)
		{
		    if (board [row] [col] == inARow)
		    {
			//xPopped and yPopped keeps track of the position where the
			//bubble popped for paintComponent
			xPopped = (col - 1) * SQUARE_SIZE;
			yPopped = row * SQUARE_SIZE;
			bubblePopped = true;
			if (soundOn)
			    audio.bubblePopping ();
			startTime = System.currentTimeMillis ();

			theBubblePopped = inARow;
			//The bubble is "popped" by making the space empty
			board [row] [col] = EMPTY;
			noOfBubblesPopped++;
			if (chain == 0)
			    score += 10;
			else
			    score += chain * 50;
			crackStone (row, col);
			paintImmediately (0, 0, getWidth (), getHeight ());

		    }

		}
	    }
	}

	//Going through all the remaining bubbles on the board, all bubbles
	//with the same value as the number of bubbles in the column is popped
	for (int row = NO_OF_ROWS ; row > 0 ; row--)
	{
	    for (int col = 1 ; col <= NO_OF_COLUMNS ; col++)
	    {
		if (board [row] [col] == columnHeights [col] && columnHeights [col] != 0)
		{
		    //xPopped and yPopped keeps track of the position where the
		    //bubble popped for paintComponent
		    xPopped = (col - 1) * SQUARE_SIZE;
		    yPopped = row * SQUARE_SIZE;
		    bubblePopped = true;
		    if (soundOn)
			audio.bubblePopping ();
		    startTime = System.currentTimeMillis ();

		    theBubblePopped = columnHeights [col];
		    board [row] [col] = EMPTY;
		    if (chain == 0)
			score += 10;
		    else
			score += chain * 50;
		    crackStone (row, col);
		    noOfBubblesPopped++;
		    paintImmediately (0, 0, getWidth (), getHeight ());

		}
	    }
	}
	//Goes through all the columns and aligns them (pulling down all
	//"floating" bubbles)
	for (int col = 1 ; col <= NO_OF_COLUMNS ; col++)
	    alignColumn (col);
	repaint ();
    }


    /** This section of code makes a stone crack and causes it to turn into a bubble
      * @param row the row of the last popped bubble
      * @param col the column of the last popped bubble
      */
    public void crackStone (int row, int col)
    {
	// Checks for a cracked stone to the right the popped bubble and turns them into a numbered bubble
	// Delays to ensure the player is able to view the value of the bubble that is created
	// (in case it pops immediately after being created)
	if (board [row] [col + 1] == CRACKED)
	{
	    board [row] [col + 1] = pieces [(int) (Math.random () * 7 + 1)];
	    paintImmediately (0, 0, getWidth (), getHeight ());
	    delay (800);
	}
	if (board [row] [col - 1] == CRACKED)
	{
	    board [row] [col - 1] = pieces [(int) (Math.random () * 7 + 1)];
	    paintImmediately (0, 0, getWidth (), getHeight ());
	    delay (800);
	}
	if (board [row + 1] [col] == CRACKED)
	{
	    board [row + 1] [col] = pieces [(int) (Math.random () * 7 + 1)];
	    paintImmediately (0, 0, getWidth (), getHeight ());
	    delay (800);
	}
	if (board [row - 1] [col] == CRACKED)
	{
	    board [row - 1] [col] = pieces [(int) (Math.random () * 7 + 1)];
	    paintImmediately (0, 0, getWidth (), getHeight ());
	    delay (800);
	}

	// Checks for stones around the popped bubble and turns them into cracked stones
	if (board [row] [col + 1] == STONE)
	    board [row] [col + 1] = CRACKED;
	if (board [row] [col - 1] == STONE)
	    board [row] [col - 1] = CRACKED;
	if (board [row + 1] [col] == STONE)
	    board [row + 1] [col] = CRACKED;
	if (board [row - 1] [col] == STONE)
	    board [row - 1] [col] = CRACKED;

	repaint ();
    }


    /** Checks for a game over and a clear bonus
      * @return whether the game is over (true) or not (false)
      */
    private boolean checkForGameOver ()
    {
	int noOfPieces = 0;
	// Counts the number of pieces on the board
	for (int col = 1 ; col <= 7 ; col++)
	{
	    for (int row = 7 ; row > 0 ; row--)
	    {
		if (board [row] [col] != EMPTY)
		    noOfPieces++;
	    }
	}

	// Game over if all the spaces are filled
	if (noOfPieces >= 49)
	{
	    gameOver = true;
	    if (soundOn)
	    {
		audio.stopSound ();
		audio.gameOverSound ();
	    }
	    return true;
	}

	// Gives a "clear bonus" if there are no pieces on the board
	if (noOfPieces == 0)
	{
	    if (soundOn)
		audio.tada ();
	    clearBonus = true;
	    paintImmediately (0, 0, getWidth (), getHeight ());
	    delay (2000);
	    score += 30000;
	}
	return false;
    }


    /** Goes through the given column and pulls all bubbles that are floating
      * down to the nearest available row
      * @param col the column to align
      */
    private void alignColumn (int col)
    {
	//Goes up the given column
	for (int checkRow = NO_OF_ROWS ; checkRow > 0 ; checkRow--)
	{
	    //When a space is reached, everything above it is moved down a row
	    if (board [checkRow] [col] == EMPTY)
	    {
		int checkRow2 = checkRow - 1;
		while (checkRow2 >= 0)
		{
		    board [checkRow2 + 1] [col] = board [checkRow2] [col];
		    checkRow2--;
		}
	    }
	}
    }


    /** Increases the level and creates a row of stones at the bottom,
      * bumping up all the current pieces in play
      */
    private void levelUp ()
    {
	if (levelBubbles == 0)
	{
	    // Moves all the pieces on the board one row up
	    for (int col = 1 ; col <= 7 ; col++)
	    {
		for (int row = 1 ; row <= 7 ; row++)
		{
		    board [row - 1] [col] = board [row] [col];
		}
		// Makes the lowest row on the board be made up of stones
		board [7] [col] = STONE;
		repaint ();
	    }

	    // Checks if the game ended due to the new level
	    for (int col = 1 ; col <= 7 ; col++)
		if (board [0] [col] != EMPTY)
		{
		    gameOver = true;
		    paintImmediately (0, 0, getWidth (), getHeight ());
		    if (soundOn)
		    {
			audio.stopSound ();
			audio.gameOverSound ();
		    }
		    return;
		}
	    // Increases the level and plays the new audio
	    if (!gameOver)
	    {
		level++;
		if (soundOn)
		    audio.levelingUp ();

		// Checks if any bubbles popped due to the new level
		chain = 0;
		do
		{
		    checkForBubblesToPop ();
		    chain++;
		}
		while (noOfBubblesPopped != 0);

		// Gives a level bonus for reaching a new level
		levelBonus = true;
		paintImmediately (0, 0, getWidth (), getHeight ());
		delay (2000);
		score += 5000;

		// Gives the player one less move to play for each level increase
		// If the player is at level 25 or more, he/she is given five moves
		// regardless of the level he/she is on
		if (level < 25)
		    levelBubbles = 30 - level;
		else
		    levelBubbles = 5;
	    }
	}

	else // continues counting down until it is time for a levelUp
	    levelBubbles--;
    }


    /** Monitors mouse movement over the game panel and responds
    */
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	/** Responds to mouse-movement inputs
	  * @param event the event created by the mouse movement
	*/
	public void mouseMoved (MouseEvent event)
	{
	    Point pos = event.getPoint ();
	    testPos = pos;
	    // If mouse is over the "New game" image of the main screen, then highlight/change the "new game" image
	    if (pos.x >= 480 && pos.x < 630 && pos.y >= 120 && pos.y < 160)
	    {
		highlightNewGame = true;
	    }
	    else
	    {
		highlightNewGame = false;
	    }

	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "Help" image of the main screen, then highlight/change the "help" image
	    if (pos.x >= 480 && pos.x < 630 && pos.y >= 175 && pos.y < 215)
	    {
		highlightHelp = true;
	    }
	    else
	    {
		highlightHelp = false;
	    }

	    repaint (); //Repaint the screen to show any changes


	    // If mouse is over the "Rank" image of the main screen, then highlight/change the "rank" image
	    if (pos.x >= 480 && pos.x < 630 && pos.y >= 230 && pos.y < 270)
		highlightRank = true;
	    else
		highlightRank = false;

	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "About" image of the main screen, then highlight/change the "about" image
	    if (pos.x >= 480 && pos.x < 630 && pos.y >= 285 && pos.y < 325)
		highlightAbout = true;
	    else
		highlightAbout = false;
	    repaint (); //Repaint the screen to show any changes

	    // If mouse is over the "Exit" image of the main screen, then highlight/change the "exit" image
	    if (pos.x >= 480 && pos.x < 630 && pos.y >= 340 && pos.y < 370)
		highlightExit = true;
	    else
		highlightExit = false;
	    if (pos.x >= 485 && pos.x < 520 && pos.y >= 450 && pos.y < 490)
		highlightSound = true;
	    else
		highlightSound = false;

	    // Highlights buttons on the help screen
	    // If the mouse is over the right button image of the help screen, then "right button" image changes
	    if (pos.x >= 490 && pos.x < 520 && pos.y >= 80 && pos.y < 110 && helpScreen == true && showAbout == false && showRank == false)
		highlightRightButton = true;
	    else
		highlightRightButton = false;

	    // If the mouse is over the left button image of the help screen, then "left button" image changes
	    if (pos.x >= 330 && pos.x < 360 && pos.y >= 80 && pos.y < 110 && helpScreen == true && showAbout == false && showRank == false)
		highlightLeftButton = true;
	    else
		highlightLeftButton = false;

	    // If the mouse is over the exit image of the help screen, then "exit" image changes
	    if (pos.x >= 400 && pos.x < 450 && pos.y >= 80 && pos.y < 110 && (helpScreen == true || showAbout == true) && showRank == false)
		highlightHelpExit = true;
	    else
		highlightHelpExit = false;

	    // Makes the cursor into a hand
	    if (highlightNewGame || highlightHelp || highlightRank || highlightAbout || highlightExit || highlightSound ||
		    highlightRightButton || highlightLeftButton || highlightHelpExit)
		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR)); // change mouse cursor to a hand
	    else
		setCursor (Cursor.getDefaultCursor ());  // change mouse cursor to its normal image

	    repaint (); //Repaint the screen to show any changes
	}
    }


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
      * @param eventinformation about the mouse pressed event
      */
    public void mousePressed (MouseEvent event)
    {
	if (helpScreen == false && showAbout == false)
	{
	    // Calculate which column was clicked, then make
	    // the player's move for that column
	    int selectedColumn = event.getX () / SQUARE_SIZE + 1;
	    if (selectedColumn <= NO_OF_COLUMNS)
		makeMove (selectedColumn);
	    else
	    {
		Point pressed = event.getPoint ();
		//Check if mouse was pressed over the Exit image
		if (pressed.x >= 480 && pressed.x < 630 && pressed.y >= 340 && pressed.y < 370)
		{
		    System.exit (0);
		}

		// Check if mouse was pressed over the NewGame image & creates a new game
		if (pressed.x >= 480 && pressed.x < 630 && pressed.y >= 120 && pressed.y < 160)
		{
		    if (gameStatus == 1)
		    {
			newGame ();
			gameStatus = 2;
			repaint ();
			return;
		    }
		    if (!gameOver)
		    {
			gameOver = true;
			paintImmediately (0, 0, getWidth (), getHeight ());
			delay (4000);
		    }
		    newGame ();

		}

		// Check if mouse was pressed over the Rank image
		if (pressed.x >= 480 && pressed.x < 630 && pressed.y >= 230 && pressed.y < 270)
		{
		    showRank = true;
		    paintImmediately (0, 0, getWidth (), getHeight ());
		    delay (5000);
		}

		//Check if mouse was pressed over the About image
		if (pressed.x >= 480 && pressed.x < 630 && pressed.y >= 285 && pressed.y < 325)
		{
		    showAbout = true;
		}

		// Check if mouse was pressed over the Help image
		if (pressed.x >= 480 && pressed.x < 630 && pressed.y >= 175 && pressed.y < 215)
		{
		    helpScreen = true;
		    currentPage = 1;
		}

		// Check if mouse was pressed over the audio image
		if (pressed.x >= 485 && pressed.x < 520 && pressed.y >= 450 && pressed.y < 490)
		{
		    if (soundOn)
		    {
			soundOn = false;
			audio.stopSound ();
		    }
		    else
		    {
			soundOn = true;
			if (soundOn)
			    audio.backgroundMusic ();
		    }
		}
	    }
	}
	// Checks for buttons pressed in the help screen
	else
	{
	    Point pressed = event.getPoint ();

	    // If mouse was pressed over the right arrow
	    if (pressed.x >= 490 && pressed.x < 520 && pressed.y >= 80 && pressed.y < 110 && helpScreen == true && showAbout == false && showRank == false)
	    {
		if (currentPage >= 1 && currentPage < (helpInstructionImages.length - 1))
		{
		    currentPage++;
		    repaint ();
		}
	    }

	    // If mouse was pressed over the left arrow
	    if (pressed.x >= 330 && pressed.x < 360 && pressed.y >= 80 && pressed.y < 110 && helpScreen == true && showAbout == false && showRank == false)
	    {
		if (currentPage > 1 && currentPage <= (helpInstructionImages.length - 1))
		{
		    currentPage--;
		    repaint ();
		}
	    }

	    // If mouse pressed over the exit sign
	    if (pressed.x >= 400 && pressed.x < 450 && pressed.y >= 80 && pressed.y < 110 && helpScreen == true && showAbout == false && showRank == false)
		helpScreen = false;

	    if (pressed.x >= 400 && pressed.x < 450 && pressed.y >= 80 && pressed.y < 110 && (helpScreen == true || showAbout == true) && showRank == false)
		showAbout = false;
	}


	repaint ();
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


