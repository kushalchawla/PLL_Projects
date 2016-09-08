/**
 * CS 431  Assignment 1
 * App.java
 * Purpose: create a simple single digit calculator
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package calculator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class to maintain global thread pool.
 */
class GlobalInfo {
	public static Executor pool = Executors.newCachedThreadPool();
}

/**
 * Class to initiate the app and GUI.
 */
public class App {

	/**
     * Main function used for initializing highlighter and UI threads.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		
		//Create JFrame for the calculator
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new CalculatorUi();
				frame.setSize(270,300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		Highlighter hl = new Highlighter();
		GlobalInfo.pool.execute(hl);
	}

}
