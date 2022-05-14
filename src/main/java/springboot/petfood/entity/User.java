package springboot.petfood.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="crypted_password")
	private String password;
	
	private String email;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;

<<<<<<< HEAD
	@OneToMany(mappedBy = "user")
	private List<Cart> carts;
	
=======
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

>>>>>>> ffadcf35dcc459fae72f1303702ae83bd31f5013
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

<<<<<<< HEAD
	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	
	public User() {
	}
=======
	public User() {	}
>>>>>>> ffadcf35dcc459fae72f1303702ae83bd31f5013

	public User(int userId) {
		this.userId = userId;
	}

	public User(int userId, String username, String password, String email, String firstName, String lastName,
			List<Cart> carts) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.carts = carts;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", carts=" + carts + "]";
	}
}