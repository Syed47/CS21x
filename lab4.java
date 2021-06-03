import java.util.*;

public class lab4 {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        String[] aliceInput = sc.nextLine().split(" ");
        String[] bobInput = sc.nextLine().split(" ");

        int[] alice = new int[3];
        int[] bob = new int[2];

        for (int i = 0; i < 3; i++) {
            alice[i] = Integer.parseInt(aliceInput[i]);
        }

        for (int i = 0; i < 2; i++) {
            bob[i] = Integer.parseInt(bobInput[i]);
        }

        System.out.println(elgamalCode(alice, bob));
    }

    static long elgamalCode(int[] alice, int[] bob) {
        int p = alice[0], g = alice[1], gXmP = alice[2];
        int c1 = bob[0], c2 = bob[1];
        int x = 1;
        while (x < p && modPow(g, x, p) != gXmP)
            x++;
        return (modPow(c1, p - 1 - x, p) * c2) % p;
    }

    // helper methods
    public static long modPow(long number, long power, long modulus) {
        if (power == 0)
            return 1;
        else if (power % 2 == 0) {
            long halfpower = modPow(number, power / 2, modulus);
            return modMult(halfpower, halfpower, modulus);
        } else {
            long halfpower = modPow(number, power / 2, modulus);
            long firstbit = modMult(halfpower, halfpower, modulus);
            return modMult(firstbit, number, modulus);
        }
    }

    public static long modMult(long first, long second, long modulus) {
        if (second == 0)
            return 0;
        else if (second % 2 == 0) {
            long half = modMult(first, second / 2, modulus);
            return (half + half) % modulus;
        } else {
            long half = modMult(first, second / 2, modulus);
            return (half + half + first) % modulus;
        }
    }
}