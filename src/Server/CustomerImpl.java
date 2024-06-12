package Server;

import java.rmi.RemoteException;
import java.util.HashMap;

public class CustomerImpl implements CustomerInterface {
    HashMap<String, HashMap<String, Integer>> movieHashMap;
    HashMap<String, HashMap<String, Integer>> userHashMap;
    HashMap<String, Integer> bookingHashMap;
    HashMap<String, Integer> CustomerBookingHashMap;
    String serverName;

    public CustomerImpl(String serverName) throws RemoteException{
        super();
        this.serverName = serverName;
        movieHashMap = new HashMap<>();
        userHashMap = new HashMap<>();
        movieHashMap.put("AVATAR", new HashMap<String, Integer>());
        movieHashMap.put("AVENGERS", new HashMap<String, Integer>());
        movieHashMap.put("TITANIC", new HashMap<String, Integer>());

        userHashMap.put("ATWC5678", new HashMap<String, Integer>());
        userHashMap.put("VERA8901", new HashMap<String, Integer>());
        userHashMap.put("OUTA3456", new HashMap<String, Integer>());

        bookingHashMap = new HashMap<String, Integer>();
        CustomerBookingHashMap = new HashMap<String, Integer>();

        CustomerBookingHashMap = userHashMap.get("ATWC5678");
//        CustomerBookingHashMap.put("ATWA07022023", 5);
        userHashMap.put("ATWC5678", CustomerBookingHashMap);

        CustomerBookingHashMap = userHashMap.get("VERA8901");
//        CustomerBookingHashMap.put("VERE07022023", 10);
        userHashMap.put("VERA8901", CustomerBookingHashMap);

        CustomerBookingHashMap = userHashMap.get("OUTA3456");
//        CustomerBookingHashMap.put("OUTM07022023", 10);
        userHashMap.put("OUTA3456", CustomerBookingHashMap);

        bookingHashMap = movieHashMap.get("AVATAR");
        bookingHashMap.put("OUTA07022023", 60);
        movieHashMap.put("AVATAR", bookingHashMap);

        bookingHashMap = movieHashMap.get("AVENGERS");
        bookingHashMap.put("ATWM07022023", 60);
        movieHashMap.put("AVENGERS", bookingHashMap);

        bookingHashMap = movieHashMap.get("TITANIC");
        bookingHashMap.put("VERA07022023", 60);
        movieHashMap.put("TITANIC", bookingHashMap);

    }
    public String bookMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) {
        if (movieHashMap.containsKey(movieName)) {
            if (movieHashMap.get(movieName).containsKey(movieID)) {
                if (movieHashMap.get(movieName).get(movieID) > numberOfTickets) {
                    movieHashMap.get(movieName).put(movieID, movieHashMap.get(movieName).get(movieID) - numberOfTickets);

                    String movieSuffix = movieName + "-" + movieID;
                    if (userHashMap.containsKey(customerID)) {
                        if (userHashMap.get(customerID).containsKey(movieSuffix)) {
                            userHashMap.get(customerID).put(movieID, userHashMap.get(customerID).get(movieSuffix) + numberOfTickets);
                            return numberOfTickets + " tickets booked for the movie " + movieName + " with movieID " + movieID;

                        } else {

                            userHashMap.get(customerID).put(movieSuffix, numberOfTickets);
                            return numberOfTickets + " tickets booked for the movie:" + movieName + " with movie ID: " + movieID;
                        }
                    } else {

                        CustomerBookingHashMap.put(movieSuffix, numberOfTickets);
                        userHashMap.put(customerID, CustomerBookingHashMap);
                        return numberOfTickets + " tickets booked for the movie " + movieName + " with movieID " + movieID;
                    }
                } else {
                    return numberOfTickets + " Seats are not available for the " + movieName + " with movieID " + movieID;
                }
            } else {
                return "No movie found with the ID" + movieID;
            }
        } else {
            return "No movie found with the name " + movieName;
        }
    }

    public HashMap<String, Integer> getBookingSchedule(String customerID){
        if(userHashMap.containsKey(customerID)){
            return userHashMap.get(customerID);
        }
        else{
            return new HashMap<>();
        }
    }

    public String cancelMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets){
        if(userHashMap.containsKey(customerID)){
            String movieSuffix = movieName + "-" + movieID;
            if(userHashMap.get(customerID).containsKey(movieSuffix)){
                if(userHashMap.get(customerID).get(movieSuffix) >= numberOfTickets){
                    userHashMap.get(customerID).put(movieSuffix, userHashMap.get(customerID).get(movieSuffix) - numberOfTickets);
                    movieHashMap.get(movieName).put(movieSuffix, movieHashMap.get(movieName).get(movieID) + numberOfTickets);
                    return numberOfTickets + " Movie tickets for " + movieName + " has been removed";
                }
                else{
                    movieHashMap.get(movieName).put(movieSuffix, userHashMap.get(customerID).get(movieID));
                    userHashMap.get(customerID).remove(movieSuffix);
                    userHashMap.get(customerID).remove(movieSuffix, userHashMap.get(customerID).get(movieID));
                    return numberOfTickets + " Movie tickets for " + movieName + " has been removed";
                }
            }else{
                return "No movies found for the movieID: " + movieID;
            }
        }else{
            return "No user data found for the customerID " + customerID;
        }
    }
}
