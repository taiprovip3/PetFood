package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;

public interface ProductDao {
	
	public Product getProductById(int id);
<<<<<<< HEAD
	public List<Product> findAllProducts(String type);
	public void addProductToCart(Product p, User u, int quantity);
=======
	public List<Product> findAllProduct(String type);
	public void addProductToCart(Product p, User u);//int productId, int userId
>>>>>>> dca74e53a3de640ffb9b0c53101ef90dcc6f5044
	public void checkOutProduct(int productId, int quantity);
	public List<Product> filterProduct(String petType, String categoryName);
	public void addProduct(Product product);
	public void removeProductById(int productId);
}
