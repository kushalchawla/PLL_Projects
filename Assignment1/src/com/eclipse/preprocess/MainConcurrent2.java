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
import java.util.concurrent.CountDownLatch;
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
	public static Executor pool = Executors.newCachedThreadPool();
}

/**
 * Class to implement thread for each Sensor
 */
class Sensor2 implements Runnable {
	int deviceNumber;
	CountDownLatch startSignal;
	
	/**
     * Constructor to set the thread number.
     * 
     * @param deviceNumber the order number of the current thread.
     * @param startSignal Signal to start the processing.
     */
	public Sensor2(int deviceNumber, CountDownLatch startSignal) {
		this.deviceNumber = deviceNumber;
		this.startSignal = startSignal;
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
			if(GlobalInfo2.inputs[deviceNumber] == -1){
				startSignal.countDown();
			}
			GlobalInfo2.inputs[deviceNumber] = rawData;
			GlobalInfo2.pipeLine.add(GlobalInfo2.inputs);				
			
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
 * Class to implement thread for addition fusion process
 */
class fuseAdd2 implements Runnable {
	int[] sortedSnapshot;
	CountDownLatch workDone;
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array.
     * @param workDone Latch to indicate work is done.
     */
	public fuseAdd2(int[] sortedSnapshot, CountDownLatch workDone) {
		this.sortedSnapshot = sortedSnapshot;
		this.workDone = workDone;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseAdd");
		AddConcurrent2 adder = new AddConcurrent2();
		ValidateFusion validator = new ValidateFusion();
		int sum = 0;
		try {
			sum = adder.addConcurrent2(sortedSnapshot);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			 
		validator.validate(sum, 2);
		workDone.countDown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Class to implement thread for multiplier fusion process
 */
class fuseMultiply2 implements Runnable {
	int[] sortedSnapshot;
	CountDownLatch workDone;
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array
     * @param workDone To indicate work is done
     */
	public fuseMultiply2(int[] sortedSnapshot, CountDownLatch workDone) {
		this.sortedSnapshot = sortedSnapshot;
		this.workDone = workDone;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseMul");
		MultiplyConcurrent2 multiplier = new MultiplyConcurrent2();
		ValidateFusion validator = new ValidateFusion();
		int mul = 0;
		try {
			mul = multiplier.multiplyConcurrent2(sortedSnapshot);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			 
		validator.validate(mul, 1);
		workDone.countDown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


/**
 * Class to implement thread for average fusion process
 */
class fuseAverage2 implements Runnable {
	int[] sortedSnapshot;
	CountDownLatch workDone;
	
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array.
     * @param workDone To indicate the work is done.
     */
	public fuseAverage2(int[] sortedSnapshot, CountDownLatch workDone) {
		this.sortedSnapshot = sortedSnapshot;
		this.workDone = workDone;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseAvg");
		AverageConcurrent2 averager = new AverageConcurrent2();
		ValidateFusion validator = new ValidateFusion();
		float avg = 0;
		try {
			avg = averager.averageConcurrent2(sortedSnapshot);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			 
		validator.validate(avg, 0);
		workDone.countDown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}




/**
 * Class to implement thread for Fusion process
 */
class DataFusion2 implements Runnable {
	CountDownLatch startSignal;
	
	/**
	 * A constructor to set startSignal
	 * @param startSignal Signal to start the processing
	 */
	public DataFusion2(CountDownLatch startSignal){
		this.startSignal = startSignal;
	}
	
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		//Waiting for all the threads to get initialized with some random value.
		try {
			startSignal.await();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		int[] snapshot = new int[10];
		int[] sortedSnapshot;
				
		
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
				
				CountDownLatch workDone = new CountDownLatch(3);
				
				// Performing fusion of data and validating results
				fuseAdd2 adder = new fuseAdd2(sortedSnapshot,workDone);
				GlobalInfo2.pool.execute(adder);
			
				
				fuseMultiply2 multiplier = new fuseMultiply2(sortedSnapshot,workDone);
				GlobalInfo2.pool.execute(multiplier);
				
				fuseAverage2 averager = new fuseAverage2(sortedSnapshot,workDone);
				GlobalInfo2.pool.execute(averager);
				
				
				System.out.println();
				
				try {
					workDone.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
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
	
	/**
     * Main function of used to create threads and start the process.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		System.out.println("main started...");
		
		CountDownLatch startSignal = new CountDownLatch(10);
		
		for (int i = 0; i < 10; i++) {
			GlobalInfo2.inputs[i] = -1;
		}
		
		DataFusion2 processor = new DataFusion2(startSignal);
		GlobalInfo2.pool.execute(processor);	
		
		for (int threadNo = 0; threadNo < 10; threadNo++) {
			Sensor2 obj = new Sensor2(threadNo, startSignal);
			GlobalInfo2.pool.execute(obj);
		}

	}

}