package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Cart;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;

public interface CartDao {
	public List<Cart> getCarts(int userId);
	public void updateCart(Product product, User user, int quantity);
	public void deleteCart(int productId, int userId);
}
