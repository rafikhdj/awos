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
public class Livreservice {
	@Autowired
	private Livrerepository livrerepository;
	
	public boolean under_maintenance() {
		return Scheduler.maintenance;
	}
	
	public List<Livre> getAllinBd(){
		if(Scheduler.maintenance==true) { //check if under maintenance time 
			System.out.println("[!] Server is under maintenance [!] Try again ...");
			return null;
		}
		return livrerepository.findAll();
	}
	public Livre save(String auteur,String titre, String editeur, String edition, int quantitedispo) {
		Livre livre_to_add = new Livre(auteur,titre,editeur,edition,quantitedispo);
		this.livrerepository.save(livre_to_add);
		System.out.println("Added Book in DB : "+livre_to_add.auteur+" | "+livre_to_add.titre+" | "+livre_to_add.editeur+" | "+livre_to_add.edition);
		return livre_to_add;
	}
	public Livre givemaxonemonth() { // the most sought-after book this month 
		List<Livre> all_livres = livrerepository.findAll(Sort.by(Sort.Direction.DESC, "numbertimemonth")); 
		if(all_livres.size()==0) {
			System.out.println("No book in the DB !");
			return null;
		}
		Iterator it = all_livres.iterator();
		return (Livre) it.next();
	}
	
	public List<Livre> givefivemaxonemonth(){ //the 5 most sought-after book this month 
		List<Livre> all_livres = livrerepository.findAll(Sort.by(Sort.Direction.DESC, "numbertimemonth")); 
		if(all_livres.size()<5) {
			System.out.println("Less than 5 books in the DB !");
			return null;
		}
		Iterator it = all_livres.iterator();
		List<Livre> to_return = new ArrayList<Livre>();;
		Livre var_livre;
		int compteur = 0;
		while(compteur<5) { 
			compteur++;
			var_livre = (Livre) it.next();
			to_return.add(var_livre);
		}
		return to_return;
	}

	public Optional<Livre> getBookByISBN(Long isbn) {
		return livrerepository.findById(isbn);
	}

	public Livre rendubook(Long isbn) {
		Optional<Livre> rendu = livrerepository.findById(isbn);
		if(rendu.isEmpty()==true) {
			System.out.println("Error rendu Book no book with this ISBN");
		}
		Livre l = (Livre) rendu.get();
		l.quantitedispo=l.quantitedispo+1;
		return l;
	}
}
