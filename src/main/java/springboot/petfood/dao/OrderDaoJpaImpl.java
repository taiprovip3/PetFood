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
		Order order = getOrderById(orderId);
		double userBalance = order.getUser().getBalance();
		double totalPrice = order.getTotalPrice();
		Query theQuery1 = entityManager.createNativeQuery("update users set balance = :NEW_BALANCE where user_id = :USER_ID");
		theQuery1.setParameter("NEW_BALANCE", userBalance + totalPrice);
		theQuery1.setParameter("USER_ID", order.getUser().getUserId());
		theQuery1.executeUpdate();
		
		Query theQuery2 = entityManager.createQuery("delete from Order where orderId = :ORDER_ID");
		theQuery2.setParameter("ORDER_ID", orderId);
		theQuery2.executeUpdate();
	}

	@Override
	@Transactional
	public Order getOrderById(int orderId) {
		return entityManager.find(Order.class, orderId);
	}

}
