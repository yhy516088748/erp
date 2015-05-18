package com.kjq.erp.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Department;


@Repository("departmentDao")
public class DepartmentDao extends GenericDaoHibernate<Department, String>{

	@Override
	public DetachedCriteria getCriteria(Map filterMap){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Department.class);
		return detachedCriteria;
	}

	public List<Department> getDepartmentParent(){
		String hql = "select department from Department department where department.parent.id = null ";
		List list = getHibernateTemplate().find(hql,new Object[]{});
		return list;
	}
	public List<Department> getDepartmentChild(String departmentid){
		String hql = "select department from Department department where department.parent.id = ? ";
		List list = getHibernateTemplate().find(hql,new Object[]{departmentid});
		return list;
	}
}