package com.th7mo.auth;

import java.util.ArrayList;
import java.util.List;

import com.th7mo.customer.Customer;
import com.th7mo.customer.CustomerRepository;
import com.th7mo.customer.Role;
import com.th7mo.shopping_cart.ShoppingCart;
import com.th7mo.track.Track;
import com.th7mo.track.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final TrackRepository trackRepository;

	@Override
	public void run(String... args) {
		if (customerRepository.findByUsername("admin").isEmpty()) {

			Customer admin = new Customer();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("theverysecretadminpassword"));
			admin.setRole(Role.ADMIN);
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setItems(new ArrayList<>());
			admin.setShoppingCart(shoppingCart);

			customerRepository.save(admin);

			System.out.println("Admin account created.");
		} else {
			System.out.println("Admin already exists. Skipping seeding.");
		}
		
		if (trackRepository.findAll().isEmpty()) {
			List<Track> seedTracks = new ArrayList<>();

			seedTracks.add(createTrack("Now or Never", "Josh Butler", "House", 211, 2.49f));
			seedTracks.add(createTrack("Losing It", "Fisher", "Tech House", 246, 2.99f));
			seedTracks.add(createTrack("Turn Back Time", "Diplo & Sonny Fodera", "House", 223, 2.79f));
			seedTracks.add(createTrack("Insomnia", "Faithless", "Trance", 221, 1.99f));
			seedTracks.add(createTrack("Cola", "CamelPhat & Elderbrook", "Deep House", 257, 2.89f));
			seedTracks.add(createTrack("Your Love", "Mark Knight", "House", 255, 2.49f));
			seedTracks.add(createTrack("Pump It Up", "Endor", "House", 171, 1.99f));
			seedTracks.add(createTrack("Piece of Your Heart", "Meduza", "House", 153, 2.59f));
			seedTracks.add(createTrack("Doomsday", "MF DOOM", "Hip Hop", 223, 3.29f));
			seedTracks.add(createTrack("Breathe", "CamelPhat & Cristoph", "Progressive House", 219, 2.99f));

			for (Track track : seedTracks) {
				trackRepository.save(track);

			}

			System.out.println("Seeded 10 example tracks.");
		}
	}

	private Track createTrack(String title, String artist, String genre, int duration, float price) {
		Track track = new Track();
		track.setTitle(title);
		track.setArtistName(artist);
		track.setGenre(genre);
		track.setDurationInSeconds(duration);
		track.setPriceInEuros(price);
		return track;
	}
}
