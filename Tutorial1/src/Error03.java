public class Error03 {
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		System.out.println("Printing the first " + n + " powers of 2.");
		int i = 0;
		int val = 1;
		while (i <= n) {
			System.out.println(i + " " + val);
			val = 2 * val;
			i++;
		}
	}
}