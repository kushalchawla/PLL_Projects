/**
 * CS 431  Assignment 1
 * MultiplyConcurrent1.java
 * Purpose: Multiply integers in an array using Thread/Runnable for concurrency.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

import java.util.Arrays;

/**
 * Class to maintain global variables.
 */
class GlobalInfoMultiply1 {
	public static int result1 = -1; 
	public static int result2 = -1;
	public static int result;
	public static boolean complete = false;
}

/**
 * Class to implement thread for each intermediate thread
 */
class IntermediateMultiply1 implements Runnable {
	int index;
	int[] inputArr;
	/**
     * Constructor to set the thread number.
     * 
     * @param threadNumber the order number of the current thread.
     */
	public IntermediateMultiply1(int threadNumber, int[] arr) {
		// TODO Auto-generated constructor stub
		index = threadNumber;
		inputArr = arr;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		Multiply multiplier = new Multiply();
		int result = multiplier.multiply(inputArr);
		if(index == 1){
			GlobalInfoMultiply1.result1 = result;
		} else{
			GlobalInfoMultiply1.result2 = result; 
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Class to implement thread for combining intermediate results
 */
class CombineMultiply1 implements Runnable {
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		while(GlobalInfoMultiply1.result1 == -1 || GlobalInfoMultiply1.result2 == -1){
			//waiting for results to be computed by other threads.
		}
		GlobalInfoMultiply1.result = GlobalInfoMultiply1.result1 * GlobalInfoMultiply1.result2;
		GlobalInfoMultiply1.complete = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Class to implement multiplication of integers in an array in a concurrent fashion.
 */
public class MultiplyConcurrent1 {

	/**
     * Function for multiplying integers in an array using concurrency.
     * 
     * @param arr An array containing integers.
     * @return Multiplication of all integers in the input array. 
     */ 
	public int multiplyConcurrent1(int[] arr){
		
		int[] arrFirstHalf = Arrays.copyOfRange(arr, 0, arr.length/2);
		int[] arrSecondHalf = Arrays.copyOfRange(arr, arr.length/2, arr.length);
		
		IntermediateMultiply1 inter1 = new IntermediateMultiply1(1, arrFirstHalf);
		Thread thread1 = new Thread(inter1);
		thread1.start();
		
		IntermediateMultiply1 inter2 = new IntermediateMultiply1(2, arrSecondHalf);
		Thread thread2 = new Thread(inter2);
		thread2.start();
		
		CombineMultiply1 combineResults = new CombineMultiply1();
		Thread combineResultsThread = new Thread(combineResults);
		combineResultsThread.start();
		while(GlobalInfoMultiply1.complete == false){
			//wait for the final thread to return.
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return GlobalInfoMultiply1.result;
	}
	
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		MultiplyConcurrent1 obj = new MultiplyConcurrent1();
		int result = obj.multiplyConcurrent1(arr);
		System.out.println(result);
	}
}
