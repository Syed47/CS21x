import java.util.Scanner;

class lab7 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // user input
		int palindromes = 0;
		for (int base = 2; base <= 10; base++) {
			if (palindrome(toBase(n, base))) palindromes++;
		}
		System.out.println(palindromes);
	}

	static boolean palindrome(String n) {
		int l = n.length();
		for (int i = 0; i < l; i++) {
			if (n.charAt(i) != n.charAt((l-1)-i)) {
				return false;
			}
		}
		return true;
	}

	static String toBase(int n, int base) {
		int power = 0;
		for (; power < 10; power++) {
			if ((int)Math.pow(base, power) > n) {
				break;
			}
		}
		power--;

		String result = "";
		int carry = 0;
		while (power != -1) {
			carry = (n / (int) Math.pow(base, power));
			n = n % (int) Math.pow(base, power);
			result += carry;
			power--;
		}
		return result;
	}
}
