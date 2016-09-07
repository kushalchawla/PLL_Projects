/**
 * CS 431  Assignment 1
 * AverageConcurrent1.java
 * Purpose: Average integers in an array using Thread/Runnable for concurrency.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;


/**
 * Class to implement average of integers in an array in a concurrent fashion.
 */
public class AverageConcurrent1 {

	/**
     * Function for average integers in an array using concurrency.
     * 
     * @param arr An array containing integers.
     * @return Average of all integers in the input array. 
     */ 
	public float averageConcurrent1(int[] arr){
		
		AddConcurrent1 adder = new AddConcurrent1();
		int result = adder.addConcurrent1(arr);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ((float)result/arr.length);
	}
	
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		AverageConcurrent1 obj = new AverageConcurrent1();
		float result = obj.averageConcurrent1(arr);
		System.out.println(result);
	}
}
