package Server.OUT;

import Server.AdminImpl;
import Server.AdminInterface;
import Server.CustomerImpl;
import Server.CustomerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class OUTServer {
    public void run() {
        try{
            Registry registry = LocateRegistry.createRegistry(999);
            AdminImpl admin = new AdminImpl("Outremont");
            AdminInterface adminStub = (AdminInterface) UnicastRemoteObject.exportObject(admin, 0);
            registry.rebind("rmi://localhost/Admin", adminStub);

            CustomerImpl customer = new CustomerImpl("Outremont");
            CustomerInterface customerStub = (CustomerInterface) UnicastRemoteObject.exportObject(customer, 0);
            registry.rebind("rmi://localhost/Customer", customerStub);
            System.out.println("Outremont server started.");


        }// end try
        catch (Exception exc) {
            System.out.println("Exception in BookingServer.main: " + exc);
            exc.printStackTrace();
        } // end catch
    } // end main
}
