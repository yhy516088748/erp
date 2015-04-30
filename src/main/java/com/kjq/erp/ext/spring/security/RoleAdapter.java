package com.kjq.erp.ext.spring.security;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;
import com.kjq.erp.model.Role;


/**
 * Role adapter for spring security
 * @author York
 *
 */
public class RoleAdapter implements GrantedAuthority, Serializable {
	private static final String rolePrefix = "";
	private final Role role;

	public RoleAdapter(Role role) {
		this.role = role;
	}

	public String getAuthority() {
		return rolePrefix + this.role.getCode().toUpperCase();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RoleAdapter)) {
			return false;
		}

		RoleAdapter roleAdapter = (RoleAdapter) o;

		return getAuthority().equals(roleAdapter.getAuthority());
	}

	public int hashCode() {
		return getAuthority().hashCode();
	}
}
