package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;

public interface ProductDao {
	
	public Product getProductById(int id);
	public List<Product> findAllProducts(String type);
	public void addProductToCart(Product p, User u, int quantity);
	public void checkOutProduct(int productId, int quantity);
	public List<Product> filterProduct(String petType, String categoryName);
}
