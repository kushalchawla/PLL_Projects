package calculator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class to maintain global thread pool.
 *
 */
class AdvancedGlobalInfo {
	public static Executor pool = Executors.newCachedThreadPool();
}

/**
 * Class to initiate the advanced calculator GUI and functionality
 *
 */
public class AppAdvanced {

	public static void main(String[] args) {
		
		//create initial UI for advanced calculator
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame frame = new CalculatorUiAdvanced();
				frame.setSize(270,300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		
		//creating thread for periodic highlighting of numbers
		NumHighlighter numHl = new NumHighlighter();
		AdvancedGlobalInfo.pool.execute(numHl);
		
		//creating thread for periodic highlighting of operators		
		OpHighlighter opHl = new OpHighlighter();
		AdvancedGlobalInfo.pool.execute(opHl);
		
		//creating thread for periodic highlighting of functions
		FuncHighlighter funcHl = new FuncHighlighter();
		AdvancedGlobalInfo.pool.execute(funcHl);

	}

}
