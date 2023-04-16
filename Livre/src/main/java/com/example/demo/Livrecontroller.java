package com.example.demo;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Livrecontroller {
	@Autowired
	private Livreservice livreservice;
	
	@GetMapping("/getbookbyisbn") //Give the Book using the ISBN
    public Optional<Livre> getBookByISBN(@RequestParam Long isbn) {
        return livreservice.getBookByISBN(isbn);
    }
	@GetMapping("/getallbooks") // return all books saved in DB 
    public List<Livre> getAllinBd() {
        return livreservice.getAllinBd();
    }
	@PostMapping(value= "/savebook") // save a book in DB 
	public Livre save(@RequestParam String auteur,@RequestParam String titre,@RequestParam String editeur,@RequestParam String edition,@RequestParam int quantitedispo) {
		return livreservice.save(auteur,titre,editeur,edition,quantitedispo);
	}
	@PostMapping(value= "/rendubook") //when we give back a book -> quantite dispo +1
	public Livre rendubook(@RequestParam Long isbn) {
		return livreservice.rendubook(isbn);
	}
	@PostMapping(value= "/fairepret") // when take a book -> quantite dispo -1
	public Livre fairepret(@RequestParam Long isbn) {
		return livreservice.fairepret(isbn);
	}
	@GetMapping(value="/deletebook") //delete a book in DB
	public boolean deletebook(@RequestParam Long isbn) {
		 return livreservice.deletebook(isbn);
	}
	@GetMapping("/getbookbyauteur")  // All the Books available with this auteur 
    public List<Livre> getBookByAuteur(@RequestParam String auteur) {
        return livreservice.getBookByAuteur(auteur);
    }
	@GetMapping("/getbookbytitre") // All the Books available with this title 
    public List<Livre> getBookByTitre(@RequestParam String titre) {
        return livreservice.getBookByTitre(titre);
    }
	@GetMapping("/getbookbyediteur")  // All the Books available with this editeur 
    public List<Livre> getBookByEditeur(@RequestParam String editeur) {
        return livreservice.getBookByEditeur(editeur);
    }
	@GetMapping("/getbookbyedition")  // All the Books available with this edition 
    public List<Livre> getBookByEdition(@RequestParam String edition) {
        return livreservice.getBookByEdition(edition);
    }
}
