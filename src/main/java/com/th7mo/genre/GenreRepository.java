package com.th7mo.genre;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface GenreRepository extends Repository<Genre, Long> {
	
	void save(Genre genre);
	List<Genre> findAll();
	void deleteById(Long id);
}
