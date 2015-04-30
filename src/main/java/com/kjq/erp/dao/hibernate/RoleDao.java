package com.kjq.erp.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Role;


@Repository("roleDao")
public class RoleDao extends GenericDaoHibernate<Role, String>{


	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
		return detachedCriteria;
	}

	
}
