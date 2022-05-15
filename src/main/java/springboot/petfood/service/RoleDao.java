package springboot.petfood.service;


import springboot.petfood.entity.Role;


import java.util.List;

public interface RoleDao {
    public List<Role> getAllRole();
    public Role getRoleById(int id);
}
