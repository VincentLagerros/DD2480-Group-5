package se.kth;

public class Main {
    public static void main(String[] args) {
        System.out.print("Hello world");
    }

    public boolean LIC5(double[] xCoordinates){
        for(int j = 1; j < xCoordinates.length; j++){
            if(xCoordinates[j] - xCoordinates[j-1] < 0){
                return true;
            }
        }
       return false;
    }
}
