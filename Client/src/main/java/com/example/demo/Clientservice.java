package com.example.demo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Clientservice {
	@Autowired
	private Clientrepository clientrepository;
	
	public boolean under_maintenance() {
		return Scheduler.maintenance;
	}
	
	public List<Client> getAllinBd(){
		if(Scheduler.maintenance==true) { //check if under maintenance time 
			System.out.println("[!] Server is under maintenance [!] Try again ...");
			return null;
		}
		return clientrepository.findAll();
	}
	public Client save(String genre,String nom, String prenom, String naissance, String adresse) {
		Client client_to_add = new Client(genre,nom,prenom,naissance,adresse);
		this.clientrepository.save(client_to_add);
		System.out.println("Added Client in DB : "+client_to_add.genre+" | "+client_to_add.nom+" | "+client_to_add.prenom+" | "+client_to_add.naissance+" | "+client_to_add.adresse);
		return client_to_add;
	}
	public Client givemaxonemonth() { // The customer who uses the system the most
		List<Client> all_clients = clientrepository.findAll(Sort.by(Sort.Direction.DESC, "numbertimemonth")); 
		if(all_clients.size()==0) {
			System.out.println("No client in the DB !");
			return null;
		}
		Iterator it = all_clients.iterator();
		return (Client) it.next();
	}
	
	public List<Client> givefivemaxonemonth(){ // Same idea : the 5 customers who use the system the most
		List<Client> all_clients = clientrepository.findAll(Sort.by(Sort.Direction.DESC, "numbertimemonth")); 
		if(all_clients.size()<5) {
			System.out.println("Less than 5 clients in the DB !");
			return null;
		}
		Iterator it = all_clients.iterator();
		List<Client> to_return = new ArrayList<Client>();;
		Client var_client;
		int compteur = 0;
		while(compteur<5) { 
			compteur++;
			var_client = (Client) it.next();
			to_return.add(var_client);
		}
		return to_return;
	}

	public Optional<Client> getClientbyID(Long id) {
		return clientrepository.findById(id);
	}
}
