package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface CustomerInterface extends Remote {
		
		public String bookMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws RemoteException;
		
		public HashMap<String, Integer> getBookingSchedule(String customerID) throws RemoteException;
		
		public String cancelMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws RemoteException;
	}