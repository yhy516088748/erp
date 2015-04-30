package com.kjq.erp.ext.spring.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kjq.erp.dao.hibernate.UserDao;
import com.kjq.erp.model.Role;
import com.kjq.erp.model.User;
/**
 * 自定义user detail service
 * @author York
 *
 */
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;

	public UserDetails loadUserByUsername(String userName)
		throws UsernameNotFoundException, DataAccessException {
		if(userName==null || userName.trim().equals("")){
			throw new UsernameNotFoundException("User not found");
		}
		
		User user = null;
		try{
			user = userDao.findUserByLoginName(userName.toUpperCase());
		}catch(Exception e){
			System.out.println(e);
		}
		if(user == null){
			throw new UsernameNotFoundException("User not found");
		}
		
		
		UserAdapter userAdapter = null;
		List<Role> roles = userDao.findRolesByUserID(user.getId());
		userAdapter = new UserAdapter(user,roles);
		return userAdapter;
	}
	
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
