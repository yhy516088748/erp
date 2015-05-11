package com.kjq.erp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Menu;

@Repository("menuDao")
public class MenuDao extends GenericDaoHibernate<Menu, String>{


	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Menu.class);
		if(isNotEmpty(filterMap.get("title"))){
			detachedCriteria.add(Restrictions.ilike("title", (String)filterMap.get("title"),MatchMode.ANYWHERE));
		}
		if(isNotEmpty(filterMap.get("parent.id"))){
			detachedCriteria.add(Restrictions.eq("parent.id", filterMap.get("parent.id")));
		}
		return detachedCriteria;
	}
	
	
	/**
	 * 用户可访问的一级菜单
	 * @param id
	 * @return
	 */
	public List<Menu> findParentMenus(){
		String hql = "select menu from Menu as menu where menu.parent is null";
		return getHibernateTemplate().find(hql,new Object[]{});
	}
	public List<Menu> findChildMenus(Menu menu){
		String hql = "select menu from Menu as menu where menu.parent.id = ?";
		Object[]params = new Object[]{menu.getId()};
		return getHibernateTemplate().find(hql,params);
	}

}
