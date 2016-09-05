
public class Average {

	//return back average of all integers in arr
	public float average(int[] arr)
	{
		float result=0;
		for (int i = 0; i < arr.length; i++) {
			result = result + arr[i];
		}
		
		return (result/arr.length);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={25,50,100,7};
		Average obj = new Average();
		float result = obj.average(arr);
		System.out.println(result);
	}
}
//code ends here.