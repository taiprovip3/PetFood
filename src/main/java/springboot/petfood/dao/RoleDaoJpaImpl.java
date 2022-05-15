package springboot.petfood.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Product;
import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.service.RoleDao;

@Repository
public class RoleDaoJpaImpl implements RoleDao {
	
	private EntityManager entityManager;
	
	@Autowired
	public RoleDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public List<String> getUserRoles(int userId) {
		Query query = entityManager.createNativeQuery("SELECT b.role_name FROM user_roles a JOIN roles b ON a.role_id = b.role_id WHERE a.user_id = :USER_ID");
		query.setParameter("USER_ID", userId);
		return query.getResultList();
    }

	@Override
	public List<Role> getAllRole() {
		Query query = entityManager.createQuery("from Role");
		List<Role> roles = query.getResultList();
		return roles;
	}

	@Override
	public Role getRoleById(int id) {
		Role r = entityManager.find(Role.class, id);
		return r;
	}
}
