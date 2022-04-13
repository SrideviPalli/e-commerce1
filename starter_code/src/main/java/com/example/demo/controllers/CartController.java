package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

@RestController
//RestController is used for making restful web services with the help of the @RestController annotation
@RequestMapping("/api/cart")
// It is used to map web requests onto specific handler classes and/or handler methods
public class CartController {

	public static final Logger log;
//creating a logger method
	static {
		log = LoggerFactory.getLogger(UserController.class);
	}

	@Autowired
	// @Autowired annotation is used for dependency injection
	private UserRepository userRepository;
	
	@Autowired
	// @Autowired annotation is used for dependency injection
	private CartRepository cartRepository;
	
	@Autowired
	// @Autowired annotation is used for dependency injection
	private ItemRepository itemRepository;
	private ModifyCartRequest request;

	@PostMapping("/addToCart")
	//@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		this.request = request;
		//calling info method
		log.info("Request addToCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
		User user;
		user = userRepository.findByUsername(request.getUsername());
		if (user != null) {
			Optional<Item> item = itemRepository.findById(request.getItemId());
			if (item.isEmpty()) {
				//calling info method
				log.error("Request addToCart failure. Error with user name {}", request.getUsername());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Cart cart = user.getCart();
			int bound = request.getQuantity();
			for (int i = 0; i < bound; i++) {
				cart.addItem(item.get());
			}
			cartRepository.save(cart);
			//calling info method
			log.info("Request addToCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
			ResponseEntity<Cart> ok;
			ok = ResponseEntity.ok(cart);
			return ok;
		} else {
			log.error("Request addToCart failure. Error with user name {}", request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/removeFromCart")
	//@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping

	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		this.request = request;
		log.info("Request removeFromCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
		//calling info method
		User user;
		user = userRepository.findByUsername(request.getUsername());
		if (user != null) {
			Optional<Item> item = itemRepository.findById(request.getItemId());
			if (item.isPresent()) {
				Cart cart = user.getCart();
				int bound = request.getQuantity();
				for (int i = 0; i < bound; i++) {
					cart.removeItem(item.get());
				}
				cartRepository.save(cart);
				//calling info method
				log.info("Request removeFromCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
				ResponseEntity<Cart> ok;
				ok = ResponseEntity.ok(cart);
				return ok;
			} else {
				log.error("Request removeFromCart failure. Error with user name {}", request.getUsername());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			log.error("Request removeFromCart failure. Error with user name {}", request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
		
}
