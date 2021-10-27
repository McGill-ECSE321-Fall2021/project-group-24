package a.mcgill.ecse321.librarysystem.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {
	@Autowired
	private LibrarianService librarianService;
	
	@GetMapping(value = { "/librarians", "/librarians/" })
	public List<LibrarianDto> getAllLibrarians() {
		System.out.println("Flag Get"); 
		return librarianService.getAllLibrarians().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/librarians/{idNum}", "/librarians/{idNum}/" })
	public LibrarianDto createLibrarian(@PathVariable("idNum") String idNum) {
		System.out.println("Flag Post"); 
		Librarian librarian = librarianService.createLibrarian(idNum);
		return convertToDto(librarian);
	}
	
	private LibrarianDto convertToDto(Librarian librarian){
	//	if (librarian == null) {
	//		throw new Exception("This user does not exist");
	//	}
		LibrarianDto librarianDto = new LibrarianDto(librarian.getIdNum());
		librarianDto.setIdNum(librarian.getIdNum());
		return librarianDto;
	}
}
