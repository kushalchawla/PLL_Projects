/**
 * CS 431  Assignment 1
 * Add.java
 * Purpose: Add integers in an array
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

/**
 * Class to implement addition of integers in an array
 */
public class Add {
	
	/**
     * Function for adding integers in an array.
     * 
     * @param arr An array containing integers.
     * @return Addition of all integers in the input array. 
     */ 
	public int add(int[] arr)
	{
		int result=0;
		for (int i = 0; i < arr.length; i++) {
			result = result + arr[i];
		}
		return result;
	}
	
	/**
     * Main Function of Add class used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		Add obj = new Add();
		int result = obj.add(arr);
		System.out.println(result);
	}
}
