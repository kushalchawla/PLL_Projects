/**
 * CS 431  Assignment 1
 * Average.java
 * Purpose: Average integers in an array
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

/**
 * Class to implement average of integers in an array
 */
public class Average {

	/**
     * Function for taking average of integers in an array.
     * 
     * @param arr An array containing integers.
     * @return Average of all integers in the input array. 
     */
	public float average(int[] arr)
	{
		float result=0;
		for (int i = 0; i < arr.length; i++) {
			result = result + arr[i];
		}
		
		result = result/arr.length;		
		return (result);
	}
	
	/**
     * Main Function of Average class used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		Average obj = new Average();
		float result = obj.average(arr);
		System.out.println(result);
	}
}