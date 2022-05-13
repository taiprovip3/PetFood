package springboot.petfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Cart;
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
	public List<Cart> getCarts(int userId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM cart WHERE user_id= :userId", Cart.class);
		query.setParameter("userId", userId);
		List<Cart> carts = query.getResultList();
		return carts;
	}

}
