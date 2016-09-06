/**
 * CS 431  Assignment 1
 * AddConcurrent1.java
 * Purpose: Add integers in an array using Thread/Runnable for concurrency.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

import java.util.Arrays;

/**
 * Class to maintain global variables.
 */
class GlobalInfoAdd1 {
	public static int result1 = -1; 
	public static int result2 = -1;
	public static int result;
	public static boolean complete = false;
}

/**
 * Class to implement thread for each intermediate thread
 */
class IntermediateAdd1 implements Runnable {
	int index;
	int[] inputArr;
	/**
     * Constructor to set the thread number.
     * 
     * @param threadNumber the order number of the current thread.
     */
	public IntermediateAdd1(int threadNumber, int[] arr) {
		// TODO Auto-generated constructor stub
		index = threadNumber;
		inputArr = arr;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		Add adder = new Add();
		int result = adder.add(inputArr);
		if(index == 1){
			GlobalInfoAdd1.result1 = result;
		} else{
			GlobalInfoAdd1.result2 = result; 
		}
	}
}

/**
 * Class to implement thread for combining intermediate results
 */
class CombineAdd1 implements Runnable {
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		while(GlobalInfoAdd1.result1 == -1 || GlobalInfoAdd1.result2 == -1){
			//waiting for results to be computed by other threads.
		}
		GlobalInfoAdd1.result = GlobalInfoAdd1.result1 + GlobalInfoAdd1.result2;
		GlobalInfoAdd1.complete = true;
	}
}

/**
 * Class to implement addition of integers in an array in a concurrent fashion.
 */
public class AddConcurrent1 {

	/**
     * Function for adding integers in an array using concurrency.
     * 
     * @param arr An array containing integers.
     * @return Addition of all integers in the input array. 
     */ 
	public int addConcurrent1(int[] arr){
		
		int[] arrFirstHalf = Arrays.copyOfRange(arr, 0, arr.length/2);
		int[] arrSecondHalf = Arrays.copyOfRange(arr, arr.length/2, arr.length);
		
		IntermediateAdd1 inter1 = new IntermediateAdd1(1, arrFirstHalf);
		Thread thread1 = new Thread(inter1);
		thread1.start();
		
		IntermediateAdd1 inter2 = new IntermediateAdd1(2, arrSecondHalf);
		Thread thread2 = new Thread(inter2);
		thread2.start();
		
		CombineAdd1 combineResults = new CombineAdd1();
		Thread combineResultsThread = new Thread(combineResults);
		combineResultsThread.start();
		while(GlobalInfoAdd1.complete == false){
			//wait for the final thread to return.
		}
		return GlobalInfoAdd1.result;
	}
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		AddConcurrent1 obj = new AddConcurrent1();
		int result = obj.addConcurrent1(arr);
		System.out.println(result);
	}
}
