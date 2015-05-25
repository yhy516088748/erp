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
		return detachedCriteria;
	}

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
	
	
}
