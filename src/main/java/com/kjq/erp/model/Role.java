package com.kjq.erp.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.kjq.erp.util.UUIDEntity;

/**
 * 角色
 * @author York
 */
@Entity
@Table(name="tb_role")
public class Role extends UUIDEntity implements Serializable{
	
	private static final long serialVersionUID = -3186342932523172811L;
	
	private String code;									// 权限代码
	private String name;								// 权限名称

//	private Set<Group> groups;					// 所属组
	private Set<User> users;

	@Column
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany
	@JoinTable(
            name = "tb_user_role",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

//	@ManyToMany
//	@JoinTable(
//            name = "tb_group_role",
//            joinColumns = { @JoinColumn(name = "role_id") },
//            inverseJoinColumns = @JoinColumn(name = "group_id")
//    )
//	public Set<Group> getGroups() {
//		return groups;
//	}
//	public void setGroups(Set<Group> groups) {
//		this.groups = groups;
//	}
}