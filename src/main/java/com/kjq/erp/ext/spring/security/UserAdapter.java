package com.kjq.erp.ext.spring.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.components.Password;
import org.springframework.security.core.userdetails.UserDetails;

import com.kjq.erp.model.Group;
import com.kjq.erp.model.Role;
import com.kjq.erp.model.User;
import com.kjq.erp.util.DES;
import com.kjq.erp.util.PropertyUtils;

/**
 * user adapter for spring security
 * @author York
 *
 */
public class UserAdapter implements UserDetails, Serializable {
	private final User user;
	private Set<RoleAdapter> cachedAuthorities;
	private Group groupAs;

	public UserAdapter(User user) {
		this.user = user;
		buildAuthorities();
	}
	
	public UserAdapter(User user,Collection<Role> roles){
		this.user = user;
		buildAuthorities(roles);
	}

	private void buildAuthorities() {
		Set<RoleAdapter> authorities = new HashSet<RoleAdapter>();
		for (Role role:this.user.getRoles()){
			System.out.println(role.getCode());
			authorities.add(new RoleAdapter(role));
		}
//		for (Group group : this.user.getGroups()) {
//			for (Role role : group.getRoles()) {
//				authorities.add(new RoleAdapter(role));
//			}
//		}
		this.cachedAuthorities = authorities;
	}
	
	private void buildAuthorities(Collection<Role> roles){
		Set<RoleAdapter> authorities = new HashSet<RoleAdapter>();
		for(Role role:roles){
			authorities.add(new RoleAdapter(role));
		}
		this.cachedAuthorities = authorities;
	}

	
	public Collection getAuthorities() {
		return this.cachedAuthorities;
	}
	
	

	public boolean isEnabled() {
		return this.user.isEnabled();
	}

	public boolean isAccountNonLocked() {
		return !this.user.isLocked();
	}

	public boolean isAccountNonExpired() {
		return this.user.isAccountNonExpired();
	}

	public boolean isCredentialsNonExpired() {
		return this.user.isCredentialsNonExpired();
	}

	public String getPassword() {
		String password = "";
		try {
			password = DES.decrypt(PropertyUtils.getStringValByKey("passwordSeed"), this.user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public String getUsername() {
		return this.user.getLoginName();
	}

	public User getUser() {
		return this.user;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserAdapter)) {
			return false;
		}
		UserAdapter userAdapter = (UserAdapter) o;
		return this.user.equals(userAdapter.user);
	}

	public int hashCode() {
		return this.user.hashCode();
	}

	public Group getGroupAs() {
		return groupAs;
	}

	public void setGroupAs(Group groupAs) {
		this.groupAs = groupAs;
	}

	
}