package com.th7mo.genre;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
	
	private final GenreRepository genreRepository;
	
	public void post(Genre genre) {
		genreRepository.save(genre);
	}
	
	public List<Genre> getAll() {
		return genreRepository.findAll();
	}
	
	public void delete(Long id) {
		genreRepository.deleteById(id);
	}
}
