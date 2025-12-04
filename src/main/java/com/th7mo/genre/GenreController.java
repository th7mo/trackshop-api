package com.th7mo.genre;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
	
	private final GenreService genreService;
	
	@PostMapping
	public void post(@RequestBody Genre genre) {
		genreService.post(genre);
	}
	
	@GetMapping
	public List<Genre> getAll() {
		return genreService.getAll();
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		genreService.delete(id);
	}
}
