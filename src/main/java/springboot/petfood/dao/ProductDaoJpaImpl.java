package springboot.petfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Cart;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.service.ProductDao;

@Repository
public class ProductDaoJpaImpl implements ProductDao{
	
	private EntityManager entityManager;
	private List<Product> products;
	
	@Autowired
	public ProductDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public Product getProductById(int id) {
		Product p = entityManager.find(Product.class, id);
		return p;
	}
	
	@Override
	@Transactional
	public List<Product> findAllProducts(String type) {
		if(type.equals(" ") || type.equals(null))
			type = "ALL";
		if(type.equals("ALL")) {
			Query query = entityManager.createQuery("SELECT p FROM Product p WHERE p.type = 'DOG' OR p.type = 'CAT' OR p.type = 'ALL'");
			products = query.getResultList();
		} else {
			Query query = entityManager.createQuery("SELECT p FROM Product p WHERE p.type = :productType");
			query.setParameter("productType", type);
			products = query.getResultList();
		}
		return products;
	}

	@Override
	@Transactional
	public void addProductToCart(Product p, User u) {//int productId, int userId
//		Product product = new Product();
//		product.setProductId(p.getProductId());
//		User user = new User();
//		user.setUserId(u.getUserId());
//		Cart cart = new Cart();
//		cart.setProduct(p);
//		cart.setUser(u);
//		System.out.println("---PRODUCT " + p + "---USER " + u);
//		entityManager.persist(cart);
	}

	@Override
	@Transactional
	public void checkOutProduct(int productId, int quantity) {
		Product product = entityManager.find(Product.class, productId);
		product.setQuantity(product.getQuantity() - quantity);
		entityManager.merge(product);
	}

	@Override
	public List<Product> filterProduct(String petType, String categoryName) {
		if(petType.equals("ALL") && categoryName.equals("ALL")) {
			products = findAllProducts("ALL");
		} else {
			if(petType.equals("ALL") && !categoryName.equals("ALL")) {
				Query query = entityManager.createNativeQuery("SELECT * FROM products p JOIN categories c ON p.category_id = c.category_id WHERE p.product_type = :petType1 AND c.category_name = :categoryName OR p.product_type = :petType2 AND c.category_name = :categoryName", Product.class);
				query.setParameter("petType1", "DOG");
				query.setParameter("categoryName", categoryName);
				query.setParameter("petType2", "CAT");
				products = query.getResultList();
			}
			if(!petType.equals("ALL") && categoryName.equals("ALL")) {
				Query query = entityManager.createNativeQuery("SELECT * FROM products p JOIN categories c ON p.category_id = c.category_id WHERE p.product_type = :petType", Product.class);
				query.setParameter("petType", petType);
				products = query.getResultList();
			}
			if(!petType.equals("ALL") && !categoryName.equals("ALL")) {
				Query query = entityManager.createNativeQuery("SELECT * FROM products p JOIN categories c ON p.category_id = c.category_id WHERE p.product_type = :petType AND c.category_name = :nameCategory", Product.class);
				query.setParameter("petType", petType);
				query.setParameter("nameCategory", categoryName);
				products = query.getResultList();
			}
		}
		return products;
	}

}
