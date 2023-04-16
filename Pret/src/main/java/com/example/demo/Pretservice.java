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
		
		List<Pret> ensemble_pret = this.pretrepository.findByIsbnAndIdclient(isbn, id); //les prêts pour ce livre et ce client 
		if(ensemble_pret.isEmpty()==true) { //This pret doesn't exist yet
			System.out.println("Stop : pas de Pret avec ce Livre et ce Client !");
			return null;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        
        String dateaujourdhui = format.format(date); 
        
        Iterator it = ensemble_pret.iterator();
        Pret a_rendre = null ; 
        boolean find_it = false;
        while(it.hasNext()) {
        	a_rendre = (Pret) it.next();
        	if(a_rendre.dateretour.equals("not")){
        		find_it = true;
        		break;
        	}
        }
        if(find_it==false) {
        	System.out.println("Tous les Prêts de ce Livre ont déjà été rendu :)");
        	return null;
        }
        a_rendre.dateretour = dateaujourdhui;
        this.pretrepository.save(a_rendre); //date retour de ce pret maintenant aujourd'hui
        
        /*------------------ Livre rendu -> quantite dispo = quantite dispo + 1 */
        
        URL url;
		HttpURLConnection con;
		BufferedReader buffer_reader;
		String read_line;
		StringBuffer answer_API;
		String finale_answer="";
		
		String path_retour = "http://localhost:1111/rendubook?isbn="+isbn;
		
		try { // même principe que sur save : commenté plus bas 
			url = new URL(path_retour); 
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			buffer_reader = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			
			answer_API = new StringBuffer(); 
			while ((read_line = buffer_reader.readLine()) != null) {
				answer_API.append(read_line);
			}
			buffer_reader.close(); // will not need the buffer anymore then can close it !
			finale_answer = answer_API.toString(); // Change StringBuffer To String for easy Use
			if(finale_answer.equals("null")) { // Book not found for this ISBN
				System.out.println("[Error No Book For This ISBN Try again ...!]");
				return null;
			}
		}catch(Exception e) { // Case doesn't work -> Exception and we Stop all 
			System.out.println("[Error Retour Livre  !]"); 
			// can print the status if needed to understand why it doesn't work 
			return null;
		}
        
        /*---------------------*/
        
        return a_rendre;
       
	}
	public Pret save(Long isbn, Long id) { // faire un pret 
		
		// Port Client -> 3333
		// Port Livre -> 1111
		
		
		String path_client = "http://localhost:3333/getclientbyid?id="+id;
		String path_book = "http://localhost:1111/getbookbyisbn?isbn="+isbn;
		
		URL url;
		HttpURLConnection con;
		BufferedReader buffer_reader;
		String read_line;
		StringBuffer answer_API;
		String finale_answer="";
		System.out.println("--- Demande de pret : ---");
		
		/*------------------------ Cherche le Client en appelant le Microservice Client */
		
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
			System.out.println("[Error Pret Client !]"); 
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
		
		/*------------------------------------- Cherche le Livre en appelant Livre */
		
		try { //Get Call to Livre by using HttpURLConnection same than for Client 
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
			if(finale_answer.equals("null")) { // No book with this ISBN
				System.out.println("[Error No Book For This ISBN Try again ...!]");
				return null;
			}
		}catch(Exception e) { // Case Get Call doesn't work -> Exception and we Stop all 
			System.out.println("[Error Pret Livre Call  !]"); 
			// can print the status if needed to understand why it doesn't work 
			return null;
		}
		
		JSONObject obj_book = new JSONObject(finale_answer); // Create a JSON Object for Easy Parse 
		String auteur = obj_book.getString("auteur");
		String titre = obj_book.getString("titre");
		String editeur = obj_book.getString("editeur");
		String edition = obj_book.getString("edition");
		int quantitedispo = obj_book.getInt("quantitedispo");
		if(quantitedispo<=0) { //check si y'a des livres dispo pour cet isbn 
			System.out.println("Plus de Livre avec cet ISBN de Dispo :( Try Again ...");
			return null;
		}
		Livre l = new Livre(auteur,titre,editeur,edition,quantitedispo);
		System.out.println("Book : "+l.auteur+" | "+l.titre+" | "+l.editeur+" | "+l.edition+" | "+l.quantitedispo);
		
		/*---------------------------- on fait un prêt donc quantite dispo = quantite dispo - 1 pour cet ISBN */
		
		String path_rajout = "http://localhost:1111/fairepret?isbn="+isbn;
		
		try { // same than before
			url = new URL(path_rajout); 
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			buffer_reader = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			
			answer_API = new StringBuffer(); 
			while ((read_line = buffer_reader.readLine()) != null) {
				answer_API.append(read_line);
			}
			buffer_reader.close(); 
			finale_answer = answer_API.toString(); 
			if(finale_answer.equals("null")) { // Book not found for this ISBN
				System.out.println("[Error No Book For This ISBN Try again ...!]");
				return null;
			}
		}catch(Exception e) { // Case doesn't work -> Exception and we Stop all 
			System.out.println("[Error Pret Livre quantité - 1 !]"); 
			// can print the status if needed to understand why it doesn't work 
			return null;
		}
		
		/*-------------------- date emprunt = date aujourd'hui*/
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        
        String dateaujourdhui = format.format(date); 
        String dateretour ="not"; // le livre vient d'être pris donc retour = not
        		
		Pret p = new Pret(isbn,id,dateaujourdhui,dateretour);
		this.pretrepository.save(p);
		System.out.println("Pret : ["+p.get_id()+"] "+" ISBN = "+isbn+" <---> "+" ID Client = "+id+" | "+dateaujourdhui);
		System.out.println("--- Fin Pret ---");
		
		return p; 
	}
	public List<Pret> encours() {
		// Pret en cours --> date retour == not
		return this.pretrepository.findByDateretour("not");
	}
	public List<Pret> bydateemprunt(String datepret){ //chercher les prets par date d'emprunt
		return this.pretrepository.findByDatepret(datepret);
	}
	public List<Pret> byidclient(Long id) { // chercher les prets par IDclient
		return this.pretrepository.findByIdclient(id);
	}
	public boolean delete(Long id) {
		Optional<Pret> test = this.pretrepository.findById(id);
		if(test.isEmpty()==true) {
			System.out.println("No Pret to Delete With This ID");
			return false;
		}
		Pret delete = (Pret) test.get();
		this.pretrepository.delete(delete);
		return true;
	}

}
