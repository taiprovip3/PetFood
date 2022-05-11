package springboot.petfood.dao;

import java.util.List;

import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;

public interface ProductDao {
	
	public Product getProductById(int id);
	public List<Product> findAllProducts(String type);
	public void addProductToCart(int productId, int userId);
	public void checkOutProduct(int productId, int quantity);
	public List<Product> filterProduct(String petType, String categoryName);
}
