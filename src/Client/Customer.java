package Client;

import Log.Log;
import Server.AdminInterface;
import Server.CustomerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Customer {
    String movieName, movieID;
    int capacity;
    int choice;
    String user_id;

        public void run(String customerID, Log LogObject) {
        try {
            Scanner sc = new Scanner(System.in);
            Registry registry = LocateRegistry.getRegistry(999);
            AdminInterface adminImpl = (AdminInterface) registry.lookup("rmi://localhost/Admin");
            CustomerInterface customerImpl = (CustomerInterface) registry.lookup("rmi://localhost/Customer");

            while (true) {
                System.out.println("****** Select the operations you want to perform from the options given below:: ******");
                System.out.println("1. Book movie tickets.");
                System.out.println("2. List your booked movie tickets.");
                System.out.println("3. Cancel your movie tickets.");
                System.out.println("4. Exit.");

                choice = Integer.parseInt(sc.nextLine()) ;
                switch (choice) {
                    case 1 -> {
                        LogObject.logger.info("The Customer " + customerID + " attempting to book Movie Slots");
                        System.out.println("Enter movie name you want to add from the option.");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        String movie_shows = adminImpl.listMovieShowsAvailability(movieName);
                        if (movie_shows.isEmpty()) {
                            System.out.println("Sorry there is no show available for " + movieName);
                        } else {
                            System.out.println("Below are the details for the movie:: " + movieName);
                            System.out.println(movie_shows);
                        }
                        System.out.println();
                        System.out.println("Enter the movieId you want to book the tickets for::");
                        movieID = (sc.nextLine()).toUpperCase();
                        System.out.println();
                        System.out.println("Enter the number of tickets you want to book for the movie: " + movieName + " with movieID " + movieID);
                        capacity = Integer.parseInt(sc.nextLine());
                        String data = customerImpl.bookMovieTickets(customerID, movieID, movieName, capacity);
                        System.out.println(data);
                        LogObject.logger.info("The Customer " + customerID + " booked movie slots successfully.");
                    }
                    case 2 -> {
                        System.out.println();
                        System.out.println("Enter CustomerID: ");
                        String userIDBooking = (sc.nextLine()).toUpperCase();
                        while (userIDBooking.isBlank() | userIDBooking.length() != 8) {
                            System.out.println("Please enter valid UserId !!");
                            userIDBooking = (sc.nextLine()).toUpperCase();
                        }
                        HashMap<String, Integer> booking_schedule = customerImpl.getBookingSchedule(userIDBooking);
                        if (booking_schedule.isEmpty()) {
                            System.out.println("There is no booked movie tickets found with the ID - " + userIDBooking);
                        } else {
                            System.out.println("Here is your booking schedule..!!");
                            System.out.println(booking_schedule);
                        }
                    }
                    case 3 -> {
                        LogObject.logger.info("The Customer " + customerID + " attempting to cancel movie booking slots");
                        System.out.println();
                        System.out.println("Enter CustomerID: ");
                        String userIDCancel = (sc.nextLine()).toUpperCase();
                        while (userIDCancel.isBlank() | userIDCancel.length() != 8) {
                            System.out.println("Please enter valid CustomerID::");
                            userIDCancel = (sc.nextLine()).toUpperCase();
                        }
                        while (!Objects.equals(customerID, userIDCancel)) {
                            System.out.println("Please enter valid CustomerID::");
                            userIDCancel = (sc.nextLine()).toUpperCase();
                        }
                        System.out.println("Enter movie name you want to cancel tickets for:.");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        HashMap<String, Integer> movieBooking = customerImpl.getBookingSchedule(customerID);
                        if (movieBooking.isEmpty()) {
                            System.out.println("There is no booked movie tickets found with the ID -" + customerID);
                            break;
                        } else {
                            System.out.println();
                            movieBooking.get(userIDCancel);
                            System.out.println("Here is the booked shows with the CustomerID - " + customerID);
                            for (String innerKey : movieBooking.keySet()) {
                                System.out.println("Inner Key: " + innerKey);
                                String[] splitMovie = innerKey.split("-");
                                if (Objects.equals(splitMovie[0], movieName)) {
                                    System.out.println(splitMovie[1] + "-" + movieBooking.get(innerKey));
                                } else {
                                    System.out.println();
                                    System.out.println("No tickets found for " + movieName);
                                }
                            }
                        }
                        System.out.println();
                        System.out.println("Enter the movieID you want to cancel the tickets for.");
                        movieID = (sc.nextLine()).toUpperCase();
                        System.out.println();
                        System.out.println("Please enter number of tickets for the movie " + movieName + " with the movieID " + movieID);
                        capacity = Integer.parseInt(sc.nextLine());
                        String newBookings = customerImpl.cancelMovieTickets(customerID, movieID, movieName, capacity);
                        System.out.println(newBookings);
                        LogObject.logger.info("The Customer " + customerID + " cancelled booking slots successfully.");
                    }
                    case 4 -> System.exit(0);
                    default -> System.out.println("Invalid input. Please enter an integer from 1 to 4.");
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred.");
            e.printStackTrace();
        }
    }
}
