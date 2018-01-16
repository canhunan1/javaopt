/**
 * Created by Jianan on 1/12/2018.
 */
import Matrix.Matrix;
import java.lang.Math;
import java.io.*;
public class Test {
    static final int NUMS = 1;
    static final double SEC = Math.pow(10, 9);

    public static void print_matrix(float[][] m) {
        int r, c;

        System.out.format("[ ");
        for (r = 0; r < m.length; r++) {
            System.out.format("[ ");
            for (c = 0; c < m[0].length; c++) {
                System.out.format("%f", m[r][c]);
                if (c != m[0].length - 1 ) {
                    System.out.format(", ");
                }
            }
            System.out.format(" ]\n");
        }
        System.out.format("]\n");
    }

    public static void main(String args[]){
        double[] res = new double[9];
        for(int i = 2; i <=10; i++){

            int size = (int)Math.pow(2, i);
            //System.out.println("running size " + size );
            res[i - 2] = measureTime(size);
            //System.out.println("Running Time is " + res[i-2]);
            //System.out.println();
        }

        String fileName = "java_test.txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i = 0; i < 9; i++){
                bufferedWriter.write(String.format("%.12f",res[i]/SEC));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
        }
        System.out.print("finished");
    }

    public static double  measureTime(int size){
        float a[][] = Matrix.rand_matrix(size,size);
        float b[][] = Matrix.rand_matrix(size,size);
        float c[][] = Matrix.create_matrix(size,size);
        double res = 0;
        
        double inner_time = 0;
        double single_time = 0;
        for(int i = 0; i < NUMS; i++){
            long start_time = System.nanoTime();
            Matrix.mult(c, a, b);
            long end_time = System.nanoTime();

            long ikj_start_time = System.nanoTime();
            Matrix.ikj_mult(c, a, b);
            long ikl_end_time = System.nanoTime();


            long para_start_time = System.nanoTime();
            Matrix.para_mult(c, a, b);
            long para_end_time = System.nanoTime();

            System.out.println("normal time is " + (double)(end_time - start_time)/SEC + 
                                "\tikj time is " + (double)(ikl_end_time - ikj_start_time)/SEC+
                                "\tpara time is " + (double)(para_end_time - para_start_time)/SEC);
            
        }
        //print_matrix(c);
        return 0.0;
    }

//    private static double aver(long[] nums){
//        double sum = 0;
//        for(int i = 0; i < nums.length; i++){
//            sum += nums[i];
//        }
//        return sum/nums.length;
//    }
}
