package com.eclipse.preprocess;
public class Multiply {

	//return multiplication of all integers in the array arr 
	public int multiply(int[] arr)
	{
		int result=1;
		for (int i = 0; i < arr.length; i++) {
			result = result * arr[i];
		}
		ValidateFusion validator = new ValidateFusion();
		validator.validate(result, 1);
		return result;
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,2,7};
		Multiply obj = new Multiply();
		int result = obj.multiply(arr);
		System.out.println(result);
	}
}
