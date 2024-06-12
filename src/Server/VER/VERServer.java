package Server.VER;

import Server.AdminImpl;
import Server.AdminInterface;
import Server.CustomerImpl;
import Server.CustomerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class VERServer {
    public void run() {
        try{
            Registry registry = LocateRegistry.createRegistry(999);
            AdminImpl admin = new AdminImpl("Verdun");
            AdminInterface adminStub = (AdminInterface) UnicastRemoteObject.exportObject(admin, 0);
            registry.rebind("rmi://localhost/Admin", adminStub);

            CustomerImpl customer = new CustomerImpl("Verdun");
            CustomerInterface customerStub = (CustomerInterface) UnicastRemoteObject.exportObject(customer, 0);
            registry.rebind("rmi://localhost/Customer", customerStub);
            System.out.println("Verdun server started.");


        }// end try
        catch (Exception exc) {
            System.out.println("Exception in BookingServer.main: " + exc);
            exc.printStackTrace();
        } // end catch
    } // end main
}
