package support_lib;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {
	static int[] wow;
	static int[] tmp2;
	 
    private static final ForkJoinPool threadPool = new ForkJoinPool();
    private static final int SIZE_THRESHOLD = 16;
 
    public static void sort(double[] a, int[] wows) {
    	wow = wows;
        sort(a, 0, a.length-1);
    }
 
    public static void sort(double[] a, int lo, int hi) {
        if (hi - lo < SIZE_THRESHOLD) {
            insertionsort(a, lo, hi);
            return;
        }
 
        double[] tmp = new double[a.length];
        tmp2 = new int[wow.length]; // dup
        threadPool.invoke(new SortTask(a, tmp, lo, hi));
    }
 
    /**
     * This class replaces the recursive function that was
     * previously here.
     */
    static class SortTask extends RecursiveAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		double[] a;
        double[] tmp;
        int lo, hi;
        public SortTask(double[] a, double[] tmp, int lo, int hi) {
            this.a = a;
            this.lo = lo;
            this.hi = hi;
            this.tmp = tmp;
        }
 
        @Override
        protected void compute() {
            if (hi - lo < SIZE_THRESHOLD) {
                insertionsort(a, lo, hi);
                return;
            }
 
            int m = (lo + hi) / 2;
            // the two recursive calls are replaced by a call to invokeAll
            invokeAll(new SortTask(a, tmp, lo, m), new SortTask(a, tmp, m+1, hi));
            merge(a, tmp, lo, m, hi);
        }
    }
 
    private static void merge(double[] a, double[] b, int lo, int m, int hi) {
        if (a[m] <= (a[m+1]))
            return;
 
        System.arraycopy(a, lo, b, lo, m-lo+1);
        System.arraycopy(wow, lo, tmp2, lo, m-lo+1); // dup
 
        int i = lo;
        int j = m+1;
        int k = lo;
 
        // copy back next-greatest element at each time
        while (k < j && j <= hi) {
            if (b[i] <= (a[j])) {
                a[k] = b[i];
                wow[k] = tmp2[i];
                i++;
                k++;
            } else {
                a[k] = a[j];
                wow[k] = wow[j];
                k++; j++;
            }
        }
 
        // copy back remaining elements of first half (if any)
        System.arraycopy(b, i, a, k, j-k);
        System.arraycopy(tmp2, i, wow, k, j-k);
    }
 
    private static void insertionsort(double[] a, int lo, int hi) {
        for (int i = lo+1; i <= hi; i++) {
            int j = i;
            double t = a[j];
            int t2 = wow[j];
            while (j > lo && t < (a[j - 1])) {
                a[j] = a[j - 1];
                wow[j] = wow[j-1];
                --j;
            }
            a[j] = t;
            wow[j] = t2;
        }
    }
}