/**
 * CS 431  Assignment 1
 * MultiplyConcurrent2.java
 * Purpose: Multiply integers in an array using concurrent package.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.CountDownLatch;

/**
 * Class to maintain global variables.
 */
class GlobalInfoMultiply2 {
	public static int result1; 
	public static int result2;
	public static int result;
}

/**
 * Class to implement thread for each intermediate thread
 */
class IntermediateMultiply2 implements Runnable {
	int index;
	int[] inputArr;
	CountDownLatch signal;
	/**
     * Constructor to set the thread number.
     * 
     * @param threadNumber the order number of the current thread.
     * @param arr Array containing input integers.
     * @param signal A Count Down Latch Signal for completion.
     */
	public IntermediateMultiply2(int threadNumber, int[] arr, CountDownLatch signal) {
		// TODO Auto-generated constructor stub
		index = threadNumber;
		inputArr = arr;
		this.signal = signal;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		Multiply multiplier = new Multiply();
		int result = multiplier.multiply(inputArr);
		if(index == 1){
			GlobalInfoMultiply2.result1 = result;
		} else{
			GlobalInfoMultiply2.result2 = result; 
		}
		signal.countDown();
	}
}

/**
 * Class to implement thread for combining intermediate results
 */
class CombineMultiply2 implements Runnable {
	CountDownLatch signal;
	CountDownLatch completeSig;
	/**
     * Constructor to set the Count Down Latch.
     * 
     * @param signal Latch to signal previous result is computed.
     * @param completeSig Latch to inform completion.
     */
	public CombineMultiply2(CountDownLatch signal,CountDownLatch completeSig) {
		// TODO Auto-generated constructor stub
		this.signal = signal;
		this.completeSig=completeSig;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     * @throws InterruptedException
     */
	public void run() {
		try {
			signal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GlobalInfoMultiply2.result = GlobalInfoMultiply2.result1 * GlobalInfoMultiply2.result2;
		completeSig.countDown();
	}
}

/**
 * Class to implement multiplication of integers in an array in a concurrent fashion.
 */
public class MultiplyConcurrent2 {
	static Executor pool = Executors.newCachedThreadPool();
	
	/**
     * Function for multiplying integers in an array using concurrency.
     * 
     * @param arr An array containing integers.
     * @return Multiplication of all integers in the input array. 
	 * @throws InterruptedException 
     */ 
	public int multiplyConcurrent2(int[] arr) throws InterruptedException{
		int[] arrFirstHalf = Arrays.copyOfRange(arr, 0, arr.length/2);
		int[] arrSecondHalf = Arrays.copyOfRange(arr, arr.length/2, arr.length);
		
		CountDownLatch signal = new CountDownLatch(2);
		CountDownLatch completeSig = new CountDownLatch(1);
		IntermediateMultiply2 inter1 = new IntermediateMultiply2(1, arrFirstHalf,signal);
		pool.execute(inter1);
		
		IntermediateMultiply2 inter2 = new IntermediateMultiply2(2, arrSecondHalf,signal);
		pool.execute(inter2);
		
		CombineMultiply2 combineResults = new CombineMultiply2(signal,completeSig);
		pool.execute(combineResults);
		
		completeSig.await();
		
		return GlobalInfoMultiply2.result;
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
		MultiplyConcurrent2 obj = new MultiplyConcurrent2();
		int result = obj.multiplyConcurrent2(arr);
		System.out.println(result);
	}

}
