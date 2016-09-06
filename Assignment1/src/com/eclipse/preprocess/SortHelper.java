package com.eclipse.preprocess;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class SortHelper extends RecursiveAction{

	private int[] mSource;
    private int mStart;
    private int mEnd;
    private int mLength;
    private int[] mDestination;
   
    public SortHelper(int[] src, int start, int end, int[] dst) {
    	mSource = src;
    	mStart = start;
        mEnd = end;
        mLength = end-start+1;
        mDestination = dst;
    }
    
    protected static int sThreshold = 2;
    @Override
    protected void compute() {
    	if(mLength < sThreshold) {
//    		base case: single element
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
    
	public static void sortForkAndJoin(int[] src) {
		int[] dst = new int[src.length];
	
		SortHelper sh = new SortHelper(src, 0, src.length-1, dst);
		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(sh);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {23,45,2312,14,5,24,64,235,86};
		
		sortForkAndJoin(arr);
		
		for (int i = 0; i < arr.length; i++) {
			System.out.format("%d,", arr[i]);
		}

	}

}
