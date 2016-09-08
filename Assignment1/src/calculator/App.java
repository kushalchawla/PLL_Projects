package calculator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class GlobalInfo {
	public static Executor pool = Executors.newCachedThreadPool();
}
public class App {

	public static void main(String[] args) {
		
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
