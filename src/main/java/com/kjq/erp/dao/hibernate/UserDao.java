package com.kjq.erp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Group;
import com.kjq.erp.model.User;


@Repository("userDao")
public class UserDao extends GenericDaoHibernate<User, String>{


	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		if(isNotEmpty(filterMap.get("name"))){
			detachedCriteria.add(Restrictions.ilike("name", (String)filterMap.get("name"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("loginName"))){
			detachedCriteria.add(Restrictions.ilike("loginName", (String)filterMap.get("loginName"),MatchMode.ANYWHERE));
		}
		return detachedCriteria;
	}

//	public List findUserGroups(String uid){
//		String hql = "select user.groups from User user where user.id=?";
//		List list = getHibernateTemplate().find(hql,new Object[]{uid});
//		return list;
//	}
//	
	public User findUserByLoginName(String name){
		String hql = "from User user where upper(user.loginName)=?";
		List list = getHibernateTemplate().find(hql,new Object[]{name});
		if(list!=null && list.size()==1){
			return (User)list.get(0);
		}else{
			return null;
		}
	}
	
	public List findRolesByUserID(String uid){
		String hql = "select roles from User user inner join user.roles as roles where user.id=?";
		List list = getHibernateTemplate().find(hql,new Object[]{uid});
		return list;
	}
	
//	public List findRolesByUserAndGroup(String uid,String gid){
//		String hql = "select gp.roles from User user inner join user.groups as gp where user.id=? and gp.id=?";
//		List list = getHibernateTemplate().find(hql,new Object[]{uid,gid});
//		return list;
//	}
	
	public Group isGroupAssignedToUser(String uid,String gid){
		String hql = "select gps from User user right join user.groups gps where user.id=? and gps.id = ?";
		Group group = (Group)getHibernateTemplate().find(hql,new Object[]{uid,gid}).listIterator().next();
		return group;
	}
	
	public boolean isUserInRole(String uid,String roleCode){
		String hql = "select role from Group gps join gps.roles role join gps.users user where user.id = ? and role.code = ?";
		List list = getHibernateTemplate().find(hql,new Object[]{uid,roleCode});
		return list!=null && list.size()>0;
	}
	
	public List<User> findAllUser(String uid){
		String hql = "from User user where user.id=? or user.manager.id=?";
		List list = getHibernateTemplate().find(hql,new Object[]{uid,uid});
		return list;
	}
	
}
