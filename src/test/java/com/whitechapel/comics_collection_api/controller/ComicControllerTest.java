package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Comic;
import com.whitechapel.comics_collection_api.repository.ComicRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class ComicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComicRepository comicRepository;

    @BeforeEach
    void cleanDb() {
        comicRepository.deleteAll();
    }
    
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetAllComics() throws Exception {

        Comic comic = new Comic();
        comic.setTitle("Batman");
        comic.setIssueNumber(1);
        comic.setYear(2022);
        comicRepository.save(comic);

        mockMvc.perform(get("/api/comics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Batman"));
    }
    @Test
    @WithMockUser(username = "testuser")
    public void testGetAllComics() throws Exception {
        mockMvc.perform(get("/api/comics"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testCreateComic() throws Exception {
        String json = "{ \"title\": \"Test Comic\", \"issueNumber\": 1, \"year\": 2026 }";
        mockMvc.perform(post("/api/comics")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json)
               .with(csrf()))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetComicById() throws Exception {
        // Crea un cómic de prueba
        Comic testComic = new Comic();
        testComic.setTitle("Test ID");
        testComic = comicRepository.save(testComic);

        mockMvc.perform(get("/api/comics/" + testComic.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Test ID"));
    }
}