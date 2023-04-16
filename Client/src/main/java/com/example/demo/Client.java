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
@Table(name = "Client")
public class Client {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="genre")
	public String genre;
	@Column(name="nom")
	public String nom;
	@Column(name="prenom")
	public String prenom;
	@Column(name="naissance")
	public String naissance;
	@Column(name="adresse")
	public String adresse;
	
	public Client() {}
	public Client(String genre,String nom, String prenom, String naissance, String adresse) {
		this.genre = genre;
		this.nom = nom;
		this.prenom = prenom;
		this.naissance = naissance;
		this.adresse = adresse;
	}
	public Long get_Id() {
		return this.id;
	}
}
