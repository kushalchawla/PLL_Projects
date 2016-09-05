package com.eclipse.preprocess;
import java.util.LinkedList;
import java.util.Queue;

class GlobalInfo {
	public static int[] inputs = new int[10];
	public static Queue<int[]> pipeLine = new LinkedList<int[]>();
	public static Object enqueueLock = new Object();
}

class Input implements Runnable {
	int deviceNumber;
	
	public Input(int threadNumber) {
		deviceNumber = threadNumber;
	}
	
	public void run() {
		GenRandomString generator = new GenRandomString();
		StringToInt converter = new StringToInt();
		
		int rawData;
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			rawData = converter.convert(generator.generate());
			synchronized(GlobalInfo.enqueueLock){
				GlobalInfo.inputs[deviceNumber] = rawData;
				GlobalInfo.pipeLine.add(GlobalInfo.inputs);
			}
		}
	}
}
class DataFusion implements Runnable {
	public void run() {
		
	}
}
public class Main {

	public static void main(String[] args) {
		

	}

}
