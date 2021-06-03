import java.util.Scanner;
 
class lab5 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        String byteOrder = sc.next();

        if (byteOrder.equals("4321")) {
            System.out.println(Integer.reverseBytes(n));
            return;
        }

        long[] bitMask = {
            Long.parseLong("11111111000000000000000000000000", 2),
            Long.parseLong("00000000111111110000000000000000", 2),
            Long.parseLong("00000000000000001111111100000000", 2),
            Long.parseLong("00000000000000000000000011111111", 2)
        };

        int[] bytes = {
            (int)(bitMask[0] & n),
            (int)(bitMask[1] & n),
            (int)(bitMask[2] & n),
            (int)(bitMask[3] & n)
        };

        System.out.println(Integer.toBinaryString(n));


        // shuffleBytes(bytes, byteOrder);
        for (int i : bytes) {
            System.out.println(i + " --> " + Integer.toBinaryString(i));
        }

        int result = 0;
        for (int i = 0; i < 4; i++) {
            bytes[i] = bytes[i] << (i * 8);
            result |= bytes[i];
        }

        System.out.println(result);
        System.out.println(little2big(n, byteOrder));
        
    }

    static int[] shuffleBytes(int[] bytes, String encoding) {
        int[] sbytes = new int[4];
        for (int i = 0; i < 4; i++) {
            int index = Integer.parseInt(encoding.substring(i, i+1))-1;
            // int copy = bytes[i];
            // bytes[i] = bytes[index];
            // bytes[index] = copy; 
            sbytes[i] = bytes[index];
        }
        return sbytes;
    }

    static int little2big(int n, String order) {

        int[] bt = new int[4]; // 4 3 2 1
        for (int i = 0; i < 4; i++) {
            int index = Integer.parseInt(order.substring(i, i + 1)) - 1;
            bt[i] = index;
        }

        for (int x: bt) {
            System.out.println(x);
        }


        int num = (n & 0xff) << (bt[3]*8) | (n & 0xff00) << (bt[2]*8) | (n & 0xff0000) >> (bt[0]*8) | (n >> (bt[3]*8)) & 0xff;
        // return Integer.reverseBytes(num);
        return num;
    }
}