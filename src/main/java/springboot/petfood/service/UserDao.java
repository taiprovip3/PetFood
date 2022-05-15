package springboot.petfood.service;


import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;

import java.util.List;

public interface UserDao {

	public List<User> getAllUser();
	public List<User> findAllUser(String username);
	public User getUserById(int id);
	public void removeUserById(int userId);
	public User findUserAccount(String username);
	public void saveUser(User u, Role r);
	public String getUserPasswordById(int id);

}
