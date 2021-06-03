import java.util.Scanner;

public class lab8 { 
    static final class Point { double lng, lat; } 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Point A = new Point(), B = new Point();
        
        A.lat = sc.nextDouble();
        A.lng = sc.nextDouble();
        B.lat = sc.nextDouble();
        B.lng = sc.nextDouble();
        
        long distance = geoDistance(B, A);
        System.out.println(distance);
    }
    // finding distance between two GPS points using heversine formula
    static long geoDistance(Point X, Point Y) {    
        double  angleA = Math.toRadians(X.lat),
                angleB = Math.toRadians(Y.lat),
                theta = Math.toRadians(Y.lat-X.lat),
                lambda = Math.toRadians(Y.lng-X.lng);
        // inside the square root
        double temp = Math.pow(Math.sin(theta / 2.0), 2) +
                    Math.cos(angleA) * Math.cos(angleB) *
                    Math.pow(Math.sin(lambda / 2.0), 2);

        double xx = 2 * Math.atan2(Math.sqrt(temp), Math.sqrt(1-temp));
        double yy = 2 * Math.asin(Math.sqrt(temp));

        System.out.println("atan2: "+ xx);
		System.out.println("asin: "+ yy);
		
        // scaling by earth radius and rounding to nearest 100
        return Math.round((6371 * (xx))/100)*100;
    }   
}