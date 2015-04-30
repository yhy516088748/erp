package com.kjq.erp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Group;
import com.kjq.erp.model.Menu;
import com.kjq.erp.model.Role;
import com.kjq.erp.model.User;


@Repository("groupDao")
public class GroupDao extends GenericDaoHibernate<Group, String>{

	@Override
	public DetachedCriteria getCriteria(Map filterMap){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Group.class);
		return detachedCriteria;
	}
	
	public List<Role> getRolebyGroupId(String groupId){
		String hql = "select roles from Group as group where  group.id = ?";
		return getHibernateTemplate().find(hql, new Object[]{groupId});
	}
	
	/**
	 * 获取组下分配的菜单
	 * @param groupID
	 * @return
	 */
	public List<Menu> findRoleMenus(String groupID){
		String hql = "select grp.menus from Group grp where grp.id=?";
		List<Menu> menus = getHibernateTemplate().find(hql, new Object[]{groupID});
		return menus;
	}
	
	/**
	 * 获取组下面的人员信息
	 * @param groupId
	 * @return
	 */
	public List<User> findUsersByGroupId(String groupId){
		String hql ="select gp.users from Group gp where gp.id = ?";
		return getHibernateTemplate().find(hql, new Object[]{groupId});
	}
}