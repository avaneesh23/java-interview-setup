package com.imdb.test.util;

import java.util.Arrays;
import java.util.List;

import com.imdb.model.Movie;

public class TestDataUtil {

    public static Movie createTestMovie() {
        return createTestMovie("Test Movie", 2000L);
    }

    public static Movie createTestMovie(String title, Long releaseYear) {
        Movie movie = new Movie(title, releaseYear);
        movie.setId(1L);
        return movie;
    }

    public static List<Movie> createTestMovieList() {
        Movie movie1 = new Movie("Gladiator", 2000L);
        movie1.setId(1L);

        Movie movie2 = new Movie("The Beautiful Mind", 2001L);
        movie2.setId(2L);

        Movie movie3 = new Movie("The Departed", 2006L);
        movie3.setId(3L);

        return Arrays.asList(movie1, movie2, movie3);
    }

    public static Movie createMovieWithId(Long id, String title, Long releaseYear) {
        Movie movie = new Movie(title, releaseYear);
        movie.setId(id);
        return movie;
    }

    public static List<Movie> createMoviesByYear(Long year) {
        Movie movie1 = new Movie("Movie 1", year);
        movie1.setId(1L);

        Movie movie2 = new Movie("Movie 2", year);
        movie2.setId(2L);

        return Arrays.asList(movie1, movie2);
    }

    public static List<Movie> createMoviesByTitle(String titleKeyword) {
        Movie movie1 = new Movie(titleKeyword + " Part 1", 2000L);
        movie1.setId(1L);

        Movie movie2 = new Movie(titleKeyword + " Part 2", 2001L);
        movie2.setId(2L);

        return Arrays.asList(movie1, movie2);
    }
}
