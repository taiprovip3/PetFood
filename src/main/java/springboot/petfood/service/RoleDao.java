package springboot.petfood.service;


import springboot.petfood.entity.Role;


import java.util.List;

public interface RoleDao {
    public List<Role> getAllRole();
    public List<String> getUserRoles(int userId);
    public Role getRoleById(int id);
    public void saveRole(Role role);
    public void deleteRoleById(int roleId);
}
