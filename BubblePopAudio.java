import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

/** The "BubblePopAudio" class.
  * A brief description of this class
  * @author your name
  * @version date
 */
public class BubblePopAudio extends JFrame
{
    private AudioClip bubblePop;
    private AudioClip popSound;
    private AudioClip levelSound;
    private AudioClip clearBoardSound;
    private String instructions;
    private MenuItem playSound;

    public BubblePopAudio ()
    {
	super ("Bubble Pop Audio");
	setSize (500, 100);

	Container contentPane = getContentPane ();
	contentPane.add (new DrawingPanel (), BorderLayout.CENTER);

	bubblePop = Applet.newAudioClip (getCompleteURL ("BubblePop.wav"));
	popSound = Applet.newAudioClip (getCompleteURL ("popsound.wav"));
	levelSound = Applet.newAudioClip (getCompleteURL ("levelup.wav"));
	clearBoardSound = Applet.newAudioClip (getCompleteURL ("tada.wav"));
	instructions = "Press 'B' for Bubble Pop!!!";

	// Makes it a pwetty bubble
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("ball1.png"));
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


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    // Add key listeners and setting focus
	    this.setFocusable (true);
	    this.addKeyListener (new KeyHandler ());
	    this.requestFocusInWindow ();
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setColor (Color.blue);
	    g.drawString (instructions, 20, 50);

	} // paint component method
    }


    // Inner class to handle key events
    private class KeyHandler extends KeyAdapter
    {
	public void keyPressed (KeyEvent event)
	{
	    char ch = Character.toUpperCase (event.getKeyChar ());
	    if (ch == 'B')
		bubblePop.play ();
	    repaint ();

	}
    }


    public void backgroundMusic ()
    {
	bubblePop.loop ();
    }


    public void bubblePopping ()
    {
	popSound.play ();
    }


    public void levelingUp ()
    {
	levelSound.play ();
    }


    public void tada ()
    {
	clearBoardSound.play ();
    }


    public void stopSound ()
    {
	bubblePop.stop ();
    }


    public static void main (String[] args)
    {
	// Create a frame
	BubblePopAudio frame = new BubblePopAudio ();
	//  JButton button = new JButton ("Click me");
	//  frame. add(button);
	//  button.addActionListener (new AL());
	frame.setVisible (true);

    } // main method
} // BubblePopAudio class
