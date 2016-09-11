package calculator;

/**
 * Class to help in evaluating the expression
 *
 */
public class ExpressionEval {

	/**
	 * @param str the expression to be evaluated
	 * @return evaluated value of expression
	 * Credits: Taken from stackoverflow.com
	 */
	public static double eval(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor

	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus

	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                x = parseFactor();
	                if (func.equals("sqrt")) x = Math.sqrt(x);
	                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	                else throw new RuntimeException("Unknown function: " + func);
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

	            return x;
	        }
	    }.parse();
	}
	
	/**
	 * @param expression the expression to be evaluated
	 * @return String: the evaluated expression
	 */
	public static String evaluateExpression(String expression) {
		Double ans = eval(expression);
		return Double.toString(ans);
	}
	
	/**
	 * @param expression the expression to be evaluated
	 * @return boolean: true if the expression is valid, otherwise false
	 */
	public static boolean validateExpression(String expression) {
		
		if(!Character.isDigit(expression.charAt(0)) || 
				!Character.isDigit(expression.charAt(expression.length()-1)))
			return false;
		
		for (int i = 0; i < expression.length(); i++) {
			if(!Character.isDigit(expression.charAt(i)) && !isOperator(expression.charAt(i)))
				return false;
		}
		
		for (int i = 0; i < expression.length()-1; i++) {
			if(isOperator(expression.charAt(i)) && isOperator(expression.charAt(i+1)))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @param ch character to be checked
	 * @return boolean: true if ch belongs to the set { +, -, /, *, . }
	 */
	public static boolean isOperator(char ch) {
		if(ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '.')
			return true;
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
