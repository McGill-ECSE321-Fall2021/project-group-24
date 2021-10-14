package ca.mcgill.ecse321.librarysystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class LibrarySystem{

    private Set<User> users;
    private Set<Room> rooms;
    private Set<Item> items;
    private LibraryCalendar libraryCalendar;

        @OneToMany(cascade={CascadeType.ALL})
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> theUsers){
        this.users = theUsers;
    }

        @OneToMany(cascade={CascadeType.ALL})
    public Set<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(Set<Room> theRooms){
        this.rooms = theRooms;
    }

        @OneToMany(cascade={CascadeType.ALL})
    public Set<Item> getItems() {
        return this.items;
    }

    public void setUsers(Set<Item> theItems){
        this.items = theItems;
    }

        @OneToOne(cascade={CascadeType.ALL})
    public LibraryCalendar getLibraryCalendar() {
        return this.items;
    }

    public void setLibraryCalendar(LibraryCalendar theLibraryCalendar){
        this.libraryCalendar = theLibraryCalendar;
    }
}

