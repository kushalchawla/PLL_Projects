/**
 * CS 431  Assignment 1
 * MainConcurrent2.java
 * Purpose: Perform complete task by taking inputs, pre-processing, fusing and output.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Class to maintain global variables.
 */
class GlobalInfo2 {
	public static int[] inputs = new int[10];
	public static Queue<int[]> pipeLine = new LinkedList<int[]>();
	public static Object enqueueLock = new Object();
	public static Semaphore available = new Semaphore(1);
}

/**
 * Class to implement thread for each Sensor
 */
class Sensor2 implements Runnable {
	int deviceNumber;
	
	/**
     * Constructor to set the thread number.
     * 
     * @param threadNumber the order number of the current thread.
     */
	public Sensor2(int threadNumber) {
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
			try {
				GlobalInfo2.available.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.format("%d got the lock.\n",deviceNumber);
			GlobalInfo.inputs[deviceNumber] = rawData;
			GlobalInfo.pipeLine.add(GlobalInfo.inputs);				
			
			GlobalInfo2.available.release();
			
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
class DataFusion2 implements Runnable {
	
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
		int sum = 0,mul = 0;
		float avg = 0;
		AddConcurrent2 adder = new AddConcurrent2();
		MultiplyConcurrent2 multiplier = new MultiplyConcurrent2();
		AverageConcurrent2 averager = new AverageConcurrent2();
		ValidateFusion validator = new ValidateFusion();		
		
		while(true) {
			
			//Checking if queue is empty or not
			if(GlobalInfo2.pipeLine.peek() != null)
			{
				snapshot = GlobalInfo2.pipeLine.poll();
				
				//printing snapshot
				System.out.format("The snapshot is: ");
				for (int i = 0; i < snapshot.length; i++) {
					System.out.format("%d,", snapshot[i]);
				}
				System.out.println();
				
				sortedSnapshot = Arrays.copyOf(snapshot, snapshot.length);
				
				SortHelper.sortForkAndJoin(sortedSnapshot);
				
				//printing sorted snapshot
				System.out.format("The sorted snapshot is: ");
				for (int i = 0; i < sortedSnapshot.length; i++) {
					System.out.format("%d,", sortedSnapshot[i]);
				}
				System.out.println();
				
				// Performing fusion of data and validating results
				try {
					sum = adder.addConcurrent2(sortedSnapshot);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}			 
				validator.validate(sum, 2);
				
				try {
					mul = multiplier.multiplyConcurrent2(sortedSnapshot);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				validator.validate(mul, 1);
				
				try {
					avg = averager.averageConcurrent2(sortedSnapshot);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				validator.validate(avg, 0);
				
				System.out.println();
				
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
public class MainConcurrent2 {
	static Executor pool = Executors.newCachedThreadPool();
	/**
     * Main function of used to create threads and start the process.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		System.out.println("main started...");
		for (int i = 0; i < 10; i++) {
			GlobalInfo2.inputs[i] = -1;
		}
		
		DataFusion2 processor = new DataFusion2();
		pool.execute(processor);	
		
		for (int threadNo = 0; threadNo < 10; threadNo++) {
			Sensor2 obj = new Sensor2(threadNo);
			pool.execute(obj);
		}

	}

}