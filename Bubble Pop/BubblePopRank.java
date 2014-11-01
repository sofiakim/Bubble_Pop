import hsa.TextInputFile;
import hsa.TextOutputFile;
import java.awt.*;
import javax.swing.*;
import hsa.*;

public class BubblePopRank extends JFrame
{
    public BubblePopRank ()
    {
    }


    /** Retrieves the top 5 scores saved in ranks.txt
      * @return an array of the top 5 rankings from highest to lowest
      */
    public int[] showRankings ()
    {
	int[] rank = new int [5];
	TextInputFile fileIn = new TextInputFile ("ranks.txt");
	for (int person = 0 ; person < 5 ; person++)
	    rank [person] = fileIn.readInt ();
	//Closes file
	fileIn.close ();
	return rank;
    }


    /** Saves a given score into the file ranks.txt if it is one of the top 5
      * scores ever achieved in the game
      * @param score the given score
      */
    public void saveScore (int score)
    {
	//Places the current top 5 scores from the file ranks.txt into the
	//array rank
	int[] rank = new int [5];
	TextInputFile fileIn = new TextInputFile ("ranks.txt");
	for (int person = 0 ; person < 5 ; person++)
	    rank [person] = fileIn.readInt ();
	//Closes file
	fileIn.close ();

	//The given score is placed into an appropriate spot within the top 5
	//or not at all if it is lower than the top 5 scores
	highestScore (score, rank);
	TextOutputFile ranks = new TextOutputFile ("ranks.txt");
	for (int person = 0 ; person < 5 ; person++)
	    ranks.println (rank [person]);
	//Closes file
	ranks.close ();
    }


    /** Sorts through the highest scores and decides if the player ranks in the top five
     * @param score the given score
     * @param highestScores the list of top 5 scores
       */
    private void highestScore (int score, int[] highestScores)
    {
	// Checks the index of where the current score should be placed
	int scoreIndex = -1;
	for (int highScore = highestScores.length - 1 ; highScore >= 0 ; highScore--)
	{
	    if (score > highestScores [highScore])
	    {
		scoreIndex = highScore;
	    }
	}

	// Quits if the current score doesn't make it into the top five
	if (scoreIndex == -1)
	    return;

	// Shifts all the scores down and places the score in its place within the top five
	for (int topScore = highestScores.length - 1 ; topScore > scoreIndex ; topScore--)
	    highestScores [topScore] = highestScores [topScore - 1];
	highestScores [scoreIndex] = score;

    }
}
