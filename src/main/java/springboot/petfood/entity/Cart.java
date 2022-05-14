package springboot.petfood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(CartPK.class)
public class Cart {
	
	@Id
	@Column(name="cart_id")
	private int cartId;

	@Id
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private int quantity;
	private double price;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cart() {
	}

	public Cart(int cartId, Product product, User user, int quantity) {
		this.cartId = cartId;
		this.product = product;
		this.user = user;
		this.quantity = quantity;
		this.price = product.getPrice()*quantity;
	}

	public Cart(Product product, User user, int quantity) {
		this.product = product;
		this.user = user;
		this.quantity = quantity;
		this.price = product.getPrice()*quantity;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", product=" + product + ", user=" + user + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
}