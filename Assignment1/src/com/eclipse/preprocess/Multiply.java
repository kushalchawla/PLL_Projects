/**
 * CS 431  Assignment 1
 * Multiply.java
 * Purpose: Multiply integers in an array
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

/**
 * Class to implement multiplication of integers in an array
 */
public class Multiply {

	/**
     * Function for multiplying integers in an array.
     * 
     * @param arr An array containing integers.
     * @return Multiplication of all integers in the input array. 
     */ 
	public int multiply(int[] arr)
	{
		int result=1;
		for (int i = 0; i < arr.length; i++) {
			result = result * arr[i];
		}
		
		//validate the multiplication using the threshold
		ValidateFusion validator = new ValidateFusion();
		validator.validate(result, 1);
		
		return result;
	}
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,2,7};
		Multiply obj = new Multiply();
		int result = obj.multiply(arr);
		System.out.println(result);
	}
}
