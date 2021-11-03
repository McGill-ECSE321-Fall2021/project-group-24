package ca.mcgill.ecse321.librarysystem.controller;
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

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ArchiveController {
	@Autowired
	private ArchiveService archiveService;
	
	@GetMapping(value = { "/items/archives", "/items/archives/" })
	public List<ArchiveDto> getAllArchives() {
		System.out.println("Flag Get"); 
		return archiveService.getAllArchives().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/items/archives/{itemNum}", "/items/archives/{itemNum}/" })
	public ArchiveDto createArchive(@PathVariable("itemNum") String itemNum) {
		System.out.println("Flag Post"); 
		Archive archive = archiveService.createArchive(itemNum);
		return convertToDto(archive);
	}
	
	public ArchiveDto convertToDto(Archive archive){
		ArchiveDto archiveDto = new ArchiveDto(archive.getItemTitle(), archive.getDescription(), archive.getImageUrl(), archive.getIsCheckedOut(), archive.getGenre(), archive.getPublishDate(), archive.getIsReservable(),archive.getItemNumber());
		return archiveDto;
	}
}
