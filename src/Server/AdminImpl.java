package Server;

import java.rmi.RemoteException;
import java.util.HashMap;



public class AdminImpl implements AdminInterface {
	HashMap<String, HashMap<String, Integer>> movieHashMap;
	HashMap<String, HashMap<String, Integer>> userHashMap;
	HashMap<String, Integer> bookingHashMap;
	HashMap<String, Integer> CustomerBookingHashMap;
	String serverName;

	public AdminImpl(String serverName) throws RemoteException {
		super();
		this.serverName = serverName;
		movieHashMap = new HashMap<>();
		userHashMap = new HashMap<>();
//		bookingHashMap = new HashMap<>();
//		CustomerBookingHashMap = new HashMap<>();
		movieHashMap.put("AVATAR", new HashMap<String, Integer>());
		movieHashMap.put("AVENGERS", new HashMap<String, Integer>());
		movieHashMap.put("TITANIC", new HashMap<String, Integer>());

		userHashMap.put("ATWC5678", new HashMap<String, Integer>());
		userHashMap.put("VERA8901", new HashMap<String, Integer>());
		userHashMap.put("OUTA3456", new HashMap<String, Integer>());

		bookingHashMap = new HashMap<String, Integer>();
		CustomerBookingHashMap = new HashMap<String, Integer>();

		CustomerBookingHashMap = userHashMap.get("ATWC5678");
		CustomerBookingHashMap.put("ATWA07022023", 5);
		userHashMap.put("ATWC5678", CustomerBookingHashMap);

		CustomerBookingHashMap = userHashMap.get("VERA8901");
		CustomerBookingHashMap.put("VERE07022023", 10);
		userHashMap.put("VERA8901", CustomerBookingHashMap);

		CustomerBookingHashMap = userHashMap.get("OUTA3456");
		CustomerBookingHashMap.put("OUTM07022023", 10);
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

	@Override
	public String addMovieSlots(String movieID, String movieName, int bookingCapacity) throws RemoteException {
		try {
			if (movieHashMap.containsKey(movieName)){
				if (movieHashMap.get(movieName).containsKey(movieID)) {
					movieHashMap.get(movieName).put(movieID, bookingCapacity + movieHashMap.get(movieName).get(movieID));
					System.out.println("Movie slots with ID " + movieID + " has been updated!");
					System.out.println(movieHashMap);
					return "Movie slots updated for the movie.";
				}else{
					movieHashMap.get(movieName).put(movieID, bookingCapacity);
					System.out.println(movieHashMap);
					return "Movie slots added for the movie.";
				}
			} else {
				System.out.println("Movie name does not exist");
				bookingHashMap.put(movieID, bookingCapacity);
				movieHashMap.put(movieName, bookingHashMap);
				System.out.println("Movie slots has been added." + movieHashMap);
				return "New movie slots has been added for the movie" + movieName;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String removeMovieSlots(String movieID, String movieName) throws RemoteException {
		if (movieHashMap.get(movieName).containsKey(movieID)){
			movieHashMap.get(movieName).remove(movieID);
			System.out.println();
			System.out.println("The foolowing is the availability after removal:: "+ movieHashMap.get(movieName));
			return "Movie slots for the movie " + movieName + "and movieID" + movieID + " has been removed.";
		}else {
			System.out.println("There iare no movie slots for the movie" + movieName);
			return "No movie slots found for the movie " + movieName + " at " + movieID.substring(0, 3) + " region";
		}
	}

	@Override
	public String listMovieShowsAvailability(String movieName) throws RemoteException {
		if (movieHashMap.containsKey(movieName)){
			System.out.println(movieHashMap.get(movieName));
			return movieHashMap.get(movieName).toString();
		}
		else{
			return new HashMap<>().toString();
		}
	}

}
