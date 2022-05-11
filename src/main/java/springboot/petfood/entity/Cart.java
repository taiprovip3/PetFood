package springboot.petfood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(CartPK.class)
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Cart(int cartId, Product product, User user) {
		this.cartId = cartId;
		this.product = product;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", product=" + product + ", user=" + user + "]";
	}
}