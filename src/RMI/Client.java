package RMI;

import THREADS.App;

import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class Client {
    private Client() {
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] nodes = { "95.179.133.188", "45.76.33.187", "45.76.34.205", "95.179.181.48"};

        int N_NODES = 2;
        int N_SIZE = 100000;

        int[] arr = App.readFile(N_SIZE);

        Instant start = Instant.now();
        int[][] array_of_subArrays = App.splits(N_NODES, arr);

        int[][] holder = new int[N_NODES][];

        // This loop gives each note a part of the array to sort

        for (int i = 0; i < N_NODES; i++) {
            int j = N_NODES;
            int[] out = callNode(array_of_subArrays[i], nodes[i]);
            while (out.length == 0) {
                out = callNode(array_of_subArrays[i], nodes[j]);
                j--;
                if (j == -1) {
                    break;
                }
            }
            holder[i] = out;
        }

        int[] finalArray = App.selectionSortMerge(holder);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("final Array size: " + finalArray.length + " file size: " + N_SIZE);
        System.out.println("Time elapsed: " + timeElapsed);
        System.out.println("is Sorted?: " + App.isSorted(finalArray));
        System.out.println("Nodes Used: " + N_NODES);
        Arrays.sort(arr);
        System.out.println("is the array same as original file? :" + Arrays.equals(finalArray, arr));

    }

    public static int[] callNode(int[] arr, String host) {
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            selectionSortInterface stub = (selectionSortInterface) registry.lookup("SelectionSort");
            String resp = stub.sayHello();
            int[] response = stub.selectSort(arr);
            System.out.println("response: " + resp);
            return response;
        } catch (Exception e) {
            System.err.println("Server not working.., no response from IP: " + host);
            return new int[0];
        }
    }

}
