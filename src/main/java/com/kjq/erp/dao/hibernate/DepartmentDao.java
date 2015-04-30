package com.kjq.erp.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Department;


@Repository("departmentDao")
public class DepartmentDao extends GenericDaoHibernate<Department, String> {

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		// TODO Auto-generated method stub
		return null;
	}

}