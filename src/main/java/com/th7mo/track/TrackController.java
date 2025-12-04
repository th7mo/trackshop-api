package com.th7mo.track;

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
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {
	
	private final TrackService trackService;
	
	@PostMapping
	public void postTrack(@RequestBody Track track) {
		trackService.postTrack(track);
	}

	@DeleteMapping("/{id}")
	public void deleteTrack(@PathVariable Long id) {
		trackService.deleteById(id);
	}
	
	@GetMapping
	public List<Track> getTracks() {
		return trackService.getAllTracks();
	}
}
