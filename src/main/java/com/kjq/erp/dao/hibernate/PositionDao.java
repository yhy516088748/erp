package com.kjq.erp.dao.hibernate;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Group;
import com.kjq.erp.model.Position;


@Repository("positionDao")
public class PositionDao extends GenericDaoHibernate<Position, String>{

	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Position.class);
		return detachedCriteria;
	}


}
