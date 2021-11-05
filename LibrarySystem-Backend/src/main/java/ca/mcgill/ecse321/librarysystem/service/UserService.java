package ca.mcgill.ecse321.librarysystem.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.User;


public class UserService {
	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	LibrarianRepository librarianRepository;
	
	@Autowired
	HeadLibrarianRepository headLibrarianRepository;
	
	
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
	
	
	
	//helper methods
	
	public boolean Authenticate(String username, String password, User user) {
		if(username.length()==0||password.length()==0) throw new IllegalArgumentException("No password or username entered.");
		
		if(user.getPassword()==password && user.getUsername() == username) {
			return true;
		}else {
			throw new IllegalArgumentException("Invalid password.");
		}
	}
}
