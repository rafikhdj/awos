package com.example.demo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Pretrepository extends JpaRepository<Pret, Long> {
	Pret save(Pret a);
	List<Pret> findAll();
	List<Pret> findByIsbnAndIdclient(Long isbn, Long idclient);
	//Long deleteByIsbnandIdclient(Long isbn, Long idclient);
	List<Pret> findByDateretour(String dateretour);
	List<Pret> findByDatepret(String datepret);
	List<Pret> findByIdclient(Long idclient);
}
