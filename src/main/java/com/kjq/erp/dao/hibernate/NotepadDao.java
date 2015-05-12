package com.kjq.erp.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Notepad;

@Repository("notepadDao")
public class NotepadDao extends GenericDaoHibernate<Notepad, String> {
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Notepad.class);
		return detachedCriteria;
	}
	
	
}
