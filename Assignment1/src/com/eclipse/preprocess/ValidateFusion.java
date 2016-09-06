/**
 * CS 431  Assignment 1
 * ValidateFusion.java
 * Purpose: Validate results using a threshold
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

/**
 * Class to implement validation of results
 */
public class ValidateFusion {

	float AVG;
	int MUL,ADD;
	
	/**
     * Constructor to set initial thresholds according to prior suggested values. 
     */
	public ValidateFusion() {
		AVG = 100;
		MUL = 100000;
		ADD = 10000;
	}
	
	/**
     * Function to change threshold values if necessary.
     * 
     * @param NEW_AVG New value for average threshold.
     * @param NEW_MUL New value for multiply threshold.
     * @param NEW_ADD New value for add threshold. 
     */
	public void setThresholds(float NEW_AVG, int NEW_MUL, int NEW_ADD) {
		AVG = NEW_AVG;
		MUL = NEW_MUL;
		ADD = NEW_ADD;
	}
	
	/**
     * Function to validate and print output messages.
     * 
     * @param val Value to be compared with the threshold.
     * @param flag To check fusion method. 
     */
	public void validate( float val , int flag ) {
		
		switch(flag) {
		
		case 0: if( val > AVG )
					System.out.println("state detected from AVG");
				else
					System.out.println("state not detected from AVG");
				break;
		
		case 1: if( (int)(val) > MUL)
					System.out.println("state detected from MUL");
				else
					System.out.println("state not detected from MUL");
				break;
		
		case 2: if( (int)(val) > ADD) 
					System.out.println("state detected from ADD");
				else
					System.out.println("state not detected from ADD");
				break;
		
		default: System.out.println("Invalid operation selected :/");
		
		}
	}
	
	/**
     * Main Function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		
		ValidateFusion obj = new ValidateFusion();
		obj.validate(10000, 0);
		obj.validate(10000, 1);
		obj.validate(10000, 2);
		obj.validate(10000, 3);
	}
		
}
