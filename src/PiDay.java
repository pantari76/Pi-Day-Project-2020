/* Ari Pant
 * Pi Day Project */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The PiDay class is the class with the entry point to the program
public class PiDay {
	// Creating the main window for the application
	JFrame mainWindow = new JFrame("Ari's Pi Day Project");
	
	// Setting the width and height for the frame
	public static int width = 800;
	public static int height = 500;
	
	// The initialization function 
	public void createAndShowGUI() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = mainWindow.getContentPane();
		
		// Creating the GamePanel function that runs the game
		GamePanel gamePanel = new GamePanel();
		gamePanel.addKeyListener(gamePanel);
		gamePanel.setFocusable(true);
		
		container.setLayout(new BorderLayout());
		container.add(gamePanel, BorderLayout.CENTER);
		
		mainWindow.setSize(width, height);
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);
	}
	
	// Executing the game
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		  public void run() {
		    new PiDay().createAndShowGUI();
		  }
		});
	}
}