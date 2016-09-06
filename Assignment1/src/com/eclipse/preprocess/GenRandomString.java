package com.eclipse.preprocess;

import java.util.Random;

public class GenRandomString {

	Random rg;
	public GenRandomString(){
		rg = new Random();
	}
	public String generate()
	{
		rg.setSeed(System.nanoTime());
	    int x;
	    char[] arr=new char[8];
	    for (int i = 0; i < arr.length; i++) {
	    	x=rg.nextInt(2);
	    	arr[i]=(char)(x+48);
		}
	    String output=new String(arr);
	    return output;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenRandomString obj=new GenRandomString();
		String randomStr=obj.generate();
		System.out.println(randomStr);
	}

}
