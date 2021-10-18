package ca.mcgill.ecse321.librarysystem.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class LibrarySystem {

	private String systemNum;
	private Set<User> users;
	private Set<Room> rooms;
	private Set<Item> items;

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> theUsers) {
		this.users = theUsers;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Room> getRooms() {
		return this.rooms;
	}

	@Id
	public String getSystemNum() {
		return this.systemNum;
	}

	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}

	public void setRooms(Set<Room> theRooms) {
		this.rooms = theRooms;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> theItems) {
		this.items = theItems;
	}
}
