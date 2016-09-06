/**
 * CS 431  Assignment 1
 * Main.java
 * Purpose: Perform complete task by taking inputs, pre-processing, fusing and output.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to maintain global variables.
 */
class GlobalInfo {
	public static int[] inputs = new int[10];
	public static Queue<int[]> pipeLine = new LinkedList<int[]>();
	public static Object enqueueLock = new Object();
}

/**
 * Class to implement thread for each Sensor
 */
class Sensor implements Runnable {
	int deviceNumber;
	
	/**
     * Constructor to set the thread number.
     * 
     * @param threadNumber the order number of the current thread.
     */
	public Sensor(int threadNumber) {
		deviceNumber = threadNumber;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		GenRandomString generator = new GenRandomString();
		StringToInt converter = new StringToInt();
		
		int rawData;
		
		while(true) {
			
			rawData = converter.convert(generator.generate());
			
			//synchronization to make sure that queue is filled up in the correct order.
			synchronized(GlobalInfo.enqueueLock){
				System.out.format("%d got the lock.\n",deviceNumber);
				GlobalInfo.inputs[deviceNumber] = rawData;
				GlobalInfo.pipeLine.add(GlobalInfo.inputs);				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

/**
 * Class to implement thread for Fusion process
 */
class DataFusion implements Runnable {
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		boolean flag = true;
		
		//Testing if all the threads are initialized with some random value.
		while(flag) {
			flag = false;
			for (int i = 0; i < 10; i++) {
				if(GlobalInfo.inputs[i]==-1)
					flag = true;
			}
		}
		
		int[] snapshot = new int[10];
		int[] sortedSnapshot;
		int sum,mul;
		float avg;
		AddConcurrent1 adder = new AddConcurrent1();
		MultiplyConcurrent1 multiplier = new MultiplyConcurrent1();
		AverageConcurrent1 averager = new AverageConcurrent1();
		ValidateFusion validator = new ValidateFusion();		
		
		while(true) {
			
			//Checking if queue is empty or not
			if(GlobalInfo.pipeLine.peek() != null)
			{
				snapshot = GlobalInfo.pipeLine.poll();
				
				System.out.format("The snapshot is: ");
				for (int i = 0; i < snapshot.length; i++) {
					System.out.format("%d,", snapshot[i]);
				}
				System.out.println();
				
				sortedSnapshot = Arrays.copyOf(snapshot, snapshot.length);
				
				
				sum = adder.addConcurrent1(sortedSnapshot);			 
				validator.validate(sum, 2);
				
				mul = multiplier.multiplyConcurrent1(sortedSnapshot);
				validator.validate(mul, 1);
				
				avg = averager.averageConcurrent1(sortedSnapshot); 
				validator.validate(avg, 0);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}

/**
 * Main Class containing the main function.
 */
public class Main {

	/**
     * Main function of used to create threads and start the process.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		System.out.println("main started...");
		for (int i = 0; i < 10; i++) {
			GlobalInfo.inputs[i] = -1;
		}
		
		DataFusion processor = new DataFusion();
		Thread processThread = new Thread(processor);
		processThread.start();	
		
		for (int threadNo = 0; threadNo < 10; threadNo++) {
			Sensor obj = new Sensor(threadNo);
			Thread t = new Thread(obj);
			t.start();
		}

	}

}
