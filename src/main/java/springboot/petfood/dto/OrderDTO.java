package springboot.petfood.dto;

import java.sql.Timestamp;

public class OrderDTO {
	private int orderId;
	private String status;
	private Timestamp orderDate;
	private Timestamp shippedDate;
	private String address;
	private int quantity;
	private double totalPrice;
	private int userId;
	private int productId;
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
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", status=" + status + ", orderDate=" + orderDate + ", shippedDate="
				+ shippedDate + ", address=" + address + ", quantity=" + quantity + ", totalPrice=" + totalPrice
				+ ", userId=" + userId + ", productId=" + productId + "]";
	}
}
