/**
 * CS 431  Assignment 1
 * StringToInt.java
 * Purpose: Convert an 8-bit binary string to an array
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

/**
 * Class to implement conversion from an 8-bit binary string to an integer
 */
public class StringToInt {

	/**
     * Function to convert a binary string to the corresponding integer value.
     * 
     * @param str A string to be converted to an integer.
     * @return An integer corresponding to the input string. 
     */
	public int convert(String str) {
		int val = Integer.parseInt(str, 2);
		return val;
	}
	
	/**
     * Main Function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {		
		String bin = "10010111";
		StringToInt obj = new StringToInt();
		int res = obj.convert(bin);
		System.out.println(res); 	
	}

}