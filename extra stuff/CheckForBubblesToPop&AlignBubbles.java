/* COMMENTS~~~~~~~~~~~~~
   */
private int checkForBubblesToPop (int lastRow, int lastColumn)
{
    // Checks the column for a winning combination
    int inARow = 0;
    int inColumn = 0;
    int checkRow = lastRow;
    int checkColumn = lastColumn;
    int boardLength = board.length - 1;

    //Counts the number of pieces in the column where the last piece was dropped
    while (board [checkRow] [lastColumn] != 0 && checkRow < boardLength)
    {
	checkRow++;
	inColumn++;
    }


    //Counts the number of pieces in the row where the last piece was dropped
    while (board [lastRow] [checkColumn] != 0 && checkRow < boardLength)
    {
	checkColumn++;
	inRow++;
    }


    checkColumn = lastColumn - 1;
    while (board [lastRow] [checkColumn] != 0 && checkRow < boardLength)
    {
	checkColumn++;
	inRow++;
    }


    checkRow = lastRow;
    //Go through column to look for bubbles matching number in column
    while (checkRow < boardLength)
    {
	//"pops bubble" by making the space empty
	if (board [checkRow] [lastColumn] == inColumn)
	{
	    board [checkRow] [lastColumn] = 0;
	    score += 10;
	    bubbleCrack (checkRow, lastColumn);
	}
	checkRow++;
    }


    checkColumn = lastColumn;
    //Go through row to the right to look for bubbles matching number in row
    while (checkColumn < boardLength)
    {
	//"pops bubble" by making the space empty
	if (board [lastRow] [checkColumn] == inRow)
	{
	    board [lastRow] [checkColumn] = 0;
	    score += 10;
	    bubbleCrack (checkRow, lastColumn);
	}
	checkColumn++;
    }
}

void alignBubbles ()
{
    //from top to bottom
    //when it finds an empty space, brings everything on top down

    //Goes through the columns
    for (int checkColumn = 1 ; checkColumn < board.length - 1 ; checkColumn++)
    {
	//Goes up the current column
	for (int checkRow = board.length - 1 ; checkRow > 0 ; checkRow--)
	{
	    //When a space is reached, everything above it is moved down a row
	    if (board [checkRow] [checkColumn] == 0)
	    {
		int checkRow2 = checkRow - 1;
		while (checkRow2 > 0)
		{
		    board [checkRow2 + 1] [checkColumn] == board [checkRow2] [checkColumn];
		    checkRow2--;
		}
	    }
	}
    }
}








