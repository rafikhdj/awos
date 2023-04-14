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
public class Clientcontroller {
	@Autowired
	private Clientservice clientservice;
	
	@GetMapping("/getclientbyid") //ok
    public Optional<Client> getClientbyID(@RequestParam Long id) {
        return clientservice.getClientbyID(id);
    }
	@GetMapping("/getallclients") // return all clients saved in DB ok
    public List<Client> getAllinBd() {
        return clientservice.getAllinBd();
    }
	@GetMapping(value= "/givemaxonemonthclient") //the customer who use the system the most ok
	public Client givemaxonemonth() {
		return clientservice.givemaxonemonth();
	}
	@GetMapping(value= "/givefivemaxonemonthclient") //the 5 customers who use the system the most ok
	public List<Client> givefivemaxonemonth() {
		return clientservice.givefivemaxonemonth();
	}
	@PostMapping(value= "/saveclient") // save a client in DB ok
	public Client save(@RequestParam String genre,@RequestParam String nom,@RequestParam String prenom,@RequestParam String naissance,@RequestParam String adresse) {
		return clientservice.save(genre,nom,prenom,naissance,adresse);
	}
}
