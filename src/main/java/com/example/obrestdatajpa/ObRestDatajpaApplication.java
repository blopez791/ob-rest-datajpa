package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//crear libro
		Book book1 = new Book(null, "Tiempos Recios", "Vargas Llosa", 345, 245.00, LocalDate.of(2018,12,1),true);
		Book book2 = new Book(null, "Las Particulas", "Huelbeqc", 340, 180.00, LocalDate.of(2009,10,07),true);
		System.out.println("Num libros antes de insertar: "+repository.findAll().size());

		//almacenar libro
		repository.save(book1);
		repository.save(book2);
		System.out.println("Num libros despues de insertar: "+repository.findAll().size());

		//recuperar todos los libros
		System.out.println(repository.findAll().size());

		//borrar un libro
		//repository.deleteById(1L);
		System.out.println("Num libros despues de borrar: "+repository.findAll().size());
	}

}
