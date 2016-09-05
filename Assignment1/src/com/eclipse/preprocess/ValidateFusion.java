package com.eclipse.preprocess;
public class ValidateFusion {

	float AVG; 
	int MUL,ADD;
	
	public ValidateFusion() {
		AVG = 100;
		MUL = 100000;
		ADD = 10000;
	}
	public void setThresholds(float NEW_AVG, int NEW_MUL, int NEW_ADD) {
		AVG = NEW_AVG;
		MUL = NEW_MUL;
		ADD = NEW_ADD;
	}
	public void validate( float val , int flag ) {
		
		switch(flag) {
		
		case 0: if( val > AVG )
					System.out.println("state detected from AVG");
				else
					System.out.println("state not detected from AVG");
				break;
		
		case 1: if( (int)(val) > MUL)
					System.out.println("state detected from MUL");
				else
					System.out.println("state not detected from MUL");
				break;
		
		case 2: if( (int)(val) > ADD) 
					System.out.println("state detected from ADD");
				else
					System.out.println("state not detected from ADD");
				break;
		
		default: System.out.println("Invalid operation selected :/");
		
		}
	}
	public static void main(String[] args) {
		
		ValidateFusion obj = new ValidateFusion();
		obj.validate(10000, 0);
		obj.validate(10000, 1);
		obj.validate(10000, 2);
		obj.validate(10000, 3);
	}
		
}
