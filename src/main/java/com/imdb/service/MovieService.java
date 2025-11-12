package com.imdb.service;

import com.imdb.model.Movie;

import java.util.List;

public interface MovieService {

    Movie getMovieById(Long movieId);
    Movie create(Movie movie);
    List<Movie> getMoviesByTitle(String title);
    List<Movie> getMoviesByReleaseYear(Long releaseYear);

}
