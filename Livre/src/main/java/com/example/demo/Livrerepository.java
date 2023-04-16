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
	Optional<Livre> findById(Long isbn);
	void delete(Livre a);
	Optional<Livre> findByAuteurAndTitreAndEditeurAndEdition(String Auteur,String Titre, String Editeur, String Edition);
	List<Livre> findByAuteur(String Auteur);
	List<Livre> findByTitre(String Titre);
	List<Livre> findByEditeur(String Editeur);
	List<Livre> findByEdition(String Edition);
}
