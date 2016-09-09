package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import javafx.scene.input.KeyCode;

class AdvancedKeyPressResponse implements Runnable {

	static int highlightedNum;
	static int highlightedFunc;
	static int highlightedOp;
	String labelText;
	String toAppend;
	String readText;
	int keyPressed;
	
	public AdvancedKeyPressResponse(String text, int kp) {
		highlightedNum = NumHighlighter.highlightedNum;
		highlightedFunc = FuncHighlighter.highlightedFunc;
		highlightedOp = OpHighlighter.highlightedOp;
		readText = text;
		keyPressed = kp;
	}
	public String evaluate(String str) {
		if(!ExpressionEval.validateExpression(str))
			return "ERROR";
		return ExpressionEval.evaluateExpression(str);
//		ScriptEngineManager mgr = new ScriptEngineManager();
//	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
//	    try {
//			Double ans = (Double)engine.eval(str);
//			return Double.toString(ans);
//		} catch (ScriptException e) {			
//			e.printStackTrace();
//			return "ERROR";
//		}
	}
	
	@Override
	public void run() {
		switch(keyPressed) {
		case KeyEvent.VK_ENTER:
			toAppend = Integer.toString(highlightedNum);
			labelText = readText.concat(toAppend);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("space pressed !");
			switch(highlightedFunc) {
			case 1: 
				toAppend = "+";
				break;
			case 2: 
				toAppend = "-";
				break;
			case 3: 
				toAppend = "/";
				break;
			case 4: 
				toAppend = "*";
				break;
			}
			labelText = readText.concat(toAppend);
			break;
		case KeyEvent.VK_SHIFT:
			switch(highlightedOp) {
			case 1:
				labelText = "";
				break;
			case 2: 
				labelText = evaluate(readText);
				CalculatorUiAdvanced.pauseMotion = true;
				break;
			}
			break;
		case KeyEvent.VK_CONTROL:
			SwingUtilities.invokeLater(new Runnable() {				
				@Override
				public void run() {
					CalculatorUiAdvanced.reset();
				}
			});
			CalculatorUiAdvanced.pauseMotion = false;			
			return;
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CalculatorUiAdvanced.display.setText(labelText);
			}			
		});
	}
	
}

class NumHighlighter implements Runnable {

	volatile static int highlightedNum;
	int toReset;
	public NumHighlighter() {		
		highlightedNum = 1;
	}
	
	@Override
	public void run() {
		while(true) {
			while(CalculatorUiAdvanced.pauseMotion);
			toReset = (highlightedNum - 1) % 10;
			
			if(highlightedNum == 0)
				toReset = 9;
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					CalculatorUiAdvanced.changeBg(1,toReset,false);
					CalculatorUiAdvanced.changeBg(1,highlightedNum,true);				
				}
				
			});
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			highlightedNum = (highlightedNum + 1)% 10 ; 
		}
		
	}
	
}

class FuncHighlighter implements Runnable{

	volatile static int highlightedFunc;
	int toReset;
	public FuncHighlighter() {		
		highlightedFunc = 1;
	}
	
	@Override
	public void run() {
		while(true) {
			while(CalculatorUiAdvanced.pauseMotion);
			toReset = highlightedFunc - 1;
			
			if(highlightedFunc == 1)
				toReset = 4;
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					CalculatorUiAdvanced.changeBg(2, toReset, false);
					CalculatorUiAdvanced.changeBg(2, highlightedFunc, true);
				}
			});
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			highlightedFunc = highlightedFunc % 4 + 1;
			
		}
		
		
	}
	
}

class OpHighlighter implements Runnable {

	volatile static int highlightedOp;
	int toReset;
	
	public OpHighlighter() {
		highlightedOp = 1;
	}
	
