package springboot.petfood.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.entity.UserRole;

@Repository
public class UserDaoJpaImpl {

	private EntityManager entityManager;
	
	@Autowired
	public UserDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	public User findUserAccount(String username) {
        try {
        	Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.username=:USERNAME");
        	query.setParameter("USERNAME", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
	
	@Transactional
	public void saveUser(User u) {
		Role role = new Role();
		role.setRoleId(1);
		role.setNameRole("MEMBER");
		
		u.setUserId(0);
		
		UserRole userRole = new UserRole();
		userRole.setId(0);
		userRole.setUser(u);
		userRole.setRole(role);
		
		entityManager.persist(u);
		entityManager.persist(userRole);
	}
}
