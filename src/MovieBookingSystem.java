import Client.Admin;
import Client.Customer;
import Log.Log;
import Server.ATW.ATWServer;
import Server.OUT.OUTServer;
import Server.VER.VERServer;

import java.util.Objects;
import java.util.Scanner;

public class MovieBookingSystem {
    private static Log LogObject;
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your valid ID:");
        String inputID = in.next().toUpperCase();
        String prefix = "";
        char role = 0;
        if(inputID.length() > 4){
            role = inputID.charAt(3);
            prefix = inputID.substring(0, 3);
        }

        LogObject = new Log(
                "/Users/namratabrahmbhatt/Desktop/DistributedMovieTicketBookingSystem/src/"
                        + prefix + ".txt");

        LogObject.logger.info(inputID + " has logged in the " + prefix + " Server");



        switch (prefix) {
            case "ATW" -> {
                ATWServer atwServer = new ATWServer();
                atwServer.run();
            }
            case "OUT" -> {
                OUTServer outServer = new OUTServer();
                outServer.run();      
            }
            case "VER" -> {
                VERServer verServer = new VERServer();
                verServer.run();
            }
            default -> System.out.println("Wrong ID entered!!");
        }
        if (Objects.equals(role, 'A')) {
            Admin admin = new Admin();
            admin.run(inputID, LogObject);
        } else if (Objects.equals(role, 'C')) {
            Customer customer = new Customer();
            customer.run(inputID, LogObject);
        }

    }
}
