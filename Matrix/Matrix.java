package Matrix;
import java.util.*;
import java.util.Random;




public class Matrix {
    private static final int BLOCK_SIZE = 4;
        private static final int num_thread = 4;

    public static float[][] rand_matrix(int rows, int cols) {
        Random rand = new Random();
        float c[][] = new float[rows][cols];
        for(int i = 0;i < rows;i++){
            for(int j = 0;j < cols;j++){
                c[i][j] = rand.nextFloat();
            }
        }
        return c;
    }
    public static float[][] create_matrix(int rows, int cols) {
        float c[][] = new float[rows][cols];
        return c;
    }

    public static float[][] mult(float c[][], float a[][], float b[][]){

        int n = a[0].length;
        int m = a.length;
        int p = b[0].length;


        for(int i = 0;i < m;i++){
            for(int j = 0;j < p;j++){
                for(int k = 0;k < n;k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }
    public static float[][] ikj_mult(float c[][], float a[][], float b[][]){

        int n = a[0].length;
        int m = a.length;
        int p = b[0].length;


        for (int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                //C[i][j] = 0;
                for(int j = 0; j < p; j++){
                    c[i][j] += a[i][k] * b[k][j];
                }
	        }
        } 
        return c;
    }


    public static float[][] block_mult(float c[][], float a[][], float b[][]){

        int n = a[0].length; // assume all the matrice are n*n
        int m = a.length;
        int p = b[0].length;

        for(int kk = 0; kk < n; kk += BLOCK_SIZE){
            for(int jj = 0; jj < n; jj += BLOCK_SIZE){
                for(int i = 0;i < m;i++){
                    for(int j = jj; j < jj + BLOCK_SIZE;j++){
                        float temp = 0;
                        for(int k = kk;k < kk + BLOCK_SIZE;k++){
                             temp += a[i][k] * b[k][j];
                        }
                        c[i][j] += temp;
                    }
                }
            }
        }


        return c;
    }
    public static void para_mult(float c[][], float a[][], float b[][]){
        for(int i = 0; i < num_thread; i++){
                WorkThread wt = new WorkThread(c, a, b, i);
                wt.start();
        }
    }

    public static class WorkThread implements Runnable{
        float c[][], a[][], b[][];
        int tid;
        WorkThread(float c[][], float a[][], float b[][], int tid){
            this.c = c;
            this.a = a;
            this.b = b;
            this.tid = tid;
        }
        @Override
        public void run(){
            int n = a[0].length;
            int m = a.length;
            int p = b[0].length;
            int mat_size = n;
            int start = tid * mat_size / 4;
            int end = (tid + 1) * mat_size / 4;

            for(int i = start; i < end; i++){
                for(int j = 0;j < p;j++){
                    for(int k = 0;k < n;k++){
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
    }

}
//public class Matrix {
//    public static float[][] rand_matrix(int rows, int cols) {
//        Random rand = new Random();
//        float c[][] = new float[rows][cols];
//        for(int i = 0;i < rows;i++){
//            for(int j = 0;j < cols;j++){
//                c[i][j] = rand.nextFloat();
//            }
//        }
//        return c;
//    }
//    public static float[][] create_matrix(int rows, int cols) {
//        float c[][] = new float[rows][cols];
//        return c;
//    }
//
//    public static float[][] mult(float c[][], float a[][], float b[][]){
//
//
//
//        int times = 0;
//        long inner_start = 0;
//        long inner_end = 0;
//        long time_e = 0;
//        long instruction_start = 0;
//        long instruction_end = 0;
//        long total_start = 0;
//        long total_end = 0;
//        long sum = 0;
//        total_start = System.nanoTime();
//        int n = a[0].length;
//        int m = a.length;
//        int p = b[0].length;
//        for(int i = 0;i < m;i++){
//            for(int j = 0;j < p;j++){
//                inner_start = System.nanoTime();
//                for(int k = 0;k < n;k++){
//                    instruction_start = System.nanoTime();
//                    c[i][j] += a[i][k] * b[k][j];
//                    instruction_end = System.nanoTime();
//                    sum += (instruction_end - instruction_start);
//                    times++;
//                }
//                inner_end = System.nanoTime();
//                time_e += inner_end - inner_start;
//            }
//        }
//        total_end = System.nanoTime();
//        //double aver_inst = aver(sum);
//        double aver_inst = sum/times;
//        System.out.println("the aver running time of each instruction is " + aver_inst);
//        System.out.println("m is " + m + ", p is " + p + ", n is " + n + ", aver inner running time is " + time_e/(m*p));
//        System.out.println("Running times " + times);
//        System.out.println("Total running time is " + (total_end - total_start)/1000 + "us");
//        return c;
//    }
//
//    private static double aver(List<Long> nums){
//        double sum = 0;
//        for(int i = 0; i < nums.size(); i++){
//            sum += nums.get(i);
//        }
//        return sum/nums.size();
//    }
//}
