package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class Window extends JFrame{
	private Window()
	{
		//Sets the title.
		this.setTitle("The Game");
		
		//Set size.
		if(false)  //Fullscreen
		{
			//Disable decoration for the frame.
			this.setUndecorated(true);
			// Put frame to fullscreen.
			this.setExtendedState(this.MAXIMIZED_BOTH);
		}
		else //Window mode
		{
			//Size of frame.
			this.setSize(1024,768);
			//Put frame to center of screen.
			this.setLocationRelativeTo(null);
			// Make frame unable to be resized by user.
			this.setResizable(false);
		}
		
		//Exit application on user close.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create instance of Framework.java
		this.setContentPane(new Framework());
		
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		//Use the event dispatch thread to build the UI for thread-safety
		SwingUtilities.invokeLater(new Runnable()	{
			@Override
			public void run(){
				new Window();
			}
				});
	}
}
