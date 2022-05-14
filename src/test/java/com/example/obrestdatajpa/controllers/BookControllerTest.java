package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:"+port);
        testRestTemplate =  new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("comprobar metodo findAll()")
    @Test
    void findAll() {
        ResponseEntity<Book[]> response =
        testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(200,response.getStatusCodeValue());

        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    //@Test
    /*void findOneById() {
        ResponseEntity<Book> response =
                testRestTemplate.getForEntity("/api/books/1", Book.class);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        assertEquals(200,response.getStatusCodeValue());
    }*/

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "title": "Eva Schols",
                    "author": "Willhem",
                    "pages": 150,
                    "price": 226.00,
                    "releaseDate": "2008-12-01",
                    "online": true
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books",HttpMethod.POST,request,Book.class);
        Book result = response.getBody();

        assertEquals(1L,result.getId());
        assertEquals("Eva Schols",result.getTitle());
    }
}