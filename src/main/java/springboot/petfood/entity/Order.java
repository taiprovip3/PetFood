package springboot.petfood.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="order_date")
	private Timestamp orderDate;
	
	@Column(name="shipped_date")
	private Timestamp shippedDate;
	
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id")
	private Product product;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Timestamp shippedDate) {
		this.shippedDate = shippedDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order() {
		super();
	}

	public Order(int orderId, String status, Timestamp orderDate, Timestamp shippedDate, int quantity,
			double totalPrice, User user, Product product) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.orderDate = orderDate;
		this.shippedDate = shippedDate;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.user = user;
		this.product = product;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", status=" + status + ", orderDate=" + orderDate + ", shippedDate="
				+ shippedDate + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", user=" + user
				+ ", product=" + product + "]";
	}
}
