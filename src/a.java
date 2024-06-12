
package Client;

import MovieTicketBooking.BookingSystem;
import MovieTicketBooking.BookingSystemHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;

public class AdminClient {

    public static void main(String[] args) {
//        try{
//
//            Properties properties = System.getProperties();
//            properties.put( "org.omg.CORBA.ORBInitialHost",
//                    "localhost" );
//            properties.put( "org.omg.CORBA.ORBInitialPort",
//                    "1051" );
//
//            // create and initialize ORB (object request broker)
//            ORB orb = ORB.init(args, properties);
//
//            //get the root naming context
//            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//            // resolve the object reference in naming
//            BookingSystem bookingSystemATW = BookingSystemHelper.narrow(ncRef.resolve_str("ATW"));
//            BookingSystem bookingSystemOUT = BookingSystemHelper.narrow(ncRef.resolve_str("OUT"));
//            BookingSystem bookingSystemVER = BookingSystemHelper.narrow(ncRef.resolve_str("VER"));
//
//
//            // Test Cases
//            System.out.println(bookingSystemATW.addMovieSlots("ATWM220223","Avatar",100));
//            System.out.println(bookingSystemATW.addMovieSlots("ATWM230223","Avatar",100));
//
//            System.out.println(bookingSystemOUT.addMovieSlots("OUTM220223","Titanic",100));
//            System.out.println(bookingSystemVER.addMovieSlots("VERM220223","Titanic",100));
//
//            System.out.println(bookingSystemATW.bookMovieTickets("ATWC2345","OUTM220223","Titanic",5));
//
//            System.out.println(bookingSystemATW.getBookingSchedule("ATWC2345"));
//            System.out.println(bookingSystemATW.exchangeTickets("ATWC2345","OUTM220223","VERM220223","Titanic","Titanic",5));
//            System.out.println(bookingSystemATW.getBookingSchedule("ATWC2345"));
//
//        }catch (Exception e){
//            System.out.println("ERROR : "+e);
//            e.printStackTrace();
//        }


        /////////////////////////////////////////////////////////////////

        try {

            Scanner sc = new Scanner(System.in);

            Properties properties = System.getProperties();
            properties.put( "org.omg.CORBA.ORBInitialHost",
                    "localhost" );
            properties.put( "org.omg.CORBA.ORBInitialPort",
                    "1051" );

            // create and initialize ORB (object request broker)
            ORB orb = ORB.init(args, properties);

            //get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            while (true) {
                System.out.println("Enter 1 for Admin or 2 for Customer");
                int role = Integer.parseInt(sc.nextLine());

                switch (role) {
                    // Admin Functions
                    case 1:

                        System.out.println("Enter Admin ID");
                        String adminID = sc.nextLine();
                        String areaCodeAdmin = adminID.substring(0, 3);

                        BookingSystem bookingSystemAdmin = BookingSystemHelper.narrow(ncRef.resolve_str(areaCodeAdmin));

                        System.out.println("Enter the operation you wanna do : 1 for addMovieSlots, 2 for removeMovieSlots, 3 for listMovieShowsAvailability");
                        int adminOperation = Integer.parseInt(sc.nextLine());

                        switch (adminOperation) {

                            // AddMovieShows
                            case 1:
                                System.out.println("Enter movieID");
                                String movieID = sc.nextLine();
                                System.out.println("Enter Movie Name");
                                String movieName = sc.nextLine();
                                System.out.println("Enter booking Capacity");
                                int capacity = Integer.parseInt(sc.nextLine());
                                String result = bookingSystemAdmin.addMovieSlots(movieID, movieName, capacity);
                                System.out.println(result);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeAdmin+"/"+adminID+".txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                break;

                            // RemoveMovieShows
                            case 2:
                                System.out.println("Enter movieID");
                                String movieID2 = sc.nextLine();
                                System.out.println("Enter Movie Name");
                                String movieName2 = sc.nextLine();
                                String result2 = bookingSystemAdmin.removeMovieSlots(movieID2, movieName2);
                                System.out.println(result2);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeAdmin+"/"+adminID+".txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result2);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                break;

                            // ListMovieShowsAvailability
                            case 3:
                                System.out.println("Enter Movie Name");
                                String movieName3 = sc.nextLine();
                                String result3 = bookingSystemAdmin.listMovieShowsAvailability(movieName3);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeAdmin+"/"+adminID+".txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result3);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                System.out.println(result3);
                        }
                        break;

                    // Customer Functions
                    case 2:
                        System.out.println("Enter Customer ID");
                        String customerID = sc.nextLine();
                        String areaCodeClient = customerID.substring(0, 3);

                        BookingSystem bookingSystemCustomer = BookingSystemHelper.narrow(ncRef.resolve_str(areaCodeClient));

                        System.out.println("Enter the operation you wanna do : 1 for bookMovieTickets, 2 for getBookingSchedule, 3 for cancelMovieTickets, 4 for exchangeMovieTickets");
                        int clientOperation = Integer.parseInt(sc.nextLine());

                        switch (clientOperation) {

                            // BookMovieTickets
                            case 1:

                                System.out.println("Enter movieID");
                                String movieID = sc.nextLine();
                                System.out.println("Enter Movie Name");
                                String movieName = sc.nextLine();
                                System.out.println("Enter number of tickets");
                                int numberOfTickets = Integer.parseInt(sc.nextLine());

                                String result = bookingSystemCustomer.bookMovieTickets(customerID, movieID, movieName, numberOfTickets);
                                System.out.println(result);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeClient+"/"+customerID+".txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                break;

                            // GetBookingSchedule
                            case 2:
                                String schedule = bookingSystemCustomer.getBookingSchedule(customerID);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeClient+"/"+customerID+".txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(schedule);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                System.out.println(schedule);
                                break;

                            // CancelMovieTickets
                            case 3:
                                System.out.println("Enter movieID");
                                String movieID2 = sc.nextLine();
                                System.out.println("Enter Movie Name");
                                String movieName2 = sc.nextLine();
                                System.out.println("Enter number of tickets");
                                int numberOfTickets2 = Integer.parseInt(sc.nextLine());

                                String result2 = bookingSystemCustomer.cancelMovieTickets(customerID, movieID2, movieName2, numberOfTickets2);
                                System.out.println(result2);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeClient+"/"+customerID+"1.txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result2);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                break;

                            // ExchangeTickets
                            case 4:
                                System.out.println("Enter old movieID");
                                String old_movieID = sc.nextLine();
                                System.out.println("Enter old Movie Name");
                                String old_movieName = sc.nextLine();
                                System.out.println("Enter new movieID");
                                String new_movieID = sc.nextLine();
                                System.out.println("Enter new Movie Name");
                                String new_movieName = sc.nextLine();
                                System.out.println("Enter number of tickets");
                                int numberOfTickets3 = Integer.parseInt(sc.nextLine());

                                String result3 = bookingSystemCustomer.exchangeTickets(customerID, old_movieID, new_movieID,old_movieName,new_movieName,numberOfTickets3);
                                System.out.println(result3);

                                try{
                                    FileWriter fileWriter = new FileWriter("src/Client/"+areaCodeClient+"/"+customerID+"1.txt", true);
                                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                    bufferedWriter.write(result2);
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                break;
                        }
                        break;
                }
            }

        } catch (InvalidName e) {
            throw new RuntimeException(e);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            throw new RuntimeException(e);
        } catch (CannotProceed e) {
            throw new RuntimeException(e);
        } catch (NotFound e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        ////////////////////////////////////////////////////////////////
    }
}