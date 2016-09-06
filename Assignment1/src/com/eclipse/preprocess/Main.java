package com.eclipse.preprocess;
import java.util.LinkedList;
import java.util.Queue;

class GlobalInfo {
	public static int[] inputs = new int[10];
	public static Queue<int[]> pipeLine = new LinkedList<int[]>();
	public static Object enqueueLock = new Object();
}

class Sensor implements Runnable {
	int deviceNumber;
	
	public Sensor(int threadNumber) {
		deviceNumber = threadNumber;
	}
	
	public void run() {
		GenRandomString generator = new GenRandomString();
		StringToInt converter = new StringToInt();
		
		int rawData;
		
		while(true) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			rawData = converter.convert(generator.generate());
			
			synchronized(GlobalInfo.enqueueLock){
				System.out.format("%d ko Lock milaa\n",deviceNumber);
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
class DataFusion implements Runnable {
	public void run() {
		boolean flag = true;
		while(flag) {
			flag = false;
			for (int i = 0; i < 10; i++) {
				if(GlobalInfo.inputs[i]==-1)
					flag = true;
			}
		}
		
		int[] snapshot = new int[10];
		Add adder = new Add();
		Multiply multiplier = new Multiply();
		Average averager = new Average();
		
		while(true) {
			if(GlobalInfo.pipeLine.peek() != null)
			{
				snapshot = GlobalInfo.pipeLine.poll();
				
				System.out.format("The snapshot is: ");
				for (int i = 0; i < snapshot.length; i++) {
					System.out.format("%d,", snapshot[i]);
				}
				System.out.println();
				
				adder.add(snapshot);
				multiplier.multiply(snapshot);
				averager.average(snapshot);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
public class Main {

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
