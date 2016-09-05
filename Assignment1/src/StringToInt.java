public class StringToInt {

	public int convert(String str) {
		int val = Integer.parseInt(str, 2);
		return val;
	}
	public static void main(String[] args) {
		
		String bin = "00020111";
		StringToInt obj = new StringToInt();
		int res = obj.convert(bin);
		System.out.println(res);
	
	}

}
