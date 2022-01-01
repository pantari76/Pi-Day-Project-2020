/* Ari Pant
* Pi Day Project */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener, Runnable {

	// Variables for game fucntions 
	public static int width;
	public static int height;
	private Thread animator;
	
	private boolean running;
	private boolean gameOver;
	
	private boolean goNorth;
	private boolean goSouth;
	private int addSpace;
	private int score;
	private int speed;
	
	private int origRectX, origRectY;
	private int origX;
	private int obstX, obstY;
	private int obst2X;
	
	private final int PIW = 30;
	private final int PIH = 20;

	// Constructor that intitializes the game variables
	public GamePanel() {
		width = 800;
		height = 500;
		
		score = 0;
		addSpace = 0;
		goNorth = false;
		goSouth = false;
		gameOver = false;
		running = false;
		
		speed = 0;
		
		origX = obstX = 800;
		obstY = 400;
		obst2X = 1200;
		
		origRectX = 100;
		origRectY = 400;
		
		setSize(width, height);
		setVisible(true);
	
	}
	
	// Creating the GUI 
	public void paint(Graphics g) {
		super.paint(g);
		
		// Text for the game
		g.setFont(new Font("Courier New", Font.BOLD, 25));
		g.drawString("  LEAP INTO PI DAY", getWidth()/3 - 5, 50);
		g.drawString(Integer.toString(score), getWidth()/2 - 5, 100);
		
		g.drawLine(0, 400 + PIW, width, 400 + PIW);
		
		// Player object
		g.drawRect(origRectX, origRectY, PIW, PIW);
		g.drawString(String.valueOf("\u03C0"), (origRectX + PIW - 21), origRectY + PIW - 8);
		
		// Obstacles
		g.setColor(Color.RED);
		g.fillRect(obstX, obstY - 9, 20, 40);
		g.fillRect(obst2X, obstY + 10, 20, 20);
	}
	
	// Running the game 
	public void run() {
		running = true;
		
		while(running) {
			// Calling the function for updating game
			updateGame();
			
			// Calling the paint function again to update the graphics
			repaint();      
			
			// Pausing the thread for 25 ms to slow down the game
			try {
				Thread.sleep(25);
			} 
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Function for updating the game
	public void updateGame() {
		// Increase the score by one for every 25 ms survived
		score += 1;
		
		// Jumping mechanism 
		
		if(goNorth) {
			origRectY -= 5;
			
			if (addSpace != 15)
				addSpace++;
			else {
				goSouth = true;
				goNorth = false;
			}
		}
		if (goSouth)  {			
			if(origRectY < 400)
				origRectY += 5;      
			else 
				origRectY = 400;   
			
			System.out.println (addSpace);
			if (addSpace != 0)
				addSpace--;
			else 
				goSouth = false;
		}
		
		// Creating the obsticles for the game
		if(obstX > 0) {
			obstX -= 8 + speed;
		}
		else 
			obstX = origX;
		
		if(obst2X >0) {
			obst2X -=8 + speed;
		}
		else{
			obst2X = 800;
			speed += 1;
		}
		
		// Collision Detection
		if(new Rectangle(origRectX, origRectY, PIW,PIW).intersects(new Rectangle(obstX, obstY, 20,38)) || new Rectangle(origRectX, origRectY, PIW, PIW).intersects(new Rectangle(obst2X, obstY + 12, 20, 20))) {
			running = false;
			gameOver = true;
			System.out.println ("LOST");
		}
	}
	
	// Resetting the game
	public void reset() {
		score = 0;
		System.out.println("reset");
		gameOver = false;
		addSpace = 0;
		obstX = origX;
		obst2X = 1200;
		origRectY = 400;
		speed = 0;
	}
	
	// KeyListener Functions
	
	public void keyTyped(KeyEvent e) {
		// Checks to see if the space button was pressed
		if(e.getKeyChar() == ' ') {    
			if(gameOver) 
				reset();
			if (animator == null || !running) {
				System.out.println("Game starts");        
				animator = new Thread(this);
				animator.start();     
			} 
			else {
				if(addSpace == 0)
					addSpace =1;
				if (addSpace == 1)
					goNorth = true;	
			}
		}
		
		// Checks to see if the restart key was pressed
		if (e.getKeyChar() == 'u')
			goNorth = true;
	}
	
	public void keyPressed(KeyEvent e) {
		// This function is needed for KeyListener to function
	}
	
	// Checking to see if the key has been released and to act accordingly  
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == ' ') {
			if(addSpace == 1) {
				addSpace = 0;
				goNorth = false;
			}
		}
	}


}