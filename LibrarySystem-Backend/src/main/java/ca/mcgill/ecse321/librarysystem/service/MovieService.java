package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;

@Service
public class MovieService  {
	
	@Autowired 
	MovieRepository movieRepository;
		
	// creates movie, returns it so we know it's not null 
	@Transactional 
	public Movie createMovie(
		String itemTitle,
		String description,
		String imageUrl,
		String itemNumber,
		String genre,
		Date publishDate,
		boolean isReservable,
		boolean isCheckedOut,
		String productionCompany,
		String producer,
		String director,
		String cast)
	{
		Movie movie = new Movie();
	    movie.setItemTitle(itemTitle);
	    movie.setDescription(description);
	    movie.setImageUrl(imageUrl);
	    movie.setItemNumber(itemNumber);
	    movie.setGenre(genre);
	    movie.setPublishDate(publishDate);
	    movie.setIsReservable(isReservable);
		movie.setIsCheckedOut(isCheckedOut);
		movie.setProductionCompany(productionCompany);
	    movie.setProducer(producer);
		movie.setDirector(director);
		movie.setMovieCast(cast);

	    movieRepository.save(movie);
	    return movie;		
	}
	public Movie createMovie(String itemNumber) {
		Movie movie = new Movie(); 
		long currentMillis = System.currentTimeMillis();
		Date currentDay = new Date(currentMillis);

		movie.setItemTitle("itemTitle");
	    movie.setDescription("description");
	    movie.setImageUrl("imageUrl");
	    movie.setItemNumber(itemNumber);
	    movie.setGenre("genre");
	    movie.setPublishDate(currentDay);
	    movie.setIsReservable(true);
		movie.setIsCheckedOut(false);
		movie.setProductionCompany("productionCompany");
	    movie.setProducer("producer");
		movie.setDirector("director");
		movie.setMovieCast("cast");
	    
		movieRepository.save(movie);
	    return movie;	
	}
	
	// looks for a movie with the given item number, returns them if found
	@Transactional 
	public Movie getMovie(String itemNumber) {
		Movie movie = movieRepository.findMovieByItemNumber(itemNumber); 
		return movie;
	}
	
	@Transactional 
	public List<Movie> getAllMovies() {
		return toList(movieRepository.findAll()); 
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
