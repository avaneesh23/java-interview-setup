package com.imdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.model.Movie;
import com.imdb.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie testMovie;
    private List<Movie> testMovies;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Gladiator", 2000L);
        testMovie.setId(1L);

        Movie movie2 = new Movie("The Beautiful Mind", 2001L);
        movie2.setId(2L);

        Movie movie3 = new Movie("The Departed", 2006L);
        movie3.setId(3L);

        testMovies = Arrays.asList(testMovie, movie2, movie3);
    }

    @Test
    void getMovieById_WhenMovieExists_ShouldReturnMovie() throws Exception {
        // Given
        when(movieService.getMovieById(1L)).thenReturn(testMovie);

        // When & Then
        mockMvc.perform(get("/api/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Gladiator"))
                .andExpect(jsonPath("$.releaseYear").value(2000));
    }

    @Test
    void getMovieById_WhenMovieDoesNotExist_ShouldReturnNull() throws Exception {
        // Given
        when(movieService.getMovieById(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/movie/999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void createMovie_WithValidMovie_ShouldReturnCreatedMovie() throws Exception {
        // Given
        Movie newMovie = new Movie("Inception", 2010L);
        Movie savedMovie = new Movie("Inception", 2010L);
        savedMovie.setId(4L);

        when(movieService.create(any(Movie.class))).thenReturn(savedMovie);

        // When & Then
        mockMvc.perform(post("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMovie)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.releaseYear").value(2010));
    }

    @Test
    void createMovie_WithInvalidMovie_ShouldReturnBadRequest() throws Exception {
        // Given
        Movie invalidMovie = new Movie(null, null);

        when(movieService.create(any(Movie.class)))
                .thenThrow(new IllegalArgumentException("Invalid movie data"));

        // When & Then
        mockMvc.perform(post("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMovie)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMoviesByTitle_WhenMoviesFound_ShouldReturnMovies() throws Exception {
        // Given
        List<Movie> moviesWithTitle = Arrays.asList(testMovie);
        when(movieService.getMoviesByTitle("Gladiator")).thenReturn(moviesWithTitle);

        // When & Then
        mockMvc.perform(get("/api/movie/search/title")
                        .param("title", "Gladiator"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Gladiator"))
                .andExpect(jsonPath("$[0].releaseYear").value(2000));
    }

    @Test
    void getMoviesByTitle_WhenNoMoviesFound_ShouldReturnEmptyList() throws Exception {
        // Given
        when(movieService.getMoviesByTitle("NonExistent")).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/movie/search/title")
                        .param("title", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getMoviesByTitle_WithEmptyTitle_ShouldReturnEmptyList() throws Exception {
        // Given
        when(movieService.getMoviesByTitle("")).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/movie/search/title")
                        .param("title", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getMoviesByReleaseYear_WhenMoviesFound_ShouldReturnMovies() throws Exception {
        // Given
        List<Movie> moviesFromYear = Arrays.asList(testMovie);
        when(movieService.getMoviesByReleaseYear(2000L)).thenReturn(moviesFromYear);

        // When & Then
        mockMvc.perform(get("/api/movie/search/year")
                        .param("releaseYear", "2000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Gladiator"))
                .andExpect(jsonPath("$[0].releaseYear").value(2000));
    }

    @Test
    void getMoviesByReleaseYear_WhenNoMoviesFound_ShouldReturnEmptyList() throws Exception {
        // Given
        when(movieService.getMoviesByReleaseYear(1990L)).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/movie/search/year")
                        .param("releaseYear", "1990"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getMoviesByReleaseYear_WithInvalidYear_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/movie/search/year")
                        .param("releaseYear", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMoviesByTitle_WithMissingParameter_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/movie/search/title"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMoviesByReleaseYear_WithMissingParameter_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/movie/search/year"))
                .andExpect(status().isBadRequest());
    }
}
