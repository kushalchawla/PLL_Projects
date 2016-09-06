/**
 * CS 431  Assignment 1
 * AverageConcurrent2.java
 * Purpose: Average integers in an array using concurrent package.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;


/**
 * Class to implement average of integers in an array in a concurrent fashion.
 */
public class AverageConcurrent2 {

	/**
     * Function for average integers in an array using concurrency.
     * 
     * @param arr An array containing integers.
     * @return Average of all integers in the input array. 
	 * @throws InterruptedException 
     */ 
	public float averageConcurrent2(int[] arr) throws InterruptedException{
		
		AddConcurrent2 adder = new AddConcurrent2();
		int result = adder.addConcurrent2(arr);
		return ((float)result/arr.length);
	}
	
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
	 * @throws InterruptedException 
     */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		AverageConcurrent2 obj = new AverageConcurrent2();
		float result = obj.averageConcurrent2(arr);
		System.out.println(result);
	}
}
