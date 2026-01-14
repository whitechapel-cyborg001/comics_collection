package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Comic;
import com.whitechapel.comics_collection_api.repository.ComicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ComicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ComicRepository comicRepository;

    @Test
    public void testGetAllComics() throws Exception {
        mockMvc.perform(get("/api/comics"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateComic() throws Exception {
        String json = "{ \"title\": \"Test Comic\", \"issueNumber\": 1, \"year\": 2026 }";
        mockMvc.perform(post("/api/comics")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk());
    }

    // Añade más tests si quieres, e.g., para GET by ID
    @Test
    public void testGetComicById() throws Exception {
        // Primero crea un cómic para probar
        Comic testComic = new Comic();
        testComic.setTitle("Test");
        testComic = comicRepository.save(testComic);

        mockMvc.perform(get("/api/comics/" + testComic.getId()))
               .andExpect(status().isOk());
    }
}