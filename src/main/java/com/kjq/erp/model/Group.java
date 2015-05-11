package com.kjq.erp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.kjq.erp.util.UUIDEntity;

@Entity
@Table(name="tb_group")
public class Group extends UUIDEntity implements Serializable
{
	private static final long serialVersionUID = 6903878446512900753L;
	
	private String name;						// 组名

	private Set<User> users;					// 所属用户
	private Set<Role> roles;					// 所属权限
	private Set<Menu> menus = new HashSet<Menu>();

	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany
	@JoinTable(
            name = "tb_user_group",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany
	@JoinTable(
            name = "tb_group_role",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@ManyToMany
	@JoinTable(
            name = "tb_group_menu",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
}