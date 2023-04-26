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
		loadInitialData();
		
		System.out.println(userList.size() + " Users");
		System.out.println(roomList.size() + " Rooms");
	}

	public void loadInitialData() {
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
	
	@Override
	public boolean moveUser(User user, int moveTo) {
		user.getRoom().setUserPosition(moveTo);
		return true;
	}

	@Override
	public int findUserIDByName(String username) {
		return 0;
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
	
	@Override
	public void swapItemInRoom(Item itemToRemove, Item itemToAdd, User user) {
		user.getRoom().getItems().remove(itemToRemove);
		user.getRoom().getItems().add(itemToAdd);
	}
	@Override
	public void swapItemInInventory(Item itemToRemove, Item itemToAdd, User user) {
		user.getInventory().remove(itemToRemove);
		user.getInventory().add(itemToAdd);
	}
	@Override
	public String useEmptyPotion(Item bottle, Item selected, User user) {
		String message = "Nothing Happened";
		System.out.println(selected.getName() + "is selected");
		if(selected.getName().equals("Cauldron with Potion")) {
			message = "You filled the bottle with a potion";
			Item fullPotion = new Item("Full Potion Bottle", false, 0,0,0);
			swapItemInInventory(bottle, fullPotion, user);
		}
		return message;
	}
	
	@Override
	public String useMatches(Item matches, Item selected, User user) {
		String message = "Nothing Happened";
		System.out.println(selected.getName() + "is selected");
		if(selected.getName().equals("Unlit Candle")) {
			message = "You lit the candle";
			Item litCandle = new Item("Lit Candle", false, 150,205,0);
			swapItemInRoom(selected, litCandle, user);
			user.getInventory().remove(matches);
		}
		return message;
	}

	@Override
	public boolean addItemToRoom(Item item, int roomID) {
		List<Item> invList = roomList.get(roomID-1).getItems();
		item.setRoomPosition(roomList.get(roomID-1).getUserPosition());
		if(invList.add(item)) {
			roomList.get(roomID-1).setItems(invList);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean addItemToInventory(Item item, int userID) {
		List<Item> invList = userList.get(userID-1).getInventory();
		if(invList.add(item)) {
			userList.get(userID-1).setInventory(invList);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean removeItemFromRoom(Item item, int roomID) {
		List<Item> invList = roomList.get(roomID-1).getItems();
		if(invList.remove(item)) {
			roomList.get(roomID-1).setItems(invList);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean removeItemFromInventory(Item item, int userID) {
		List<Item> invList = userList.get(userID-1).getInventory();
		if(invList.remove(item)) {
			userList.get(userID-1).setInventory(invList);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean changeCanBePickedUp(User user, Item item, Boolean canBePickedUp) {
		List<Item> items = user.getRoom().getItems();
		
		for(int i = 0; i < items.size(); i++) {
			if(item.getName() == items.get(i).getName()) {
				user.getRoom().getItems().get(i).setCanBePickedUp(canBePickedUp);
			}
		}
		return true;
	}

	@Override
	public String usePotionIngredient(Item item, Item selected, User user) {
		//message telling the user they successfully added an item
		String message = "You put the item in the cauldron";
		List<Item> items = user.getRoom().getItems();
		List<Item> ingredients = new ArrayList<Item>();
		//position 4 means it is a potion ingredient. Find all of the potion ingredients added
		for(int i = 0;i<items.size();i++) {
			if(items.get(i).getRoomPosition() == 4) {
				ingredients.add(items.get(i));
			}
		}
		
		Item itemToAdd = item;
		//2 represents the number of ingredients needed. Change this later when all ingredients are added
		if(ingredients.size() < 2) {
			itemToAdd.setRoomPosition(4);
			items.add(itemToAdd);
			ingredients.add(itemToAdd);
		}
		
		if(ingredients.size() >= 2) {
			//check if the ingredients are correct and in the right order
			if(ingredients.get(0).getName().equals("Jar of Cat Hairs") &&
					ingredients.get(1).getName().equals("Jar with Hibiscus")) {
					//swap empty cauldron with full cauldron 
				Item emptyCauldron = selected;
				Item fullCauldron = selected;
				fullCauldron.setName("Cauldron with potion");
				//if the potion was made swap the empty cauldron with the full cauldron into the room
				swapItemInRoom(emptyCauldron, fullCauldron, user);
				//message telling the user they were successful
				message = "You created a potion";
			}else {
				//if they are incorrect return the items to inventory and remove them from ingredient list
				Item firstItem = ingredients.get(0);
				Item secondItem = ingredients.get(1);
				user.getInventory().add(firstItem);
				user.getInventory().add(secondItem);
				items.remove(firstItem);
				items.remove(secondItem);
				//message telling the user they were not successful
				message = "The ingredients added did not seem to do anything";
			}
		}
		
		//return the message to be displayed
		return message;
	}

	@Override
	public List<Item> findItemsInPositionByID(int roomID, int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemByNameAndIDInRoom(String name, int roomID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item findItemByNameAndIDInInv(String name, int userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
