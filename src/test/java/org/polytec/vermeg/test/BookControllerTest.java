package org.polytec.vermeg.test;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.polytec.vermeg.controller.BookController;
import org.polytec.vermeg.model.Book;
import org.polytec.vermeg.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class BookControllerTest {
	Date d = new Date (2020, 11,11);
	
	@Mock
	private BookService bookservice;
	@InjectMocks
	private BookController bookcontroller;
	@Autowired
	private MockMvc mockmvc;
	private BookController bookcon;

	@BeforeEach
	void setUp() throws Exception {		
		 MockitoAnnotations.initMocks(this);			
		 mockmvc = MockMvcBuilders.standaloneSetup(bookcontroller).build();	
		 bookcon=new BookController();
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetBooks() throws Exception {
		List<Book> livres = new ArrayList<Book>() ;
		
		livres.add(new Book(1,"a","a",1,d));
		livres.add(new Book(1,"a","a",1,d));
		when(bookservice.getAllBooks()).thenReturn((List)livres ) ;
		 
			mockmvc.perform(get("/api/personne/getAllBook"))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$", hasSize(2) ))
			 .andDo(print()) ;
		
		
		
		
		
		//fail("Not yet implemented");
	}

	@Test
	void testaddBook() {
		
		
		Book b =new  Book(1,"a","a",1,d);
		bookservice.addBook(b);
		verify(bookservice, times(1)).addBook(b);
		//fail("Not yet implemented");
	}

	@Test
	void testGetBookById() throws Exception {
		Book b= new Book(1, "jhon", "jack", 5, d);
		long idBook=b.getId();
		when(bookservice.getBook(idBook)).thenReturn(b);
		this.mockmvc.perform(get("/api/personne/getBook/"+idBook))
		.andExpect(status().isOk())
		.andDo(print());
	}

	@Test
	void testAddBook() throws Exception {
		Book b= new Book(1, "jhon", "jack", 5, d);
		bookservice.addBook(b);;
		   verify(bookservice, times(1)).addBook(b);
	
	 this.mockmvc.perform(post("/api/personne/addBook")).andExpect(status().isOk()).andDo(print());	}

	@Test
	void testUpdateBook() throws Exception {
		Book b= new Book(1, "jhon", "jack", 5, d);
		Book b1= new Book(1, "fly me to the moon", "forbs", 6, d);
		long idBook =b.getId();
		bookservice.updateBook(b);
		verify(bookservice, times(1)).updateBook(b);
		 this.mockmvc.perform(put("/api/personne/updateBook/"+idBook)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	void testDeleteBook() throws Exception {
		Book b= new Book(1, "jhon", "jack", 5, d);
		long idBook =b.getId();
		bookservice.deleteBook(idBook);
	verify(bookservice, times(1)).deleteBook(idBook);
    this.mockmvc.perform(delete("/api/personne/deleteBook/"+idBook)).andExpect(status().isOk()).andDo(print());
		}
	
	@Test
	void testCalSommePrixTotal() throws Exception {
		Book b= new Book(1, "a", "a", 5, d);
		Book b0= new Book(2, "b", "b", 10, d);
		Book b1= new Book(3, "c", "c", 15, d);
		List <Long> liste = new ArrayList <Long>();
		liste.add(b.getId());
		liste.add(b0.getId());
		liste.add(b1.getId());
		
		this.mockmvc.perform(post("/api/personne/calSommePrixTotal")).andExpect(status().isOk()).andDo(print());
		
		assertEquals(bookcon.calSommePrixTotal(liste),30.0);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
