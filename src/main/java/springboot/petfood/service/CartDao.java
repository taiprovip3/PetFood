package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Cart;

public interface CartDao {
	public List<Cart> getCarts(int userId);
}
