import java.util.Arrays;
import java.util.Scanner;

// ? Find all cicular prime numbers in a given range 
public class lab11 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        
        // !! Not giving correct answer sometime. Pass test 0,7,8,9 only
        // e.g. 200, 300 would answer the same as 100
        PrimeNumberSieve sieve = new PrimeNumberSieve(x);
        System.out.println(sieve.getCirculerPrimes()); 

        // this solution passed all tests
        for (int i = 0; i < x; i++)
            if (Prime.circular(i))
                Prime.counter++;
        System.out.println(Prime.counter);
    }

}

class Prime {
    static int counter = 0;

    static boolean prime(int n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        int step = 6;
        for (int i = 5; (i * i) <= n; i = i + step) {
            if (n % i == 0)
                return false;
            if (n % (i + 2) == 0)
                return false;
        }
        return true;
    }

    static boolean circular(int num) {
        int l = Integer.toString(num).length(), t = num;
        while (prime(t)) {
            t = (int) ((Math.pow(10, l - 1)) * (t % 10)) + (t / 10);
            if (t == num)
                return true; // all permutations checked !!
        }
        return false;
    }
}



class PrimeNumberSieve {

    PrimeNumberSieve(int nr) {
        numsRange = nr;
        primesRange = (int) (nr * 0.75) + 1;
        sieve = new int[primesRange];
        circulerPrimes = 0;
        sieveOfEratosthenes();
        // Arrays.sort(sieve);
    }

    int getCirculerPrimes() {
        for (int i = 0; i < numsRange; i++)
            if (isCircularPrime(i)) {
                circulerPrimes++;
            }
        return circulerPrimes;
    }

    // private stuff
    private int lastIndex, circulerPrimes;
    private int numsRange, primesRange;
    int[] sieve;

    private boolean isCircularPrime(int n) {
        int t = n, len = 0;
        while (t > 0) { t /= 10; len++; }
        t = n;
        while (bSearch(t, sieve, 0, lastIndex)) {
            t = (int) ((Math.pow(10, len - 1)) * (t % 10)) + (t / 10);
            if (t == n)
                return true;
        }
        return false;
    }

    private final void sieveOfEratosthenes() {
        int i = 0, j = 0;
        int[] nums = new int[numsRange];

        for (; i < numsRange; i++)
            nums[i] = i + 2;
        for (i = 0; i < numsRange; i++) {
            if (nums[i] != -1) {
                for (j = 2 * nums[i] - 2; j < numsRange; j += nums[i]) {
                    nums[j] = -1;
                }
            }
        }
        for (i = 0, j = 0; i < numsRange && j < primesRange; i++) {
            if (nums[i] != -1) {
                sieve[j] = nums[i];
                j++;
            }
        }
        lastIndex = j;
    }

    private boolean bSearch(int n, int array[], int start, int end) {
        int middle = (start + end) / 2;
        if (start > end)
            return false;
        if (n == array[middle])
            return true;
        if (n > array[middle])
            return bSearch(n, array, middle + 1, end);
        else
            return bSearch(n, array, start, middle - 1);
    }

}