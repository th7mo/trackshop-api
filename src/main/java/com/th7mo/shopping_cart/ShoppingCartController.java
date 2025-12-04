package com.th7mo.shopping_cart;

import com.th7mo.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers/{customerId}/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
	
	private final ShoppingCartService shoppingCartService;
	
	@PostMapping("/items/{trackId}")
	public void addTrackToShoppingCartById(@PathVariable Long customerId, @PathVariable Long trackId) {
		shoppingCartService.addTrackToShoppingCart(customerId, trackId);
	}
	
	@DeleteMapping("/items/{trackId}")
	public void deleteTrackFromShoppingCartById(@PathVariable Long customerId, @PathVariable Long trackId) {
		shoppingCartService.deleteTrackFromShoppingCart(customerId, trackId);
	}
	
	@DeleteMapping
	public void clearShoppingCart(@PathVariable Long customerId) {
		shoppingCartService.clearShoppingCart(customerId);
	}
}
