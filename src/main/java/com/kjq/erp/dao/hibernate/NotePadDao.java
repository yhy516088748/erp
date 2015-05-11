package com.kjq.erp.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.NotePad;

@Repository("notePadDao")
public class NotePadDao extends GenericDaoHibernate<NotePad, String> {
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NotePad.class);
		return detachedCriteria;
	}
	
	
}
