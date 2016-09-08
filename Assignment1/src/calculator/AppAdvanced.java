package calculator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class AdvancedGlobalInfo {
	public static Executor pool = Executors.newCachedThreadPool();
}

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
		NumHighlighter numHl = new NumHighlighter();
		AdvancedGlobalInfo.pool.execute(numHl);
		
		OpHighlighter opHl = new OpHighlighter();
		AdvancedGlobalInfo.pool.execute(opHl);
		
		FuncHighlighter funcHl = new FuncHighlighter();
		AdvancedGlobalInfo.pool.execute(funcHl);

	}

}
