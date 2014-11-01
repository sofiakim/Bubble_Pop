import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

/** The "BubblePopAudio" class.
  * This class contains the audio files for Bubble Pop
  * @author Sofia Kim, Tracy Lei, Jessie Ma
  * @version June 18, 2012
 */
public class BubblePopAudio extends JFrame
{
    // Declares variables for audio
    private AudioClip bubblePop;
    private AudioClip popSound;
    private AudioClip levelSound;
    private AudioClip clearBoardSound;
    private AudioClip gameOver;
    private MenuItem playSound;

    public BubblePopAudio ()
    {
	super ("Bubble Pop Audio");
	
	// Set the variables to the audio clips
	bubblePop = Applet.newAudioClip (getCompleteURL ("BubblePop.wav"));
	popSound = Applet.newAudioClip (getCompleteURL ("popsound.wav"));
	levelSound = Applet.newAudioClip (getCompleteURL ("levelup.wav"));
	clearBoardSound = Applet.newAudioClip (getCompleteURL ("tada.wav"));
	gameOver = Applet.newAudioClip (getCompleteURL ("GameOverMan.wav"));

    }


    // Gets the URL needed for newAudioClip
    public URL getCompleteURL (String fileName)
    {
	try
	{
	    return new URL ("file:" + System.getProperty ("user.dir") + "/" + fileName);
	}
	catch (MalformedURLException e)
	{
	    System.err.println (e.getMessage ());
	}
	return null;
    }

    /** Plays the background music of the game in a loop
      */
    public void backgroundMusic ()
    {
	bubblePop.loop ();
    }

    /** Plays the sound of a bubble popping when a bubble pops
      */
    public void bubblePopping ()
    {
	popSound.play ();
    }

    /** Plays the sound of a level up when a new level is reached
      */
    public void levelingUp ()
    {
	levelSound.play ();
    }

    /** Plays the sound of when a clear bonus is awarded
      */
    public void tada ()
    {
	clearBoardSound.play ();
    }

    /** Stops the background music from playing
      */
    public void stopSound ()
    {
	bubblePop.stop ();
    }
    
    /** Plays the sound of when a game over occurs
      */
    public void gameOverSound ()
    {
	gameOver.play ();
    }


} // BubblePopAudio class
