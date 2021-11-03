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
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.LibrarySystemApplication;
import ca.mcgill.ecse321.librarysystem.model.*;
import ca.mcgill.ecse321.librarysystem.dto.*;
import ca.mcgill.ecse321.librarysystem.service.*;

@CrossOrigin(origins = "*")
@RestController
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	@GetMapping(value = { "/items/movies", "/items/movies/" })
	public List<MovieDto> getAllMovies() {
		System.out.println("Flag Get"); 
		return movieService.getAllMovies().stream().map(lib -> convertToDto(lib)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/items/movies/{itemNum}", "/items/movies/{itemNum}/" })
	public MovieDto createMovie(@PathVariable("itemNum") String itemNum) {
		System.out.println("Flag Post"); 
		Movie movie = movieService.createMovie(itemNum);
		return convertToDto(movie);
	}
	
	private MovieDto convertToDto(Movie movie){
		MovieDto movieDto = new MovieDto(movie.getItemTitle(), movie.getDescription(), movie.getImageUrl(), movie.getIsCheckedOut(), movie.getGenre(), movie.getPublishDate(), movie.getIsReservable(),movie.getItemNumber(), movie.getProductionCompany(), movie.getMovieCast(),movie.getDirector(), movie.getProducer());
		return movieDto;
	}
}
