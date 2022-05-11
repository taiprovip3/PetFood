package springboot.petfood.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class CartPK implements Serializable{
	
	private int cartId;
	private int product;
	private int user;
	
	public CartPK() {
	}

	public CartPK(int cartId, int product, int user) {
		this.cartId = cartId;
		this.product = product;
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, product, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartPK other = (CartPK) obj;
		return cartId == other.cartId && product == other.product && user == other.user;
	}
}