package springboot.petfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Category;
import springboot.petfood.service.CategoryDao;

@Repository
public class CategoryDaoJpaImpl implements CategoryDao{

	private EntityManager entityManager;
	private List<Category> categories;
	
	@Autowired
	public CategoryDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public List<Category> getAllCategory() {
		Query query = entityManager.createQuery("from Category");
		List<Category> categories = query.getResultList();
		return categories;
	}

	@Override
	@Transactional
	public Category getCategoryById(int categoryId) {
		Category category = entityManager.find(Category.class, categoryId);
		return category;
	}
	
	@Override
	@Transactional
	public void addCategory(Category category) {
		Category c = entityManager.merge(category);
	}

	@Override
	@Transactional
	public void removeCategoryById(int categoryId) {
		Query query = entityManager.createQuery("delete from Category where categoryId = :CATEGORY_ID");
		query.setParameter("CATEGORY_ID", categoryId);
		query.executeUpdate();
	}

}
