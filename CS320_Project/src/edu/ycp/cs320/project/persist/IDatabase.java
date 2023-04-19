package edu.ycp.cs320.project.persist;

import edu.ycp.cs320.project.model.*;
import java.util.List;

public interface IDatabase {
	// Returns either the user in question or Null if no user found.
	public User findUserByName(String name);
	
	public boolean transferItemFromRoomToUser(User user, Item item);
	
	public boolean transferItemFromUserToRoom(User user, String itemName);
	
	public boolean moveUser(User user, int moveTo);

	public boolean addUser(User user);
}
