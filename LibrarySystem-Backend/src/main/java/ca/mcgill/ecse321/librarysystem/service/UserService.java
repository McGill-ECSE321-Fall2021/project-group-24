package ca.mcgill.ecse321.librarysystem.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.HeadLibrarian;
import ca.mcgill.ecse321.librarysystem.model.Librarian;
import ca.mcgill.ecse321.librarysystem.model.Patron;
import ca.mcgill.ecse321.librarysystem.model.User;

@Service
public class UserService {
	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	LibrarianRepository librarianRepository;
	
	@Autowired
	HeadLibrarianRepository headLibrarianRepository;
	
	
	
	@Transactional
	public User logIn(String username, String password) {
		if(patronRepository.findPatronByUsername(username)==null && librarianRepository.findUserByUsername(username)==null && headLibrarianRepository.findUserByUsername(username)==null) {
			throw new IllegalArgumentException("Invalid username entered.");
		}
		
		User user = null;
		
		if(patronRepository.findPatronByUsername(username)!=null) {
			user = patronRepository.findPatronByUsername(username);
		}else if(librarianRepository.findUserByUsername(username)!=null) {
			user = librarianRepository.findUserByUsername(username);
		}else if(headLibrarianRepository.findUserByUsername(username)!= null) {
			user = headLibrarianRepository.findUserByUsername(username);
		}
		
		if(Authenticate(username, password, user)==true) {
			user.setIsLoggedIn(true);
			
			return user;
		}
		
		else return null;
	}
	
	
	
	
	@Transactional
	public User logOut(String username) {
		User user = null;
		String type="";
		if(patronRepository.findPatronByUsername(username)!=null) {
			user = patronRepository.findPatronByUsername(username);
			type="patron";
		}else if(librarianRepository.findUserByUsername(username)!=null) {
			user = librarianRepository.findUserByUsername(username);
			type="librarian";
			
		}else if(headLibrarianRepository.findUserByUsername(username)!= null) {
			user = headLibrarianRepository.findUserByUsername(username);
			type="head librarian";
		}
		
		if(user.getIsLoggedIn()==false)throw new IllegalArgumentException("User is not logged in.");
		
		
		
		user.setIsLoggedIn(false);
		if(type.equals("patron")) {
			Patron p = (Patron) user;
			patronRepository.save(p);
		}else if(type.equals("librarian")) {
			Librarian lib = (Librarian) user;
			librarianRepository.save(lib);
		}else {
			HeadLibrarian h = (HeadLibrarian) user;
			headLibrarianRepository.save(h);
		}
		return user;
	}
	
	
	@Transactional
	public User getUserAccountById(String id) {
		if(id.length()==0) throw new IllegalArgumentException("Invalid ID entered.");
		User user = null;
		if(librarianRepository.findUserByIdNum(id)!=null) {
			user = librarianRepository.findUserByIdNum(id);
		}else if(patronRepository.findPatronByIdNum(id)!=null) {
			user = patronRepository.findPatronByIdNum(id);
		}else if(headLibrarianRepository.findUserByIdNum(id)!=null) {
			user = headLibrarianRepository.findUserByIdNum(id);
		}else {
			throw new IllegalArgumentException("An account with this ID does not exist.");
		}
		
		return user;
	}
	
	
	@Transactional
	public User getUserAccountByUsername(String username) {
		if(username.length()==0) throw new IllegalArgumentException("Invalid ID entered.");
		User user = null;
		if(librarianRepository.findUserByUsername(username)!=null) {
			user = librarianRepository.findUserByUsername(username);
		}else if(patronRepository.findPatronByUsername(username)!=null) {
			user = patronRepository.findPatronByUsername(username);
		}else if(headLibrarianRepository.findUserByUsername(username)!=null) {
			user = headLibrarianRepository.findUserByUsername(username);
		}else {
			throw new IllegalArgumentException("An account with this username does not exist.");
		}
		return user;
	}
	
	
	@Transactional
	public User changePassword(String username, String newPass, String oldPass) {
		
		User user = null;
		if(librarianRepository.findUserByUsername(username)!=null) {
			user = librarianRepository.findUserByUsername(username);
		}else if(patronRepository.findPatronByUsername(username)!=null) {
			user = patronRepository.findPatronByUsername(username);
		}else if(headLibrarianRepository.findUserByUsername(username)!=null) {
			user = headLibrarianRepository.findUserByUsername(username);
		}else {
			throw new IllegalArgumentException("An account with this username does not exist.");
		}
		
		if(!user.getPassword().equals(oldPass)){
			throw new IllegalArgumentException("Incorrect password entered.");
		}
		
		if(user.getIsLoggedIn()==false) {
			throw new IllegalArgumentException("User must be logged in to change password.");
		}
		
		if(user.getPassword().equals(newPass)) {
			throw new IllegalArgumentException("New password cannot be the same as the old password.");
		}
		
		if(newPass.length()<8) {
			throw new IllegalArgumentException("Password needs to be at least 6 characters in length.");
		}
		
		
		user.setPassword(newPass);
		return user;
	}
	
	
	
	
	@Transactional
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		List<Patron> patrons = toList(patronRepository.findAll());
		List<Librarian> librarians = toList(librarianRepository.findAll());
		List<HeadLibrarian> hl = toList(headLibrarianRepository.findAll());
		
		users = Stream.of(hl, librarians, patrons).flatMap(Collection::stream).collect(Collectors.toList());
		return users;
	}
	
	
	
	
	
	//helper methods
	
	public boolean Authenticate(String username, String password, User user) {
		if(username.length()==0||password.length()==0) throw new IllegalArgumentException("No password or username entered.");
		
		if(user.getPassword().equals(password) && user.getUsername().equals(username)) {
			return true;
		}else {
			throw new IllegalArgumentException("Invalid password or username.");
		}
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for(T t: iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	
}
