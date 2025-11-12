package com.imdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imdb.model.Movie;
import com.imdb.service.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        try {
            Movie createdMovie = movieService.create(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/title")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getMoviesByTitle(@RequestParam String title) {
        return movieService.getMoviesByTitle(title);
    }

    @GetMapping("/search/year")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getMoviesByReleaseYear(@RequestParam Long releaseYear) {
        return movieService.getMoviesByReleaseYear(releaseYear);
    }
}
