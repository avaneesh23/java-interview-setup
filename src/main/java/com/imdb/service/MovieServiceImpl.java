package com.imdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imdb.dto.MovieRepository;
import com.imdb.model.Movie;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie getMovieById(Long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        return (movie.isPresent()) ? movie.get() : null;
    }

    @Override
    public Movie create(Movie movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid movie data");
        }
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getMoviesByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public List<Movie> getMoviesByReleaseYear(Long releaseYear) {
        List<Movie> list = (List<Movie>) movieRepository.findAll();
        return list.stream().filter(obj -> obj.getReleaseYear().equals(releaseYear)).collect(Collectors.toList());

    }
}
