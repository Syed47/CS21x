import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class lab3 {

    public static void main(String args[]) throws Exception {

        Scanner sc = new Scanner(System.in);
        long start = sc.nextLong();
        long end = sc.nextLong();
        long xth = sc.nextLong();

        ArrayList<Element> numbers = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            numbers.add(new Element(i, collatz(i)));
        }
        Collections.sort(numbers);
        System.out.println(numbers.get((int) xth - 1).value);

    }

    static class Element implements Comparable<Element> {
        long value;
        long length;

        Element(long v, long len) {
            value = v;
            length = len;
        }

        @Override
        public int compareTo(Element other) {
            if (this.length > other.length)
                return 1;
            if (this.length < other.length)
                return -1;
            return 0;
        }
    }

    static long collatz(long n) {
        if (n == 1)
            return 0;
        int len = 0;
        while (n != 1) {
            len++;
            n = (n % 2 == 0) ? (n / 2) : (n * 3) + 1;
        }
        return len;
    }
}

/*
 * 4: 2, 1 5: 16, 8, 4, 2, 1 6: 3, 10, 5, 16, 8, 4, 2, 1 7: 22, 11, 34, 17, 52,
 * 26, 13, 40, 20, 10, 5, 16, 8, 4, 2, 1 8: 4, 2, 1 9: 28, 14, 7, 22, 11, 40,
 * 20, 10, 16, 8, 4, 2,1
 * 
 * 
 * 12: 6, 3, 10, 5, 16, 8, 4, 2, 1 ---- (2) 13: 40, 20, 10, 5, 16, 8, 4, 2, 1
 * ---- (3) 14: 7, 22, 11, 34, 17, 52, 26, 13, 40, 20, 10, 5, 16, 8, 4, 2, 1
 * ---- (4) 15: 46, 23, 70, 35, 106, 53, 160, 80, 40, 20, 10, 5, 16, 8, 4, 2, 1
 * ---- (5) 16: 8, 4, 2, 1 ---- (1)
 * 
 * 
 */