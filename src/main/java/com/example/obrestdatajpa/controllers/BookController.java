package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final Logger log = LoggerFactory.getLogger(BookController.class);
    //Atributos
    private BookRepository bookRepository;

    //Constructores
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Buscar todos los libros (lista de libros)
    @GetMapping("/api/books")
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    //Buscar un solo libro por ID

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/api/books/{id}")
    @ApiOperation("Buscar un libro por clave primaria id")
    public ResponseEntity<Book> findOneById(@ApiParam("Clave primaria de tipo Long") @PathVariable Long id){
        Optional<Book> bookOpt = bookRepository.findById(id);

        //opcion 1 para devolver
        if(bookOpt.isPresent())
            return ResponseEntity.ok(bookOpt.get());
        else
            return ResponseEntity.notFound().build();

        //opcion 2 para devolver
        /*return bookOpt.orElse(null);*/
    }

    //Crear un nuevo libro

    /**
     *
     * @param book
     * @param headers
     * @return
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));

        if(book.getId() != null){
            log.warn("Trying to create a book with a id");
            System.out.println("Trying to create a book with a id");
            return ResponseEntity.badRequest().build();
        }
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    //Actualizar un libro existente
    /**
     *
     * @param book
     * @return
     */
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book){
        if(book.getId()==null){
            log.warn("Trying to update a non existente book");
            return ResponseEntity.badRequest().build();
        }

        if(!bookRepository.existsById(book.getId())){
            log.warn("Trying to update a non existente book");
            return ResponseEntity.notFound().build();
        }

        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    //Borrar un libro por id

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){

        if(!bookRepository.existsById(id)){
            log.warn("Trying to delete a non existente book");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //Borrar todos los libros
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("REST Reqiest for delete all books");
        bookRepository.deleteAll();
        return  ResponseEntity.noContent().build();
    }

}
