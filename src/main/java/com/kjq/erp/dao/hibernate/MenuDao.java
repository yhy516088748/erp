package com.kjq.erp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Group;
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
	

}
