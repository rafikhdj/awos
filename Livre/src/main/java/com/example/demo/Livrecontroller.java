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
	
	@GetMapping("/getbookbyisbn") //ok
    public Optional<Livre> getBookByISBN(@RequestParam Long isbn) {
        return livreservice.getBookByISBN(isbn);
    }
	@GetMapping("/getallbooks") // return all books saved in DB ok
    public List<Livre> getAllinBd() {
        return livreservice.getAllinBd();
    }
	@GetMapping(value = "/undermaintenance") // true if the server is under maintenance ok
	public boolean under_maintenance() {
		return livreservice.under_maintenance();
	}
	@GetMapping(value= "/givemaxonemonthbook") // the most sought-after book this month ok
	public Livre givemaxonemonth() {
		return livreservice.givemaxonemonth();
	}
	@GetMapping(value= "/givefivemaxonemonthbook") //The 5 most sought-after books this month ok
	public List<Livre> givefivemaxonemonth() {
		return livreservice.givefivemaxonemonth();
	}
	@PostMapping(value= "/savebook") // save a book in DB ok
	public Livre save(@RequestParam String auteur,@RequestParam String titre,@RequestParam String editeur,@RequestParam String edition,@RequestParam int quantitedispo) {
		return livreservice.save(auteur,titre,editeur,edition,quantitedispo);
	}
	@PutMapping(value= "/rendubook") //when we give back a book
	public Livre rendubook(@RequestParam Long isbn) {
		return livreservice.rendubook(isbn);
	}
}
