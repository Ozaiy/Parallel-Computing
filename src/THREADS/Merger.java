package THREADS;
import java.util.concurrent.*;

public class Merger {

    /**
     * Starts a thread (gets the minimum number from the subarray)
     * 
     * @param mergedArray is a part of the array
     * @return is the minimum number of the array part
     */
    public static int[] startThread(int[] a, int[] b) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Callable callable = () -> {
            // System.out.println("Starting thread..");

            // selection sort
            int[] mergedArray = new int[a.length + b.length];
            int i = a.length - 1, j = b.length - 1, k = mergedArray.length;

            while (k > 0)
                mergedArray[--k] = (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
            return mergedArray;
        };

        Future<int[]> future = executor.submit(callable);

        int[] sortedSubArray = new int[a.length - 1 + b.length - 1];

        try {
            sortedSubArray = future.get(); // Wait for the thread to complete
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        return sortedSubArray;
    }

}