	@Override
	public void run() {
		while(true) {
			while(CalculatorUiAdvanced.pauseMotion);
			if(highlightedOp == 1)
				toReset = 2;
			else
				toReset = 1;
			
			SwingUtilities.invokeLater(new Runnable() {
							
				@Override
				public void run() {
					CalculatorUiAdvanced.changeBg(3, toReset, false);
					CalculatorUiAdvanced.changeBg(3, highlightedOp, true);
				}
			});

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			highlightedOp = highlightedOp % 2 + 1;
			
		}
	}
	
}

public class CalculatorUiAdvanced extends JFrame {

	private JPanel contentPane;

	//colors used for buttons for background and when highlighted
	public volatile static boolean pauseMotion;
	public static Color numBg = new Color(0, 102, 153);
	public static Color numHighlighted = new Color(255, 255, 0);
	public static Color funcBg = UIManager.getColor("OptionPane.foreground");
	public static Color funcHighlighted =  new Color(0, 153, 0);
	public static Color opBg = new Color(0, 102, 153);
	public static Color opHighlighted = new Color(230, 59, 17);
	
	public static JLabel num1;
	public static JLabel num2 ;
	public static JLabel num3 ;
	public static JLabel num4 ;
	public static JLabel num5 ;
	public static JLabel num6 ;
	public static JLabel num7 ;
	public static JLabel num8 ;
	public static JLabel num9 ;
	public static JLabel num0 ;
	public static JLabel equals ;
	public static JLabel clear ;
	public static JLabel plus ;
	public static JLabel minus ;
	public static JLabel div ;
	public static JLabel mul ;
	public static JLabel display;
	
	/**
	 * function to change the background of buttons
	 * @param type To identify the type of the key
	 * @param buttonNo To identify the key
	 * @param boolean to tell whether to highlight or reset the background 
	 */
	public static void changeBg(int type, int buttonNo, boolean highlight) {
		switch(type) {
		case 1:
			switch(buttonNo) {
			case 0:
				if(highlight)
					num0.setBackground(numHighlighted);
				else
					num0.setBackground(numBg);
				break;
			case 1:
				if(highlight)
					num1.setBackground(numHighlighted);
				else
					num1.setBackground(numBg);
				break;
			case 2:
				if(highlight)
					num2.setBackground(numHighlighted);
				else
					num2.setBackground(numBg);
				break;
			case 3:
				if(highlight)
					num3.setBackground(numHighlighted);
				else
					num3.setBackground(numBg);
				break;
			case 4:
				if(highlight)
					num4.setBackground(numHighlighted);
				else
					num4.setBackground(numBg);
				break;
			case 5:
				if(highlight)
					num5.setBackground(numHighlighted);
				else
					num5.setBackground(numBg);
				break;
			case 6:
				if(highlight)
					num6.setBackground(numHighlighted);
				else
					num6.setBackground(numBg);
				break;
			case 7:
				if(highlight)
					num7.setBackground(numHighlighted);
				else
					num7.setBackground(numBg);
				break;
			case 8:
				if(highlight)
					num8.setBackground(numHighlighted);
				else
					num8.setBackground(numBg);
				break;
			case 9:
				if(highlight)
					num9.setBackground(numHighlighted);
				else
					num9.setBackground(numBg);
				break;
			}
			break;
		case 2:
			switch(buttonNo) {
			case 1:
				if(highlight)
					plus.setBackground(funcHighlighted);
				else
					plus.setBackground(funcBg);
				break;
			case 2:
				if(highlight)
					minus.setBackground(funcHighlighted);
				else
					minus.setBackground(funcBg);
				break;
			case 3:
				if(highlight)
					div.setBackground(funcHighlighted);
				else
					div.setBackground(funcBg);
				break;
			case 4:
				if(highlight)
					mul.setBackground(funcHighlighted);
				else
					mul.setBackground(funcBg);
				break;
			}
			break;
		case 3:
			switch(buttonNo) {
			case 1:
				if(highlight)
					clear.setBackground(opHighlighted);
				else
					clear.setBackground(opBg);
				break;
			case 2:
				if(highlight)
					equals.setBackground(opHighlighted);
				else
					equals.setBackground(opBg);
				break;
			}
			break;
		}
	}
	
