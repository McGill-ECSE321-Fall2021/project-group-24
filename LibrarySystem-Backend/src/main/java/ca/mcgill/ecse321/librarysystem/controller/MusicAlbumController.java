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
public class MusicAlbumController {
	@Autowired
	private MusicAlbumService musicAlbumService;
	
	@GetMapping(value = { "/items/musicAlbums", "/items/musicAlbums/" })
	public List<MusicAlbumDto> getAllMusicAlbums() {
		System.out.println("Flag Get"); 
		return musicAlbumService.getAllMusicAlbums().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/items/musicAlbums/{itemNum}", "/items/musicAlbums/{itemNum}/" })
	public MusicAlbumDto createMusicAlbum(@PathVariable("itemNum") String itemNum) {
		System.out.println("Flag Post"); 
		MusicAlbum musicAlbum = musicAlbumService.createMusicAlbum(itemNum);
		return convertToDto(musicAlbum);
	}
	
	private MusicAlbumDto convertToDto(MusicAlbum musicAlbum){
		MusicAlbumDto musicAlbumDto = new MusicAlbumDto(musicAlbum.getItemTitle(), musicAlbum.getDescription(), musicAlbum.getImageUrl(), musicAlbum.getIsCheckedOut(), musicAlbum.getGenre(), musicAlbum.getPublishDate(), musicAlbum.getIsReservable(),musicAlbum.getItemNumber(), musicAlbum.getArtist(), musicAlbum.getRecordingLabel());
		return musicAlbumDto;
	}
}
