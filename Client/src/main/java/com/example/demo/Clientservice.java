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
	
	public List<Client> getAllinBd(){
		return clientrepository.findAll();
	}
	
	public Client save(String genre,String nom, String prenom, String naissance, String adresse) {
		Client client_to_add = new Client(genre,nom,prenom,naissance,adresse);
		this.clientrepository.save(client_to_add);
		System.out.println("Added Client in DB : ["+client_to_add.get_Id()+"] "+client_to_add.genre+" | "+client_to_add.nom+" | "+client_to_add.prenom+" | "+client_to_add.naissance+" | "+client_to_add.adresse);
		return client_to_add;
	}
	
	public Optional<Client> getClientbyID(Long id) {
		return clientrepository.findById(id);
	}

	public List<Client> getClientbyNomAndPrenom(String nom, String prenom) {
		return this.clientrepository.findByNomAndPrenom(nom, prenom);
	}

	public boolean deleteclient(Long id) {
		Optional<Client> test = clientrepository.findById(id);
		if(test.isEmpty()==true) {
			System.out.println("No Client to delete with this ID !");
			return false;
		}
		Client delete = (Client)test.get();
		this.clientrepository.delete(delete);
		return true;
	}
}
