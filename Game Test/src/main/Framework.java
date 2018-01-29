package main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



public class Framework {

	/**
	 * Frame Width
	 */
	
	public static int frameWidth;
	
	/**
	 * Frame Height
	 */
	
	public static int frameHeight;
	
	/**
	 * Time of one second in nanoseconds
	 */
	public static final long secInNanosec = 1000000000L;
	
	/**
	 * Time of 1 millisecond in nanoseconds
	 */
	
	public static final long millisecInNanosec = 1000000L;
	
	/**
	 * FPS
	 */
	
	private final int GAME_FPS = 60;
	
	/**
	 * pause between updates, in nanoseconds.
	 * 
	 */
	private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
	
	/**
	 * Possible game states
	 */
	
	public static enum GameState{STARTING, VISUALIZING, GAME_CONTENT_LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER, DESTROYED}
	
	/**
	 * Current state of the game
	 */
	public static GameState gameState;
	
	/**
	 * Elapsed game time, in nanoseconds
	 */
	private long gameTime;
	// Long used for calculating elapsed time
	private long lastTime;
	
	//The Game
	private Game game;
	
	public Framework ()
	{
		super();
		
		gameState = GameState.VISUALIZING;
		
		//We start game in new thread
		Thread gameThread = new Thread() {
			@Override
			public void run() {
				GameLoop();
			}
		};
		gameThread.start();
	}
	
	/**
	 * Set variables and objects, only for this class
	 */
	private void Initialize()
	{
		
	}
	
	/**
	 * Load files, only for this class
	 */
	private void LoadContent()
	{
		
	}
	
	/**
	 * Game Loop, updated and drawn to the screen each time
	 */
	private void GameLoop()
	{
		//These 2 variables are for the VISUALIZING state of game.
		long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
		
		//These are used for calculating the defining time for how long we should put the thread to sleep to meet the GAME_FPS
		long beginTime, timeTaken, timeLeft;
		
		while(true)
		{
			beginTime = System.nanoTime();
			
			switch(gameState)
			{
			case PLAYING:
				gameTime += System.nanoTime() - lastTime;
				
				game.UpdateGame(gameTime, mousePosition());
				
				lastTime = System.nanoTime();
				break;
			case GAMEOVER:
				break;
			case MAIN_MENU:
				break;
			case OPTIONS:
				break;
			case GAME_CONTENT_LOADING:
				break;
			case STARTING:
				//Set variables and objects
				Initialize();
				//Load files
				LoadContent();
				//When finished, change gameState to MAIN_MENU
				gameState = GameState.MAIN_MENU;
				break;
			case VISUALIZING:
				if(this.getWidth() > 1 && visualizingTime > secInNanosec)
				{
					frameWidth = this.getWidth();
					frameHeight = this.getHeight();
					
					//When we get size of frame, change status
					gameState = GameState.STARTING;
				}
				else
				{
					visualizingTime += System.nanoTime() - lastVisualizingTime;
					lastVisualizingTime = System.nanoTime();
				}
				break;
			}
			
			//Repaint the screen,
			repaint();
			
			//Calculate the time that defines how long we should sleep to meet GAME_FPS
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / millisecInNanosec; // In milliseconds
			//If time is less than 10 milliseconds, we will put thread to sleep so others can do work
			if(timeLeft <10)
				timeLeft = 10; //set a minimum
			try {
				//Provides the necessary delay and also yields control so that other thread can do work
				Thread.sleep(timeLeft);
				
			} catch (InterruptedException ex) {}
		}
	}
	
	/**
	 * Draw the game on screen.  It is called through repaint() method
	 * 
	 */
	public void Draw(Graphics2D g2d)
	{
		switch(gameState)
		{
		case PLAYING:
			game.Draw(g2d, mousePosition());
			break;
		case GAMEOVER:
			break;
		case MAIN_MENU:
			break;
		case OPTIONS:
			break;
		case GAME_CONTENT_LOADING:
			break;
		}
	}
	
	/**
	 * Start a new game.
	 */
	
	private void newGame()
	{
		//Set gameTime to zero and lastTime to current time for later use
		gameTime = 0;
		lastTime = System.nanoTime();
		
		game = new Game();
	}
	
	/**
	 * Restart game - reset game time and call RestartGame() method
	 * 
	 */
	private void restartGame()
	{
		//Set gameTime to zero and lastTime to current time for later use
		gameTime = 0;
		lastTime = System.nanoTime();
		
		game.RestartGame();
		
		
		//Change game status
		gameState = GameState.PLAYING;
	}
	/**
	 * Return mouse position in frame/window
	 * 
	 * @return Point of mouse coordinates
	 */
	private Point mousePosition()
	{
		try
		{
			Point mp = this.mousePosition();
			
			if(mp != null)
				return this.mousePosition();
			else
				return new Point(0,0);
		}
		catch (Exception e)
		{
			return new Point(0,0);
		}
	}
	
	/**
	 * This method is called when keyboard key is released.
	 * 
	 * @param e KeyEvent
	 */
	public void keyReleasedFramework(KeyEvent e)
	{
		
	}
	
	/**
	 * This method is called when mouse button is clicked
	 * 
	 * @param e MouseEvent
	 */
	public void mouseClicked(MouseEvent e)
	{
		
	}
}
