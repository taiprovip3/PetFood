package springboot.petfood.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable{
	
	private int id;
	private int user;
	private int role;
	
	public UserRolePK() {
	}
	
	public UserRolePK(int id, int user, int role) {
		this.id = id;
		this.user = user;
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRolePK other = (UserRolePK) obj;
		return id == other.id && role == other.role && user == other.user;
	}
}
