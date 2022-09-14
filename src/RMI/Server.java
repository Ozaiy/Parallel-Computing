package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements selectionSortInterface {
    public Server() {}

    final static String HOSTNAME = "95.179.133.188";
    final static int PORT = 1099;

    public String sayHello() {
        return "Hello, from " + HOSTNAME;
    }

    @Override
    public int[] selectSort(int[] subArray) throws RemoteException {

        for (int i = 0; i < subArray.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < subArray.length; j++) {
                if (subArray[j] < subArray[index]) {
                    index = j;// searching for lowest index
                }
            }
            int smallerNumber = subArray[index];
            subArray[index] = subArray[i];
            subArray[i] = smallerNumber;
        }

        return subArray;
    }

    public static void main(String args[]) throws RemoteException {

        register(1099);

    }

    public static void register (int port) throws RemoteException {

        Server serv = new Server();
        Registry registry = LocateRegistry.createRegistry(port);
        System.setProperty("java.rmi.server.hostname", HOSTNAME);
        registry.rebind ("serviceImpl", serv);
        try {
            Server obj = new Server();
            selectionSortInterface stub = (selectionSortInterface) UnicastRemoteObject.exportObject(obj, 1099);
            // Bind the remote object's stub in the registry
            registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.out.println(registry.lookup("SelectionSort"));
            System.err.println("Server ready");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
