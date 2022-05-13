package springboot.petfood.service;

import java.util.List;

import springboot.petfood.entity.Category;

public interface CategoryDao {
	public List<Category> getAllCategory();
	public Category getCategoryById(int categoryId);
	public void addCategory(Category category);
	public void removeCategoryById(int categoryId);
}
