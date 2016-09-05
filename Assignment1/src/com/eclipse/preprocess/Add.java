package com.eclipse.preprocess;

public class Add {
	//return addition of all integers in the array arr 
		public int add(int[] arr)
		{
			int result=0;
			for (int i = 0; i < arr.length; i++) {
				result = result + arr[i];
			}
			ValidateFusion validator = new ValidateFusion(); 
			validator.validate(result, 2);
			return result;
		}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			int[] arr={25,50,100,7};
			Add obj = new Add();
			int result = obj.add(arr);
			System.out.println(result);
		}
}
