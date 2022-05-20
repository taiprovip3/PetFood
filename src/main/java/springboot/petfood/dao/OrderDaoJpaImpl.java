package springboot.petfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Order;
import springboot.petfood.service.OrderDao;

@Repository
public class OrderDaoJpaImpl implements OrderDao{

	private EntityManager entityManager;
	
	public OrderDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public void saveOrder(Order order) {
		entityManager.merge(order);
	}

	@Override
	@Transactional
	public List<Order> getAllOrder() {
		Query query = entityManager.createQuery("from Order");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void destroyOrderById(int orderId) {
		Query theQuery = entityManager.createQuery("delete from Order where orderId = :ORDER_ID");
		theQuery.setParameter("ORDER_ID", orderId);
		theQuery.executeUpdate();
	}

	@Override
	@Transactional
	public Order getOrderById(int orderId) {
		return entityManager.find(Order.class, orderId);
	}

}
