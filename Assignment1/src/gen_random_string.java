import java.util.Random;

public class gen_random_string {

	public String generate()
	{
		Random rg = new Random();
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
		gen_random_string obj=new gen_random_string();
		String random_str=obj.generate();
		System.out.println(random_str);
	}

}
