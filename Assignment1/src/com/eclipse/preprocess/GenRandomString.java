/**
 * CS 431  Assignment 1
 * GenRandomString.java
 * Purpose: Generate a random 8-bit string
 *
 * @version 1.1 2016
 * @author Ajinkya and Kushal
 */
package com.eclipse.preprocess;

import java.util.Random;

/**
 * Class to implement generation of a random 8-bit binary string
 */
public class GenRandomString {

	Random rg;
	
	/**
     * Random object memory allocation in constructor function 
     */
	public GenRandomString(){
		rg = new Random();
	}
	
	/**
     * Function for generating the random string. 
     */ 
	public String generate()
	{
		//set the seed using system time so that a different number is generated every time
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
	
	/**
     * Main function used for testing.
     * 
     * @param args Input from console. 
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenRandomString obj=new GenRandomString();
		String randomStr=obj.generate();
		System.out.println(randomStr);
	}

}
