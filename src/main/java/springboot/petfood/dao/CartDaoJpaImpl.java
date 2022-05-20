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
import springboot.petfood.service.CartDao;

@Repository
public class CartDaoJpaImpl implements CartDao{

	private EntityManager entityManager;
	
	@Autowired
	public CartDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Cart> getAllCart() {
		Query query = entityManager.createQuery("from Cart");
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<Cart> getCarts(int userId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM cart WHERE user_id= :userId", Cart.class);
		query.setParameter("userId", userId);
		List<Cart> carts = query.getResultList();
		return carts;
	}

	@Override
	@Transactional
	public void updateCart(Product product, User user, int quantity) {
		entityManager.merge(new Cart(user.getUserId(), product, user, quantity));
	}

	@Override
	@Transactional
	public void deleteCart(int productId, int userId) {
		Query query = entityManager.createNativeQuery("DELETE FROM cart WHERE product_id= :productId and user_id= :userId");
		query.setParameter("productId", productId);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}
}