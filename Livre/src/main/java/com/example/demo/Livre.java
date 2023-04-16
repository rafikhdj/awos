package com.example.demo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "Livre")
public class Livre {
	@Id
	@GeneratedValue
	private Long isbn;
	@Column(name="auteur")
	public String auteur;
	@Column(name="titre")
	public String titre;
	@Column(name="editeur")
	public String editeur;
	@Column(name="edition")
	public String edition;
	@Column(name="quantitedispo")
	public int quantitedispo;
	
	public Livre() {}
	public Livre(String auteur,String titre, String editeur, String edition, int quantitedispo) {
		this.auteur = auteur;
		this.titre = titre;
		this.editeur = editeur;
		this.edition = edition;
		this.quantitedispo = quantitedispo;
	}
	public Long get_isbn() {
		return this.isbn;
	}
}
