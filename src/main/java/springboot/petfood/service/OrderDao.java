package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Order;

public interface OrderDao {
	public void saveOrder(Order order);
	public List<Order> getAllOrder();
	public Order getOrderById(int orderId);
	public void destroyOrderById(int orderId);
}
