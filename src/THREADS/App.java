package THREADS;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class App {

    // public static int THREADS = 4;
    public static int[] N_THREADS = { 1 };
    public static int[] SIZES = { 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000, 550000 };

    public static void main(String args[]) throws IOException {

        // int ARRAY_SIZE = 350000;

        mainProgram();
       
        // arrayToFile(ARRAY_SIZE);

        // 2, 4, 8, 16, 32 THREADS USE 5 number of threads
        // not random numbers
        // generate a standard
        // generate numbers store in file

        // // // Split the array in multiple parts

        // // // Each thread should selection sort the sub array

    }

    public static void mainProgram() throws FileNotFoundException{
        for (int z = 0; z < N_THREADS.length; z++) {

            System.out.println("Amount of threads in use: " + N_THREADS[z]);
            

            long[] times = new long[SIZES.length];
            boolean[] sorted = new boolean[SIZES.length];

            for (int i = 0; i < SIZES.length; i++) {
                System.out.println("sorting... dataset Size: " + SIZES[i]);

                int[] arr1 = readFile(SIZES[i]);

                Instant start = Instant.now();

                int[][] split = splits(N_THREADS[z], arr1);

                int[][] holder = new int[N_THREADS[z]][];

                for (int j = 0; j < N_THREADS[z]; j++) {
                    int[] out = Runner.startThread(split[j]);
                    holder[j] = out;
                }

                int[] mergedArr = selectionSortMerge(holder);

                Instant finish = Instant.now();
                long timeElapsed = Duration.between(start, finish).toMillis();

                System.out.println("time elapsed: " + timeElapsed );
                times[i] = timeElapsed;
                sorted[i] = isSorted(mergedArr);
            }

            System.out.println("Amount of threads: " + N_THREADS[z]);
            System.out.println(Arrays.toString(times));
            System.out.println(Arrays.toString(sorted));

        }
    }

    public static void arrayToFile(int ARRAY_SIZE) throws IOException {
        Random random = new Random();
        int[] arr1 = random.ints(ARRAY_SIZE, 0, 10000).toArray();
        FileWriter writer = new FileWriter(ARRAY_SIZE + ".txt");
        for (int i = 0; i < arr1.length; i++) {
            writer.write(arr1[i] + " ");
        }
        writer.close();
    }

    public static int[] readFile(int ARRAY_SIZE) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(ARRAY_SIZE + ".txt"));

        int[] arr1 = new int[ARRAY_SIZE];

        int j = 0;
        while (scanner.hasNextInt()) {
            arr1[j++] = scanner.nextInt();
        }

        return arr1;

    }

    public static int[] selectionSortMerge(int[][] array) {

        int pos = 0;
        int totalLenght = 0;
        for (int i = 0; i < array.length; i++) {
            totalLenght += array[i].length;
        }

        int[] mergedArray = new int[totalLenght];

        for (int i = 0; i < array.length; i++) {
            for (int elemt : array[i]) {
                mergedArray[pos] = elemt;
                pos++;
            }
        }

        Runner.startThread(mergedArray);

        return mergedArray;
    }

    // merge the sorted sub arrays using selection sort
    // NOT USABLE FOR THE ASSIGNMENT YOU NEED TO USE SELECTION SORT
    public static int[] merge(int[] a, int[] b) {

        int[] mergedArray = new int[a.length + b.length];
        int i = a.length - 1, j = b.length - 1, k = mergedArray.length;

        while (k > 0)
            mergedArray[--k] = (j < 0 || (i >= 0 && a[i] >= b[j])) ? a[i--] : b[j--];
        return mergedArray;

        // // if (THREADS < 1) {
        // // mergedArr = holder[0];
        // // } else {

        // // mergedArr = ownMerge(split[0], split[1]);

        // // for (int i = 0; i < THREADS; i++) {
        // // mergedArr = merge(holder[i], mergedArr);
        // // }

        // // }
    }

    public static int[] ownMerge(int[] arr1, int[] arr2) {

        int[] arr3 = new int[arr1.length + arr2.length];

        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            // Check if current element of first
            // array is smaller than current element
            // of second array. If yes, store first
            // array element and increment first array
            // index. Otherwise do same with second array

            if (arr1[i] < arr2[j])
                arr3[k++] = arr1[i++];
            else
                arr3[k++] = arr2[j++];
        }

        // Store remaining elements of first array
        while (i < arr1.length)
            arr3[k++] = arr1[i++];

        // Store remaining elements of second array
        while (j < arr2.length)
            arr3[k++] = arr2[j++];

        return arr3;
    }

    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }

    public static int[][] splits(int THREADS, int[] arr) {
        if (arr.length == 0) {
            return new int[0][0];
        }

        int splitLength = (int) Math.ceil((double) arr.length / (double) THREADS);
        int[][] splits = new int[THREADS][];

        int j = 0;
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (k == splitLength) {
                k = 0;
                j++;
            }
            if (splits[j] == null) {
                int remainingNumbers = arr.length - i;
                splits[j] = new int[Math.min(remainingNumbers, splitLength)];
            }
            splits[j][k++] = arr[i];
        }
        return splits;
    };

}
