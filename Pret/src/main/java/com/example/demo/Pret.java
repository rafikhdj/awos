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
import javax.persistence.OneToOne;
import javax.persistence.Table;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "Pret")
public class Pret {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="isbn")
	public Long isbn;
	@Column(name="idclient")
	public Long idclient;
	@Column(name="datepret")
	public String datepret;
	@Column(name="dateretour")
	public String dateretour;
	
	public Pret() {}
	public Pret(Long isbn_livre, Long id_client, String datepret, String dateretour) {
		this.isbn=isbn_livre;
		this.idclient=id_client;
		this.datepret=datepret;
		this.dateretour=dateretour;
	}
	
}
