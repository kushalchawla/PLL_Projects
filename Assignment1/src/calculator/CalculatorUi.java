package calculator;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class Highlighter {

	static volatile int highlightedNum;
	static volatile int highlightedOp;
	static volatile int highlightedFunc;
	static volatile int currentlyHighlighted;
	
	public Highlighter() {
		highlightedNum = 1;
		highlightedOp = 1;
		highlightedFunc = 1;
		currentlyHighlighted = 1;
	}
	public static void highlight() {
		int prev;
		while(true){
			switch(currentlyHighlighted) {
			case 1:	
				SwingUtilities.invokeLater(new Runnable() {
									
									@Override
									public void run() {
										switch(highlightedNum) {
										case 1: 
											CalculatorUi.num0.setBackground(new Color(0, 102, 153));
											CalculatorUi.num1.setBackground(new Color(255, 255, 0));
											break;
											
										case 2:
											CalculatorUi.num1.setBackground(new Color(0, 102, 153));
											CalculatorUi.num2.setBackground(new Color(255, 255, 0));
											break;
											
										case 3:
											CalculatorUi.num2.setBackground(new Color(0, 102, 153));
											CalculatorUi.num3.setBackground(new Color(255, 255, 0));
											break;
											
										case 4:
											CalculatorUi.num3.setBackground(new Color(0, 102, 153));
											CalculatorUi.num4.setBackground(new Color(255, 255, 0));
											break;
											
										case 5:
											CalculatorUi.num4.setBackground(new Color(0, 102, 153));
											CalculatorUi.num5.setBackground(new Color(255, 255, 0));
											break;
											
										case 6:
											CalculatorUi.num5.setBackground(new Color(0, 102, 153));
											CalculatorUi.num6.setBackground(new Color(255, 255, 0));
											break;
											
										case 7:
											CalculatorUi.num6.setBackground(new Color(0, 102, 153));
											CalculatorUi.num7.setBackground(new Color(255, 255, 0));
											break;
											
										case 8:
											CalculatorUi.num7.setBackground(new Color(0, 102, 153));
											CalculatorUi.num8.setBackground(new Color(255, 255, 0));
											break;
											
										case 9:
											CalculatorUi.num8.setBackground(new Color(0, 102, 153));
											CalculatorUi.num9.setBackground(new Color(255, 255, 0));
											break;
											
										case 0:
											CalculatorUi.num9.setBackground(new Color(0, 102, 153));
											CalculatorUi.num0.setBackground(new Color(255, 255, 0));
											break;
										}
										
									}
								});
								
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				highlightedNum = (highlightedNum + 1)%10;
				break;
			}
		}
		
	} 
}
public class CalculatorUi extends JFrame {

	private JPanel contentPane;
	JLabel test1;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorUi frame = new CalculatorUi();
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
	public CalculatorUi() {
		super("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 270, 300);
		contentPane = new JPanel();
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
		
		JLabel display = new JLabel("");
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
