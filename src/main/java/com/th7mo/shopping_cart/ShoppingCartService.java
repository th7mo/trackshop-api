package com.th7mo.shopping_cart;

import com.th7mo.customer.Customer;
import com.th7mo.customer.CustomerRepository;
import com.th7mo.track.Track;
import com.th7mo.track.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
	
	private final CustomerRepository customerRepository;
	private final TrackRepository trackRepository;
	
	public void addTrackToShoppingCart(Long customerId, Long trackId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")
		);
		ShoppingCart shoppingCart = customer.getShoppingCart();
		Track track = trackRepository.findById(trackId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found")
		);
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
		shoppingCartItem.setTrack(track);
		shoppingCart.getItems().add(shoppingCartItem);
		customer.setShoppingCart(shoppingCart);
		customerRepository.save(customer);
	}

	public void deleteTrackFromShoppingCart(Long customerId, Long trackId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		ShoppingCart cart = customer.getShoppingCart();

		ShoppingCartItem item = cart.getItems().stream()
			.filter(i -> i.getTrack().getId().equals(trackId))
			.findFirst()
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not in cart"));

		cart.getItems().remove(item);
		customerRepository.save(customer);
	}

	public void clearShoppingCart(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		ShoppingCart shoppingCart = customer.getShoppingCart();
		shoppingCart.getItems().clear();
		customerRepository.save(customer);
	}
	
}
