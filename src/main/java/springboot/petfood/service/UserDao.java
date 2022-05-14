package springboot.petfood.service;


import springboot.petfood.entity.Category;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;

import java.util.List;

public interface UserDao {

	public List<User> getAllUser();
	public List<User> findAllUser(String username);
	public User getUserById(int id);
	public User findUserAccount(String username);
	public void saveUser(User u);

}
