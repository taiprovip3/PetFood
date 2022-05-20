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
		Query query = entityManager.createQuery("DELETE FROM UserRole ur WHERE ur.user.userId = :USER_ID");
		query.setParameter("USER_ID", userId);
		query.executeUpdate();
		Query query2 = entityManager.createQuery("delete from User where userId = :USER_ID");
		query2.setParameter("USER_ID", userId);
		query2.executeUpdate();
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
	public void saveUser(User u, Role r, String oldPassword) {
		
		u.setRole(r);
		String password = u.getPassword();
		
		if(u.getUserId() == 0) {//TH addUser
			if(password.isEmpty()) {//TH bỏ trống input Password
				String passwordGenerated = BcryptPasswordEncoderUtil.encryptPassword("123123az");
				u.setPassword(passwordGenerated);
			} else {//TH có nhập password
				String encryptedPassword = BcryptPasswordEncoderUtil.encryptPassword(password);
				u.setPassword(encryptedPassword);
			}
			entityManager.persist(u);
		} else {//TH updateUser
			if(password.isEmpty()) {//TH bỏ trống input Password
				u.setPassword(oldPassword);
			} else {
				String encryptedPassword = BcryptPasswordEncoderUtil.encryptPassword(password);
				u.setPassword(encryptedPassword);
			}
			entityManager.merge(u);
		}

		UserRole userRole = new UserRole();
		userRole.setUser(u);
		userRole.setRole(r);
		
		entityManager.merge(userRole);
	}


	@Override
	@Transactional
	public String getUserPasswordById(int id) {
		Query query = entityManager.createQuery("select user.password from User user where user.userId = :USER_ID");
		query.setParameter("USER_ID", id);
		String oldPassword = (String) query.getSingleResult();
		return oldPassword;
	}


}
