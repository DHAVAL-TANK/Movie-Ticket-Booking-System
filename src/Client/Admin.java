package Client;

import Log.Log;
import Server.AdminInterface;
import Server.CustomerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;




public class Admin {
    String movieName, movieID;
    int capacity;

    public void run(String adminID, Log LogObject){
        try {
            Registry registry = LocateRegistry.getRegistry(999);
            AdminInterface adminImpl = (AdminInterface) registry.lookup("rmi://localhost/Admin");
            CustomerInterface customerImpl = (CustomerInterface) registry.lookup("rmi://localhost/Customer");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("****** Select which Admin operation you want to perform ******");
                System.out.println("1. Add Movie Slots.");
                System.out.println("2. Remove Movie Slots.");
                System.out.println("3. List Movie Shows Availability.");
                System.out.println("4. Book your own movie tickets.");
                System.out.println("5. List your booked movie tickets.");
                System.out.println("6. Cancel your movie tickets.");
                System.out.println("7. Exit.");

                int choice;
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        LogObject.logger.info("The Admin " + adminID + " attempting to Add Movie Slots");
                        System.out.println("Choose a movie name you wanna add slots to::");
                        System.out.println("AVENGERS \t AVATAR \t TITANIC");
                        System.out.println("Enter the movie name::");
                        movieName = (sc.nextLine()).toUpperCase();
                        System.out.println("Enter the movieID: ");
                        movieID = (sc.nextLine()).toUpperCase();
                        while(movieID.isBlank() | movieID.length()<11 | movieName.isBlank()){
                            System.out.println("Please enter valid movie ID!");
                            System.out.println("Enter the movieID: ");
                            movieID = (sc.nextLine()).toUpperCase();
                        }
                        System.out.println("Enter the number of slots you want to add for the Movie: " + movieName + " with the MovieId: "+ movieID);
                        capacity = Integer.parseInt(sc.nextLine());
                        String response = adminImpl.addMovieSlots(movieID, movieName, capacity);
                        System.out.println(response);

//                        LogObject.logger.info("The Admin " + adminID + " attempting to Add Movie Slots");
                        LogObject.logger.info("The Admin " + adminID + " successfully added new movie slots");
                    }
                    case 2 -> {
                        LogObject.logger.info("The Admin " + adminID + " is attempting to remove movie slots");
                        System.out.println("Enter movie name for which you want to remove movie slots.");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        System.out.println();
                        System.out.println("Enter movieId for the movie - " + movieName);
                        movieID = (sc.nextLine()).toUpperCase();
                        while(movieID.isBlank() | movieID.length() < 11 | movieName.isBlank()){
                            System.out.println("Please enter valid movie ID");
                            System.out.println("Enter movieID for the movie - " + movieName);
                            movieID = (sc.nextLine()).toUpperCase();
                        }
                        String data = adminImpl.removeMovieSlots(movieID, movieName);
                        System.out.println(data);
                        LogObject.logger.info("The Admin " + adminID + " removed slots successfully");
                    }
                    case 3 -> {
                        System.out.println("Enter movie name you want to see the availabilities for:");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        while(movieName.isBlank()){
                            System.out.println("Please enter valid movie name from the below:");
                            System.out.println("AVATAR \t AVENGERS \t TITANIC");
                            movieName = (sc.nextLine()).toUpperCase();
                        }
                        String shows = adminImpl.listMovieShowsAvailability(movieName);
                        if(shows.isEmpty()){
                            System.out.println();
                            System.out.println("There are no shows available for:: " + movieName);
                            System.out.println();
                        }
                        else{
                            System.out.println();
                            System.out.println("Here is the schedule for the movie: "+ movieName);
                            System.out.println(shows);
                        }
                    }
                    case 4 -> {
                        System.out.println("Enter CustomerID: ");
                        String bookingID = (sc.nextLine()).toUpperCase();
                        while(bookingID.isBlank() | bookingID.length()!=8){
                            System.out.println("Please enter valid CustomerID !!");
                            bookingID = (sc.nextLine()).toUpperCase();
                        }
                        System.out.println("Enter movie name you want to add from the option.");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        String movieShows = adminImpl.listMovieShowsAvailability(movieName);
//                        HashMap<String, Integer> list_movie_shows = adminImpl.listMovieShowsAvailability(movieName);
                        if(movieShows.isEmpty()){
                            System.out.println("Sorry there is no show available for " + movieName);
                        }
                        else{
                            System.out.println("Here is the movie shows available for "+movieName);
                            System.out.println(movieShows);
                        }
                        System.out.println();
                        System.out.println("Enter the movieID you want to book::");
                        movieID = (sc.nextLine()).toUpperCase();

                        System.out.println();
                        System.out.println("Please enter number of tickets for the movie " + movieName + "-" +movieID);
                        capacity = Integer.parseInt(sc.nextLine());
                        // TODO
                        String reply = customerImpl.bookMovieTickets(bookingID, movieID, movieName, capacity);
                        System.out.println(reply);
                    }
                    case 5 -> {
                        System.out.println();
                        System.out.println("Enter CustomerID: ");
                        String userIDBooking = (sc.nextLine()).toUpperCase();
                        while (userIDBooking.isBlank() | userIDBooking.length() > 8) {
                            System.out.println("Please enter valid UserId !!");
                            userIDBooking = (sc.nextLine()).toUpperCase();
                        }
                        // TODO
                        HashMap<String, Integer> booking_schedule = customerImpl.getBookingSchedule(userIDBooking);
                        if (booking_schedule.isEmpty()) {
                            System.out.println("There is no booked movie tickets found with the ID - " + userIDBooking);
                        } else {
                            System.out.println("Below is your movie booking schedule::");
                            System.out.println(booking_schedule);
                            LogObject.logger.info("The Admin " + adminID + " displayed the Record");
                        }
                    }
                    case 6 -> {
                        LogObject.logger.info("The Manager " + adminID + " attempting to retrieve Record count over all the servers");
                        System.out.println();
                        System.out.println("Enter CustomerID: ");
                        String userIDCancel = (sc.nextLine()).toUpperCase();
                        while(userIDCancel.isBlank() | userIDCancel.length() > 8){
                            System.out.println("Please enter valid CustomerID !!");
                            userIDCancel = (sc.nextLine()).toUpperCase();
                        }
                        System.out.println("-------"  +adminID + "<----->" + userIDCancel);
                        while(Objects.equals(adminID, userIDCancel)){
                            System.out.println("Customer ID didn't match with the login ID !!");
                            userIDCancel = (sc.nextLine()).toUpperCase();
                        }
                        System.out.println("Enter movie name you want to cancel from the option.");
                        System.out.println("AVATAR \t AVENGERS \t TITANIC");
                        movieName = (sc.nextLine()).toUpperCase();
                        HashMap<String, Integer> movieBookings = customerImpl.getBookingSchedule(userIDCancel);
                        if(movieBookings.isEmpty()){
                            System.out.println("There is no booked movie tickets found with the ID -" + adminID);
                            break;
                        }
                        else{
                            System.out.println();
                            movieBookings.get(userIDCancel);
                            System.out.println("Here is the booked shows with the userID - "+adminID);
                            for (String innerKey : movieBookings.keySet()) {
                                String[] splitMovie = innerKey.split("-");
                                String movieBookingName = splitMovie[0];
                                if (movieBookingName.trim().equals(movieName)){
                                    System.out.println(splitMovie[1] + " " + movieBookings.get(innerKey));
                                }else{
                                    System.out.println();
                                    System.out.println(splitMovie[1] + "/-/" + movieBookings.get(innerKey));
                                    System.out.println("No tickets found for " + movieName);
                                }
                            }
                        }
                        System.out.println();
                        System.out.println("Enter the movieID for which you want to cancel your tickets::");
                        movieID = (sc.nextLine()).toUpperCase();
                        System.out.println();
                        System.out.println("Number of tickets for the movie " + movieName + " with movieID" +movieID);
                        capacity = Integer.parseInt(sc.nextLine());
                        String newBookings = customerImpl.cancelMovieTickets(adminID, movieID, movieName, capacity);
                        System.out.println(newBookings);
                        LogObject.logger.info("The Admin " + adminID + " retrieved Record count of all the servers");
                    }
                    case 7 -> System.exit(0);
                    default -> System.out.println("Invalid input. Please enter an integer from 1 to 7.");
                }

            }

        }
        catch (Exception e) {
            System.out.println("An exception occurred.");
            e.printStackTrace();
        }
    }
}