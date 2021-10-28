package ca.mcgill.ecse321.librarysystem.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/*@Arman (Del 2)
 * Del 2 edits: Added name, email, address, and phone number (i.e. basic contact info for the library) 
 * Added timeSlots
 */
@Entity
public class LibrarySystem {

	private String systemNum;
	private String name; 
	private String email; 
	private String address; 
	private String phoneNumber; 
	private Set<User> users;
	private Set<Room> rooms;
	private Set<Item> items;
	private List<TimeSlot> timeSlots; 
	
	public String getName() {
		return this.name; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
	    return address;
	}
	
	public void setAddress(String address) {
	    this.address = address;
	}
	
	public String getPhoneNumber() {
	    return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
	    return email;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}

	@OneToMany(cascade = { CascadeType.ALL }) 
	public List<TimeSlot> getTimeSlots() {
		return this.timeSlots; 
	}
	
	public void setTimeSlots(List<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots; 
	}
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
