package com.eclipse.preprocess;
public class Average {

	//return average of all integers in arr
	public float average(int[] arr)
	{
		float result=0;
		for (int i = 0; i < arr.length; i++) {
			result = result + arr[i];
		}
		
		result = result/arr.length;
		ValidateFusion validator = new ValidateFusion(); 
		validator.validate(result, 0);
		return (result);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		Average obj = new Average();
		float result = obj.average(arr);
		System.out.println(result);
	}
}