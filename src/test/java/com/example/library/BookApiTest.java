package com.example.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void listsBooks() throws Exception {
        mvc.perform(get("/api/books"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createsABook() throws Exception {
        String json = """
            {"title":"Test Book","author":"Test Author","isbn":"TEST-ISBN-1","publishedYear":2020}
            """;
        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().exists("Location"))
           .andExpect(jsonPath("$.title").value("Test Book"))
           .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void rejectsInvalidBook() throws Exception {
        mvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"\",\"author\":\"\",\"isbn\":\"\"}"))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.errors.title").exists());
    }

    @Test
    void returns404ForUnknownBook() throws Exception {
        mvc.perform(get("/api/books/9999"))
           .andExpect(status().isNotFound());
    }
}
