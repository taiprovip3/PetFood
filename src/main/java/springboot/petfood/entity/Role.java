package springboot.petfood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;
	
	@Column(name="role_name")
	private String nameRole;
	
	@OneToMany(mappedBy = "role")
	private List<UserRole> userRoles;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Role() {
	}

	public Role(int roleId, String nameRole, List<UserRole> userRoles) {
		this.roleId = roleId;
		this.nameRole = nameRole;
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", nameRole=" + nameRole + ", userRoles=" + userRoles + "]";
	}
}