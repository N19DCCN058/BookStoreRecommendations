package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Role")
public class Role {
	@Id
	@Column(name="RoleId")
	private Integer roleId;
	
	@Column(name="RoleName")
	private String roleName;
	
	@OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
	private Collection<Account> accounts;

	public Role() {
	}

	public Role(Integer roleId, String roleName, Collection<Account> accounts) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.accounts = accounts;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Collection<Account> accounts) {
		this.accounts = accounts;
	}

	
}
