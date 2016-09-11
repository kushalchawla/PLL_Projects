/**
 * CS 431  Assignment 1
 * AddConcurrent1.java
 * Purpose: Add integers in an array using Thread/Runnable for concurrency.
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class to implement sorting using Fork-and-Join
 *
 */
public class SortHelper extends RecursiveAction{

	private int[] mSource;
    private int mStart;
    private int mEnd;
    private int mLength;
    private int[] mDestination;
   
    /**
     * class constructor
     * @param src array to be sorted
     * @param start starting index of array
     * @param end ending index of array
     * @param dst array to store the result
     */
    public SortHelper(int[] src, int start, int end, int[] dst) {
    	mSource = src;
    	mStart = start;
        mEnd = end;
        mLength = end-start+1;
        mDestination = dst;
    }
    
    protected static int sThreshold = 2;
    /* (non-Javadoc)
     * @see java.util.concurrent.RecursiveAction#compute()
     * overrided function- to implement merge sort
     */
    @Override
    protected void compute() {
    	if(mLength < sThreshold) {
    	//	base case: single element
    		return;
    	}
    	
    	int mid = (mStart + mEnd) / 2;
    	
    	invokeAll(new SortHelper(mSource, mStart, mid, mDestination),
                new SortHelper(mSource, mid+1, mEnd, mDestination));
    	
    	int i, j, k;
    	
    	i = mStart;
    	j = mid + 1;
    	k = mStart;
    	
    	while(i <= mid && j <= mEnd) {
    		if(mSource[i] < mSource[j])
    			mDestination[k++] = mSource[i++];
    		else
    			mDestination[k++] = mSource[j++];
    	}
    	
    	while(i <= mid)
    		mDestination[k++] = mSource[i++];
    	while(j <= mEnd)
    		mDestination[k++] = mSource[j++];
    	
    	for (int k2 = mStart; k2 <= mEnd; k2++) {
			mSource[k2] = mDestination[k2]; 
		}
    	
    }
    
	/**
	 * @param src Array to be sorted
	 */
	public static void sortForkAndJoin(int[] src) {
		int[] dst = new int[src.length];
	
		SortHelper sh = new SortHelper(src, 0, src.length-1, dst);
		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(sh);
		
	}
	/** Main function used for testing
	 * @param args input from console
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {23,45,2312,14,5,24,64,235,86};
		
		System.out.format("Initial array: ");
		for (int i = 0; i < arr.length; i++) {
			System.out.format("%d,", arr[i]);
		}
		System.out.println();
		
		sortForkAndJoin(arr);
				
		System.out.format("Sorted array: ");
		for (int i = 0; i < arr.length; i++) {
			System.out.format("%d,", arr[i]);
		}
		System.out.println();

	}

}
