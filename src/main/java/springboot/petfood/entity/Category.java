package springboot.petfood.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "category_name")
	private String nameCategory;
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getNameCategory() {
		return nameCategory;
	}

	public void setNameCategory(String nameCategory) {
		this.nameCategory = nameCategory;
	}

	public Category() {
		super();
	}

	public Category(int categoryId, String nameCategory) {
		super();
		this.categoryId = categoryId;
		this.nameCategory = nameCategory;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", nameCategory=" + nameCategory + "]";
	}
}
