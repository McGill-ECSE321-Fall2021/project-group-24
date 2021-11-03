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
public class PrintedMediaController {
	@Autowired
	private PrintedMediaService printedMediaService;
	
	@GetMapping(value = { "/items/printedMedias", "/items/printedMedias/" })
	public List<PrintedMediaDto> getAllPrintedMedias() {
		System.out.println("Flag Get"); 
		return printedMediaService.getAllPrintedMedias().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/items/printedMedias/{itemNum}", "/items/printedMedias/{itemNum}/" })
	public PrintedMediaDto createPrintedMedia(@PathVariable("itemNum") String itemNum) {
		System.out.println("Flag Post"); 
		PrintedMedia printedMedia = printedMediaService.createPrintedMedia(itemNum);
		return convertToDto(printedMedia);
	}
	
	public PrintedMediaDto convertToDto(PrintedMedia printedMedia){
		PrintedMediaDto printedMediaDto = new PrintedMediaDto(printedMedia.getItemTitle(), printedMedia.getDescription(), printedMedia.getImageUrl(), printedMedia.getIsCheckedOut(), printedMedia.getGenre(), printedMedia.getPublishDate(), printedMedia.getIsReservable(),printedMedia.getItemNumber(), printedMedia.getIssueNumber());
		return printedMediaDto;
	}
}
