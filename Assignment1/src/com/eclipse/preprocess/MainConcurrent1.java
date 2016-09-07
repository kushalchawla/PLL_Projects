/**
 * CS 431  Assignment 1
 * MainConcurrent1.java
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
	public static int completeThreads;
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
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}

/**
 * Class to implement thread for addition fusion process
 */
class fuseAdd1 implements Runnable {
	int[] sortedSnapshot;
	
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array
     */
	public fuseAdd1(int[] sortedSnapshot) {
		this.sortedSnapshot = sortedSnapshot;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseAdd");
		AddConcurrent1 adder = new AddConcurrent1();
		ValidateFusion validator = new ValidateFusion();
		int sum = adder.addConcurrent1(sortedSnapshot);			 
		validator.validate(sum, 2);
		GlobalInfo.completeThreads++;
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
class fuseMultiply1 implements Runnable {
	int[] sortedSnapshot;
	
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array
     */
	public fuseMultiply1(int[] sortedSnapshot) {
		this.sortedSnapshot = sortedSnapshot;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseMul");
		MultiplyConcurrent1 multiplier = new MultiplyConcurrent1();
		ValidateFusion validator = new ValidateFusion();
		int mul = multiplier.multiplyConcurrent1(sortedSnapshot);			 
		validator.validate(mul, 1);
		GlobalInfo.completeThreads++;
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
class fuseAverage1 implements Runnable {
	int[] sortedSnapshot;
	
	/**
     * Constructor to set the array.
     * 
     * @param sortedSnapshot the sorted integer array
     */
	public fuseAverage1(int[] sortedSnapshot) {
		this.sortedSnapshot = sortedSnapshot;
	}
	
	/**
     * Run function of the thread called within start function.
     * 
     */
	public void run() {
		System.out.println("fuseAvg");
		AverageConcurrent1 averager = new AverageConcurrent1();
		ValidateFusion validator = new ValidateFusion();
		float avg = averager.averageConcurrent1(sortedSnapshot);			 
		validator.validate(avg, 0);
		GlobalInfo.completeThreads++;
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
				
		
		while(true) {
			
			//Checking if queue is empty or not
			if(GlobalInfo.pipeLine.peek() != null)
			{
				snapshot = GlobalInfo.pipeLine.poll();
				
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
				
				GlobalInfo.completeThreads=0;
				
				
				// Performing fusion of data and validating results
				fuseAdd1 adder = new fuseAdd1(sortedSnapshot);
				Thread adderThread = new Thread(adder);
				adderThread.start();
				
				fuseMultiply1 multiplier = new fuseMultiply1(sortedSnapshot);
				Thread multiplierThread = new Thread(multiplier);
				multiplierThread.start();
				
				fuseAverage1 averager = new fuseAverage1(sortedSnapshot);
				Thread averagerThread = new Thread(averager);
				averagerThread.start();
				
				while(GlobalInfo.completeThreads < 3){
					//wait for all three fusions to take place.
				}
				System.out.println();
				
				try {
					Thread.sleep(3000);
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
public class MainConcurrent1 {

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