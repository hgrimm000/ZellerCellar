package edu.ycp.cs320.project.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.project.model.*;
import edu.ycp.cs320.project.persist.IDatabase;


public class FakeDatabase implements IDatabase {
	
	private List<User> userList;
	private List<Room> roomList;
	
	public FakeDatabase() {
		userList = new ArrayList<User>();
		roomList = new ArrayList<Room>();
		
		// Add initial data
		readInitialData();
		
		System.out.println(userList.size() + " Users");
		System.out.println(roomList.size() + " Rooms");
	}

	public void readInitialData() {
		try {
			// Gets all users and rooms
			userList.addAll(InitialData.getUsers());
			roomList.addAll(InitialData.getRooms());
			// Links users/rooms together (adds the rooms to the related user)
			for (User user : userList) {
				for (Room room : roomList) {
					if(room.getUserID() == user.getUserID()) {
						user.setRoom(room);
					}
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
	
	@Override
	public User findUserByName(String name) {
		// Searches userList for a username, returns the user or null
		for (User user : userList) {
			if (user.getUsername().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	@Override
	public boolean addUser(User user) {
		// Sets up inventory and room before adding.
		user.setInventory(new ArrayList<Item>());
		user.setRoom(new Room());
	    if (userList.add(user) && roomList.add(new Room())) {
	        return true;
	    }
	    return false;
	}
	
	@Override
	public boolean transferItemFromRoomToUser(User user, Item item) {
		user.getInventory().add(item);
		user.getRoom().getItems().remove(item);
		return true;
	}
	
	@Override
	public boolean transferItemFromUserToRoom(User user, String itemName) {
		Item itemToBeTransferred = findItemByName(itemName, user.getInventory());
		// Does the item exist in the inventory?
		// MOVE TO CONTROLLER
		if(itemToBeTransferred != null) {
			// Can the item be picked up?
			// MOVE TO CONTROLLER
			if(itemToBeTransferred.getCanBePickedUp() == true) {
				itemToBeTransferred.setRoomPosition(user.getRoom().getUserPosition());
				user.getRoom().getItems().add(itemToBeTransferred);
				user.getInventory().remove(itemToBeTransferred);
				return true;
			}
		}
		return false;
	}

	private Room findRoomByUserID(int userID) {
		for (Room room : roomList) {
			if (room.getUserID() == userID) {
				return room;
			}
		}
		return null;
	}
	
	private Item findItemByName(String itemName, List<Item> itemList) {
		for (Item item : itemList) {
			if(item.getName() == itemName) {
				return item;
			}
		}
		return null;
	}
}
