package com.th7mo.track;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

public interface TrackRepository extends Repository<Track, Long> {
	
	void save(Track track);
	List<Track> findAll();
	Optional<Track> findById(long id);
	void deleteById(long id);
}
