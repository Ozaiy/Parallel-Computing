package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface selectionSortInterface extends Remote {
    String sayHello() throws RemoteException;
    int[] selectSort(int[] subArray) throws RemoteException;
}
