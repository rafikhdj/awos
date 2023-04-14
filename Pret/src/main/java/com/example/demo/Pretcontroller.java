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
public class Pretcontroller {
	@Autowired
	private Pretservice pretservice;

	@GetMapping("/getallprets") // return all prets saved in DB ok
    public List<Pret> getAllinBd() {
        return pretservice.getAllinBd();
    }
	@PostMapping(value= "/fairepret") // faire un pret
	public Pret fairepret(@RequestParam Long isbn,@RequestParam Long id) {
		return pretservice.save(isbn,id);
	}
	@PostMapping(value= "/retourpret") // retour un pret
	public Pret retourpret(@RequestParam Long isbn,@RequestParam Long id) {
		return pretservice.retourpret(isbn,id);
	}
	@GetMapping("/encours") // pret en cours 
	public List<Pret> encours(){
		return pretservice.encours();
	}
	@GetMapping("/bydateemprunt") // chercher prets par date emprunt
	public List<Pret> bydateemprunt(@RequestParam String datepret){
		return pretservice.bydateemprunt(datepret);
	}
	@GetMapping("/byidclient") // chercher prets par date emprunt
	public List<Pret> byidclient(@RequestParam Long id){
		return pretservice.byidclient(id);
	}
}
