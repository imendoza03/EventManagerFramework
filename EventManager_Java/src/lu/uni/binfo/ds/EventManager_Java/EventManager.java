package lu.uni.binfo.ds.EventManager_Java;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class EventManager
{
	private static EventManager instance;
	
	/**
	 * Default Constructor.
	 */
	public EventManager() { }
	
	/**
	 * Singleton pattern instantiation of the EventManager object.
	 * @return The unique instance of the EventManager object.
	 */
	public static EventManager Instance()
	{
		if(instance == null)
		{
			instance = new EventManager();
		}
		return instance;
	}
	
	/**
	 * Method used to wait for input and invoke methods according to that input.
	 */
	public void Start()
	{
		System.out.println("EventManager started");
		String input = "";
		Scanner in = new Scanner(System.in);
		// Create the events folder if it does not already exist.
		File dir = new File("events");
		if(! dir.exists())
		{
			dir.mkdir();
		}
		
		// Enter a loop to parse all input.
		do{
			System.out.println(">");
			input = in.nextLine();
			
			// Perform string matching
			if(input.equals("le"))
			{
				list();
				continue;
			}

			if(input.equals("help"))
			{
				help();
				continue;
			}

			if(input.equals("clear"))
			{
				clear();
				continue;
			}
			
			if(input.startsWith("add"))
			{
				// Call the add method and pass a string array as argument which does not
				// include "add" and has split the remaining arguments by whitespace.
				add(input.substring(4).split("\\s+"));
				continue;
			}
			
			if(input.startsWith("remove"))
			{
				// Call the remove method and pass a string array as argument which does not
				// include "add" and has split the remaining arguments by whitespace.
				remove(input.substring(7).split("\\s+"));
				continue;
			}
			
			if(input.startsWith("register"))
			{
				// Call the register method and pass a string array as argument which does not
				// include "register" and has split the remaining arguments by whitespace.
				register(input.substring(9).split("\\s+"));
			}
			
			if(input.startsWith("drop"))
			{
				// Call the drop method and pass a string array as argument which does not
				// include "drop" and has split the remaining arguments by whitespace.
				drop(input.substring(5).split("\\s+"));
			}
			
			if(input.startsWith("modify"))
			{
				// Call the modify method and pass a string array as argument which does not
				// include "modify" and has split the remaining arguments by whitespace.
				modify(input.substring(7).split("\\s+"));
			}
			
			// Fall through case
			System.out.println("Illegal command: " + input);
			
		}while(!input.equals("quit"));
		// Cleanup.
		System.out.println("EventManager is terminating...");
		in.close();		
	}
	
	/**
	 * Method used for listing all events.
	 */
	private void list()
	{
		File dir = new File("events");
		File[] events = dir.listFiles();
		// No files in directory.
		if(events == null)
		{
			return;
		}
		// Initialise streams
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    BufferedReader reader = null;
		
	    // Loop over all files in the directory.
	    for(File f : events)
	    {
	    	try{
	    		fis = new FileInputStream(f);
	    		bis = new BufferedInputStream(fis);
	    		reader = new BufferedReader(new InputStreamReader(bis));
	    		String entry;

	    		while ((entry = reader.readLine()) != null)
	    		{
	    			System.out.println(f.getName().substring(0, f.getName().indexOf(".")) + "\t" + entry);	    			
	    		}

	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		try{
	    			fis.close();
	    			bis.close();
	    			reader.close();
	    		}catch(IOException e)
	    		{
	    			System.out.println("Error closing streams!");
	    			e.printStackTrace();
	    		}
	    	}
	    }
	    System.out.println();
	}
	
	/**
	 * The method modifies the given event locally as well as remote.
	 * TODO implement the remainder of the method.
	 * @param tokens
	 */
	private void modify(String[] tokens)
	{
        if (tokens.length < 3)
        {
            System.out.println("Illegal arguments");
            System.out.println("modify [id] [field] [newValue] --> Modify the event with the given id and set the value of the specified field to [newValue].");
            System.out.println();
            return;
        }
        // TODO: Add additional input validity checks
        String id = tokens[0];
        String field = tokens[1];
        String newValue = tokens[2];
        // TODO: Modify the given event locally as well as remote
	}
	
	/**
	 * Method used to register devices with the current device in order to receive/send updates.
	 * TODO fully implement method
	 * @param tokens
	 */
	private void register(String[] tokens)
	{
         if (tokens.length < 1)
         {
             System.out.println("Illegal arguments");
             System.out.println("register [IP] --> Register the device with the given [IP].");
             System.out.println();
             return;
         }
         //TODO: Add additional input validity checks
         String ip = tokens[0];  
         //TODO: Add the host with the given IP to the list of remote hosts and notify the remote host for mutual addition. 
	}
	
	private void drop(String[] tokens)
	{
		if (tokens.length < 1)
        {
            System.out.println("Illegal arguments");
            System.out.println("drop [IP] --> Drop the device with the given [IP].");
            System.out.println();
            return;
        }
        //TODO: Add additional input validity checks
        String ip = tokens[0];  
        //TODO: Add the host with the given IP to the list of remote hosts and notify the remote host for mutual addition.
	}
	
	/**
	 * Method for adding an event. Will create a local file in the events directory.
	 * TODO Notify other devices of the addition.
	 * @param tokens Parameters for the event in the form of [date, time, duration, header, comment].
	 */
	private void add(String[] tokens)
	{
		if(tokens.length < 5)
		{
			System.out.println("Illegal arguments");
            System.out.println("add [date] [time] [duration] [header] [comment] "+
            		"--> add a new event with the given arguments; date format: "+
            		"dd.mm.yyyy; time format: hh:mm; duration in minutes");
            System.out.println();
            return;
		}
		// TODO Check data validity and formating
		SimpleDateFormat df = new SimpleDateFormat("dd.mm.yyyy HH:mm");
		Date date = null;
		try {
			date = df.parse(tokens[0] + " " + tokens[1]);
		} catch (ParseException e){
			System.out.println("Error parsing date/time!");
            System.out.println("add [date] [time] [duration] [header] [comment] "+
            		"--> add a new event with the given arguments; date format: "+
            		"dd.mm.yyyy; time format: hh:mm; duration in minutes");
            System.out.println();
            return;
		}
		int duration = Integer.parseInt(tokens[2]);
        String header = tokens[3];
        String comment = tokens[4];      
        // ToDo: Write the new date to a file in the directory "events" and send it to the other devices...
	}
	/**
	 * Method for removing all events.
	 * TODO Notify all other devices of the deletion.
	 */
	private void clear()
	{
		File dir = new File("events");
		File[] events = dir.listFiles();
		// No files in directory.
		if(events == null)
		{
			return;
		}
		// Remove all files and thereby events from the local events directory.
		for(File f : events)
		{
			f.delete();
		}
	}
	
	/**
	 * Method removing the event that matches the input.
	 * TODO Notify all other devices of the removal.
	 * @param tokens
	 */
	private void remove(String[] tokens)
	{
		if (tokens.length < 1)
        {
            System.out.println("Illegal arguments");
            System.out.println("remove [id] --> remove the event with the given id");
            return;
        }
		
		File dir = new File("events");
		File[] events = dir.listFiles();
		if(events == null)
		{
			System.out.println("Error - Event list emtpy!");
			return;
		}
		// Remove file.
		for(File f : events)
		{
			if(f.getName().equals(tokens[0] + ".event"))
			{
				f.delete();
				return;
			}
		}
		System.out.println("Error - No matching event could be found!");		
	}
	
	/**
	 * This method shows all valid commands and arguments.
	 */
	private void help()
	{
		System.out.println("ls --> list events");
		System.out.println();
        System.out.println("add [date] [time] [duration] [header] [comment] --> add a new event with the given arguments; date format: dd.mm.yyyy; time format: hh:mm; duration in minutes");
		System.out.println();
        System.out.println("remove [id] --> remove the event with the given id");
		System.out.println();
        System.out.println("clear --> remove all events");
		System.out.println();
        System.out.println("modify [id] [field] [newValue] --> modify the event with the given id and set the value of the specified field to newValue");
		System.out.println();
        System.out.println("quit --> quit the application");
		System.out.println();
	}
}
