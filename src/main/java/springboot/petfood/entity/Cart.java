package springboot.petfood.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private int cartId;

	@Column(name="product_id")
	private int productId;
	
	@Column(name="user_id")
	private int user_id;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Cart() {
		super();
	}

	public Cart(int cartId, int productId, int user_id) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", productId=" + productId + ", user_id=" + user_id + "]";
	}

	
}
