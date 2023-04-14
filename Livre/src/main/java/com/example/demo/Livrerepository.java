package com.example.demo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Livrerepository extends JpaRepository<Livre, Long> {
	Livre save(Livre a);
	List<Livre> findAll();
	Optional<Livre> findByIsbn(Long isbn);
	
	/*Optional<Livre> findByIsbn(Long isbn);
	Optional<Livre> findByTitreandEditeur(String titre,String editeur);
	List<Livre> findByEdition(String edition);
	List<Livre> findByAuteur(String auteur);*/
}