	public static void reset() {
		if(display.getText() == "ERROR" || display.getText() == "Infinity")
			display.setText("");
		num1.setBackground(numBg);
		num2.setBackground(numBg);
		num3.setBackground(numBg);
		num4.setBackground(numBg);
		num5.setBackground(numBg);
		num6.setBackground(numBg);
		num7.setBackground(numBg);
		num8.setBackground(numBg);
		num9.setBackground(numBg);
		num0.setBackground(numBg);
		
		clear.setBackground(opBg);
		equals.setBackground(opBg);
		
		plus.setBackground(funcBg);
		minus.setBackground(funcBg);
		div.setBackground(funcBg);
		mul.setBackground(funcBg);
		
		NumHighlighter.highlightedNum = 1;
		FuncHighlighter.highlightedFunc = 1;
		OpHighlighter.highlightedOp = 1;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorUiAdvanced frame = new CalculatorUiAdvanced();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalculatorUiAdvanced() {
		super("Advanced Calculator");
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(pauseMotion && e.getKeyCode() != KeyEvent.VK_CONTROL)
					return;
				if (	e.getKeyCode() == KeyEvent.VK_ENTER ||
						e.getKeyCode() == KeyEvent.VK_SPACE ||
						e.getKeyCode() == KeyEvent.VK_SHIFT ||
						e.getKeyCode() == KeyEvent.VK_CONTROL) {
					System.out.println("Special Key Pressed\nProcessing..");
					AdvancedKeyPressResponse kpr = new AdvancedKeyPressResponse(display.getText(), e.getKeyCode());
					AdvancedGlobalInfo.pool.execute(kpr);
				}
			}
		});
		pauseMotion = false;
		
		System.out.println("UI bana");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 270, 300);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("pressed");
			}
		});
		contentPane.setBackground(UIManager.getColor("RadioButton.disabledText"));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		num1 = new JLabel("1");
		num1.setForeground(new Color(255, 204, 0));
		num1.setOpaque(true);
		num1.setBounds(10, 55, 80, 30);
		num1.setHorizontalAlignment(SwingConstants.CENTER);
		num1.setBackground(new Color(0, 102, 153));
		contentPane.add(num1);
		
		display = new JLabel("");
		display.setBorder(new EmptyBorder(5, 5, 5, 5));
		display.setFont(new Font("Dialog", Font.BOLD, 16));
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setForeground(new Color(102, 255, 255));
		display.setBackground(new Color(0, 0, 51));
		display.setBounds(10, 10, 250, 35);
		display.setOpaque(true);
		contentPane.add(display);
		
		num2 = new JLabel("2");
		num2.setOpaque(true);
		num2.setHorizontalAlignment(SwingConstants.CENTER);
		num2.setForeground(new Color(255, 204, 0));
		num2.setBackground(new Color(0, 102, 153));
		num2.setBounds(95, 55, 80, 30);
		contentPane.add(num2);
		
		num3 = new JLabel("3");
		num3.setOpaque(true);
		num3.setHorizontalAlignment(SwingConstants.CENTER);
		num3.setForeground(new Color(255, 204, 0));
		num3.setBackground(new Color(0, 102, 153));
		num3.setBounds(180, 55, 80, 30);
		contentPane.add(num3);
		
		num4 = new JLabel("4");
		num4.setOpaque(true);
		num4.setHorizontalAlignment(SwingConstants.CENTER);
		num4.setForeground(new Color(255, 204, 0));
		num4.setBackground(new Color(0, 102, 153));
		num4.setBounds(10, 95, 80, 30);
		contentPane.add(num4);
		
		num5 = new JLabel("5");
		num5.setOpaque(true);
		num5.setHorizontalAlignment(SwingConstants.CENTER);
		num5.setForeground(new Color(255, 204, 0));
		num5.setBackground(new Color(0, 102, 153));
		num5.setBounds(95, 95, 80, 30);
		contentPane.add(num5);
		
		num6 = new JLabel("6");
		num6.setOpaque(true);
		num6.setHorizontalAlignment(SwingConstants.CENTER);
		num6.setForeground(new Color(255, 204, 0));
		num6.setBackground(new Color(0, 102, 153));
		num6.setBounds(180, 95, 80, 30);
		contentPane.add(num6);
		
		num7 = new JLabel("7");
		num7.setOpaque(true);
		num7.setHorizontalAlignment(SwingConstants.CENTER);
		num7.setForeground(new Color(255, 204, 0));
		num7.setBackground(new Color(0, 102, 153));
		num7.setBounds(10, 135, 80, 30);
		contentPane.add(num7);
		
		num8 = new JLabel("8");
		num8.setOpaque(true);
		num8.setHorizontalAlignment(SwingConstants.CENTER);
		num8.setForeground(new Color(255, 204, 0));
		num8.setBackground(new Color(0, 102, 153));
		num8.setBounds(95, 135, 80, 30);
		contentPane.add(num8);
		
		num9 = new JLabel("9");
		num9.setOpaque(true);
		num9.setHorizontalAlignment(SwingConstants.CENTER);
		num9.setForeground(new Color(255, 204, 0));
		num9.setBackground(new Color(0, 102, 153));
		num9.setBounds(180, 135, 80, 30);
		contentPane.add(num9);
		
		num0 = new JLabel("0");
		num0.setOpaque(true);
		num0.setHorizontalAlignment(SwingConstants.CENTER);
		num0.setForeground(new Color(255, 204, 0));
		num0.setBackground(new Color(0, 102, 153));
		num0.setBounds(95, 175, 80, 30);
		contentPane.add(num0);
		
		clear = new JLabel("C");
		clear.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		clear.setOpaque(true);
		clear.setHorizontalAlignment(SwingConstants.CENTER);
		clear.setForeground(new Color(255, 204, 0));
		clear.setBackground(new Color(51, 102, 255));
		clear.setBounds(10, 175, 80, 30);
		contentPane.add(clear);
		
		equals = new JLabel("=");
		equals.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 17));
		equals.setOpaque(true);
		equals.setHorizontalAlignment(SwingConstants.CENTER);
		equals.setForeground(new Color(255, 204, 0));
		equals.setBackground(new Color(51, 102, 255));
		equals.setBounds(180, 175, 80, 30);
		contentPane.add(equals);
		
		plus = new JLabel("+");
		plus.setFont(new Font("Dialog", Font.BOLD, 20));
		plus.setOpaque(true);
		plus.setHorizontalAlignment(SwingConstants.CENTER);
		plus.setForeground(new Color(255, 204, 0));
		plus.setBackground(UIManager.getColor("OptionPane.foreground"));
		plus.setBounds(10, 225, 55, 30);
		contentPane.add(plus);
		
		minus = new JLabel("-");
		minus.setFont(new Font("Dialog", Font.BOLD, 20));
		minus.setOpaque(true);
		minus.setHorizontalAlignment(SwingConstants.CENTER);
		minus.setForeground(new Color(255, 204, 0));
		minus.setBackground(UIManager.getColor("OptionPane.foreground"));
		minus.setBounds(75, 225, 55, 30);
		contentPane.add(minus);
		
		div = new JLabel("/");
		div.setFont(new Font("Dialog", Font.BOLD, 20));
		div.setOpaque(true);
		div.setHorizontalAlignment(SwingConstants.CENTER);
		div.setForeground(new Color(255, 204, 0));
		div.setBackground(UIManager.getColor("OptionPane.foreground"));
		div.setBounds(140, 225, 55, 30);
		contentPane.add(div);
		
		mul = new JLabel("*");
		mul.setFont(new Font("Dialog", Font.BOLD, 20));
		mul.setOpaque(true);
		mul.setHorizontalAlignment(SwingConstants.CENTER);
		mul.setForeground(new Color(255, 204, 0));
		mul.setBackground(UIManager.getColor("OptionPane.foreground"));
		mul.setBounds(205, 225, 55, 30);
		contentPane.add(mul);
		
	}

}
