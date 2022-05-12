package springboot.petfood.service;

import springboot.petfood.entity.User;

public interface UserDao {
	public User findUserAccount(String username);
	public void saveUser(User u);
}
