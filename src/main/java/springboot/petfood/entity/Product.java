package springboot.petfood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private int productId;
	
	@Column(name="product_name")
	private String name;
	
	@Column(name="product_type")
	private String type;
	
	@Column(name="product_description")
	private String description;
	
	@Column(name="product_price")
	private double price;
	
	@Column(name="product_weight")
	private double weight;
	
	@Column(name="product_quantity")
	private int quantity;
	
	@Column(name="product_image")
	private String image;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<Cart> carts;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Product() {
	}

	public Product(int productId) {
		this.productId = productId;
	}

	public Product(int productId, String name, String type, String description, double price, double weight,
			int quantity, String image, Category category, List<Cart> carts) {
		this.productId = productId;
		this.name = name;
		this.type = type;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.quantity = quantity;
		this.image = image;
		this.category = category;
		this.carts = carts;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", type=" + type + ", description=" + description
				+ ", price=" + price + ", weight=" + weight + ", quantity=" + quantity + ", image=" + image
				+ ", category=" + category + "]";
	}
}