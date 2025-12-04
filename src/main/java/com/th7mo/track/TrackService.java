package com.th7mo.track;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {
	
	private final TrackRepository trackRepository;
	
	public void postTrack(Track track) {
		trackRepository.save(track);
	}
	
	public List<Track> getAllTracks() {
		return trackRepository.findAll();
	}
	
	public void deleteById(Long id) {
		trackRepository.deleteById(id);
	}
}
