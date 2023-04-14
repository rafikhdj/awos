package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Data;

@Data
@Service
public class Pretservice {
	@Autowired
	private Pretrepository pretrepository;
	
	public List<Pret> getAllinBd(){
		return pretrepository.findAll();
	}
	public Pret retourpret(Long isbn, Long id) {
		
		List<Pret> ensemble_pret = this.pretrepository.findByIsbnAndIdclient(isbn, id);
		if(ensemble_pret.isEmpty()==true) { //This pret doesn't exist yet
			System.out.println("Stop : Ce pret n'existe pas !");
			return null;
		}
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        
        String dateaujourdhui = format.format(date); 
        
        Iterator it = ensemble_pret.iterator();
        Pret a_rendre = (Pret) it.next();
        a_rendre.dateretour = dateaujourdhui;
        this.pretrepository.save(a_rendre);
        
        return a_rendre;
       
	}
	public Pret save(Long isbn, Long id) {
		
		// Port Client -> 3333
		// Port Livre -> 1111
		
		/*Optional test = this.pretrepository.findByIsbnAndIdclient(isbn, id);
		System.out.println(test.isEmpty());
		if(test.isEmpty()==false) { //This pret exist already
			System.out.println("Stop : Ce pret existe déjà :( !");
			return null;
		}*/
		
		String path_client = "http://localhost:3333/getclientbyid?id="+id;
		String path_book = "http://localhost:1111/getbookbyisbn?isbn="+isbn;
		
		URL url;
		HttpURLConnection con;
		BufferedReader buffer_reader;
		String read_line;
		StringBuffer answer_API;
		String finale_answer="";
		System.out.println("--- Demande de pret : ---");
		/*------------------------ Client */
		
		try { //Get Call to Client by using HttpURLConnection
			url = new URL(path_client); 
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			buffer_reader = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			
			answer_API = new StringBuffer(); //StringBuffer where we'll append the answer step by step 
			while ((read_line = buffer_reader.readLine()) != null) {
				answer_API.append(read_line);
			}
			buffer_reader.close(); // will not need the buffer anymore then can close it !
			finale_answer = answer_API.toString(); // Change StringBuffer To String for easy Use
			if(finale_answer.equals("null")) { // Client not found for this ID
				System.out.println("[Error No Client For This ID Try again ...!]");
				return null;
			}
		}catch(Exception e) { // Case Get Call doesn't work -> Exception and we Stop all 
			System.out.println("[Error Get Call API !]"); 
			// can print the status if needed to understand why it doesn't work 
			return null;
		}
		
		JSONObject obj_client = new JSONObject(finale_answer); // Create a JSON Object for Easy Parse 
		
		String genre = obj_client.getString("genre");
		String nom = obj_client.getString("nom");
		String prenom = obj_client.getString("prenom");
		String naissance = obj_client.getString("naissance");
		String adresse = obj_client.getString("adresse");		
		
		Client cl = new Client(genre,nom,prenom,naissance,adresse);
		
		System.out.println("Client : "+cl.genre+" | "+cl.nom+" | "+cl.prenom+" | "+cl.naissance+" | "+cl.adresse);
		
		/*------------------------------------- Livre*/
		
		try { //Get Call to Livre by using HttpURLConnection
			url = new URL(path_book); 
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			buffer_reader = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			
			answer_API = new StringBuffer(); //StringBuffer where we'll append the answer step by step 
			while ((read_line = buffer_reader.readLine()) != null) {
				answer_API.append(read_line);
			}
			buffer_reader.close(); // will not need the buffer anymore then can close it !
			finale_answer = answer_API.toString(); // Change StringBuffer To String for easy Use
			if(finale_answer.equals("null")) { // Client not found for this ID
				System.out.println("[Error No Book For This ISBN Try again ...!]");
				return null;
			}
		}catch(Exception e) { // Case Get Call doesn't work -> Exception and we Stop all 
			System.out.println("[Error Get Call API !]"); 
			// can print the status if needed to understand why it doesn't work 
			return null;
		}
		
		JSONObject obj_book = new JSONObject(finale_answer); // Create a JSON Object for Easy Parse 
		String auteur = obj_book.getString("auteur");
		String titre = obj_book.getString("titre");
		String editeur = obj_book.getString("editeur");
		String edition = obj_book.getString("edition");
		int quantitedispo = Integer.parseInt(obj_book.getString("edition"));
		if(quantitedispo==0) {
			System.out.println("Plus de Livre Dispo :(");
			return null;
		}
		Livre l = new Livre(auteur,titre,editeur,edition,quantitedispo);
		System.out.println("Book : "+l.auteur+" | "+l.titre+" | "+l.editeur+" | "+l.edition+" | "+l.quantitedispo);
		
		/*-------------------- date aujourd'hui*/
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        
        String dateaujourdhui = format.format(date); 
        String dateretour ="not"; // le livre vient d'être pris.
        		
		Pret p = new Pret(isbn,id,dateaujourdhui,dateretour);
		this.pretrepository.save(p);
		System.out.println("Pret : "+" ISBN = "+isbn+" <---> "+" ID Client = "+id);
		System.out.println("--- Fin Pret ---");
		
		return p; //return the response to the Front side 
	}
	public List<Pret> encours() {
		// Pret en cours --> date retour == not
		return this.pretrepository.findByDateretour("not");
	}
	public List<Pret> bydateemprunt(String datepret){ //chercher les prets par date d'emprunt
		return this.pretrepository.findByDatepret(datepret);
	}
	public List<Pret> byidclient(Long id) { // chercher les prets émis par un client x
		return this.pretrepository.findByIdclient(id);
	}

}
