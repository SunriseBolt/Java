package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Create a JPanel to draw and listen for events.
 * @author Christian
 *
 */

public abstract class Canvas extends JPanel implements KeyListener, MouseListener {

	//Keyboard state - holds all states for Keyboard keys
	private static boolean[] keyboardState = new boolean[525];
	
	//Mouse states
	private static boolean[] mouseState = new boolean[3];
	
	public Canvas()
	{
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.black);
		
		
		//Adds keyboard listener to JPanel to receive events
		this.addKeyListener(this);
		//Adds mouse listener to JPanel to receive events
		this.addMouseListener(this);
	}
	
	// This method is overriden in Framework.java and is used for drawing to screen
	public abstract void Draw(Graphics2D g2d);
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		Draw(g2d);
	}
	
	//Keyboard
	/**
	 * Is a key down?
	 * 
	 * @param key Number of key for which you want to check
	 * @return true if the key is down
	 */
	public static boolean keyboardKeyState(int key)
	{
		return keyboardState[key];
	}
	
	//Methods of the keyboard listener
	@Override
	public void keyPressed(KeyEvent e)
	{
		keyboardState[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		keyboardState[e.getKeyCode()] = false;
		keyReleasedFramework(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public abstract void keyReleasedFramework(KeyEvent e);
	
	
	// Mouse
	/**
	 * Is mouse button down?
	 * Parameter "button" can be "MouseEvent.BUTTON1" - Indicated mouse button #1
	 * or "MouseEvent.BUTTON2" - indicates mouse button #2
	 * 
	 * @param button Number of mouse button for which you want to check
	 * @return true if button is down
	 */
	public static boolean mouseButtonState(int button)
	{
		return mouseState[button - 1];
	}
	
	//Sets mouse key status.
	private void mouseKeyStatus(MouseEvent e, boolean status)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			mouseState[0] = status;
		else if(e.getButton() == MouseEvent.BUTTON2)
			mouseState[1] = status;
		else if(e.getButton() == MouseEvent.BUTTON3)
			mouseState[2] = status;
	}
	
	// Methods of the mouse listener
	@Override
	public void mousePressed(MouseEvent e)
	{
		mouseKeyStatus(e, true);
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		mouseKeyStatus(e, false);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	
	
}
