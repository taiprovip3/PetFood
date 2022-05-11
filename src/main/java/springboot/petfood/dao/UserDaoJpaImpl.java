package springboot.petfood.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springboot.petfood.entity.User;

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
        	Query query = entityManager.createNativeQuery("SELECT * FROM users WHERE user_name = :USERNAME", User.class);
        	query.setParameter("USERNAME", username);
        	User u = (User) query.getSingleResult();
        	System.out.println(u);
            return u;
        } catch (NoResultException e) {
            return null;
        }
    }
}
