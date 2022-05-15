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

import springboot.petfood.entity.*;
import springboot.petfood.service.UserDao;
import springboot.petfood.util.BcryptPasswordEncoderUtil;

@Repository
public class UserDaoJpaImpl implements UserDao{

	private EntityManager entityManager;
	private List<User> users;

	
	@Autowired
	public UserDaoJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public List<User> getAllUser() {
		Query query = entityManager.createQuery("from User");
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public List<User> findAllUser(String username) {
		return null;
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		User u = entityManager.find(User.class, id);
		return u;
	}

	@Override
	@Transactional
	public void removeUserById(int userId) {
		Query query = entityManager.createQuery("delete from User where userId = :USER_ID");
		query.setParameter("USER_ID", userId);
		query.executeUpdate();
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
	public void saveUser(User u, Role r) {

		String encryptedPassword = BcryptPasswordEncoderUtil.encryptPassword(u.getPassword());
		u.setPassword(encryptedPassword);
		u.setRole(r);

		UserRole userRole = new UserRole();
		userRole.setUser(u);
		userRole.setRole(r);

		if(u.getUserId() == 0) {
			entityManager.persist(u);
		} else {
			entityManager.merge(u);
		}
		
		entityManager.persist(userRole);
	}


}
