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
	
	public List<Livre> getAllinBd(){ //give all the books in DB
		return livrerepository.findAll();
	}
	
	public Livre save(String auteur,String titre, String editeur, String edition, int quantitedispo) { //save a book in BD
		/*------ Test if exist a Book with same Characteristics -----*/
		Optional<Livre> test = this.livrerepository.findByAuteurAndTitreAndEditeurAndEdition(auteur,titre,editeur,edition);
		if(test.isEmpty()==false) { //already exist in DB
			Livre l = (Livre) test.get();
			System.out.println("Exist already book with this Characteristics! "+"ISBN = "+l.get_isbn());
			//therefore we will just add the quantitedispo
			l.quantitedispo=l.quantitedispo+quantitedispo;
			this.livrerepository.save(l);
			return l;
		} // First Time saved in DB
		Livre livre_to_add = new Livre(auteur,titre,editeur,edition,quantitedispo);
		this.livrerepository.save(livre_to_add);
		System.out.println("Added Book in DB : ["+livre_to_add.get_isbn()+"] "+livre_to_add.auteur+" | "+livre_to_add.titre+" | "+livre_to_add.editeur+" | "+livre_to_add.edition+" | "+livre_to_add.quantitedispo);
		return livre_to_add;
	}

	public Optional<Livre> getBookByISBN(Long isbn) {
		return livrerepository.findById(isbn);
	}

	public Livre rendubook(Long isbn) { //Quand on rend le book -> quantite dispo +1
		Optional<Livre> rendu = livrerepository.findById(isbn);
		if(rendu.isEmpty()==true) {
			System.out.println("Error rendu Book no book with this ISBN !");
			return null;
		}
		Livre l = (Livre) rendu.get();
		l.quantitedispo=l.quantitedispo+1;
		this.livrerepository.save(l);
		return l;
	}
	public Livre fairepret(Long isbn) { // Quand on fait un prêt -> quantite dispo -1
		Optional<Livre> rendu = livrerepository.findById(isbn);
		if(rendu.isEmpty()==true) {
			System.out.println("Error faire pret Book no book with this ISBN !");
			return null;
		}
		Livre l = (Livre) rendu.get();
		l.quantitedispo=l.quantitedispo-1;
		this.livrerepository.save(l);
		return l;
	}

	public boolean deletebook(Long isbn) {
		Optional<Livre> to_delete = this.livrerepository.findById(isbn);
		if(to_delete.isEmpty()==true) {
			System.out.println("Error delete Book no book with this ISBN !");
			return false;
		}
		Livre l = (Livre) to_delete.get();
		this.livrerepository.delete(l);
		return true;
	}
	public List<Livre> getBookByAuteur(String auteur) {
		return livrerepository.findByAuteur(auteur);
	}
	public List<Livre> getBookByTitre(String titre) {
		return livrerepository.findByTitre(titre);
	}
	public List<Livre> getBookByEditeur(String editeur) {
		return livrerepository.findByEditeur(editeur);
	}
	public List<Livre> getBookByEdition(String edition) {
		return livrerepository.findByEdition(edition);
	}
}
