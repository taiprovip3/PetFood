package springboot.petfood.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.entity.UserRole;
import springboot.petfood.util.BcryptPasswordEncoderUtil;

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
	
		String encryptedPassword = BcryptPasswordEncoderUtil.encryptPassword(u.getPassword());
		u.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setNameRole("MEMBER");
		
		UserRole userRole = new UserRole();
		userRole.setUser(u);
		userRole.setRole(role);
		
		entityManager.persist(u);
		entityManager.persist(userRole);
	}
}
