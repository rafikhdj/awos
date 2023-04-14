package com.example.demo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Clientrepository extends JpaRepository<Client, Long> {
	Client save(Client a);
	List<Client> findAll();
	Optional<Client> findById(Long id);
}
