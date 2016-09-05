package com.eclipse.preprocess;
public class StringToInt {

	//function to convert a binary string to the corresponding integer value
	public int convert(String str) {
		int val = Integer.parseInt(str, 2);
		return val;
	}
	public static void main(String[] args) {		
		String bin = "00010111";
		StringToInt obj = new StringToInt();
		int res = obj.convert(bin);
		System.out.println(res); 	
	}

}