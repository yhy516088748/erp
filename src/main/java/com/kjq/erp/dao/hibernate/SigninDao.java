package com.kjq.erp.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.kjq.erp.model.Signin;

@Repository("signinDao")
public class SigninDao extends GenericDaoHibernate<Signin, String> {
	@Override
	public DetachedCriteria getCriteria(Map filterMap) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(Signin.class);
		return detachedCriteria;
	}

	public List<Signin> getSigninByDayByUser(Date date,String userID) {
		Date headerDate = null;
		Date footerDate = null;
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		headerDate = time.getTime();
		
		time.setTime(date);
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		footerDate = time.getTime();
		
		Session session = getSession();
		String sql = "select signin.* from tb_signin signin where signinTime >= ? and signinTime <= ? and user_id = ? ";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, headerDate);
		query.setParameter(1, footerDate);
		query.setParameter(2, userID);
		List list = query.list();
		releaseSession(session);
		return list;
	}
	
	public List<Signin> getSigninByMonthByUser(Date date,String userID) {
		Date headerDate = null;
		Date footerDate = null;
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.DAY_OF_MONTH, 1);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		//日期所在月第一天
		headerDate = time.getTime();
		
		time.setTime(date);
		time.add(Calendar.MONTH, 1);    //加一个月  获取下个月
		time.set(Calendar.DATE, 1);        //设置为下个月第一天
		time.add(Calendar.DATE, -1);        //下个月减一天 获取该月最后一天
		time.set(Calendar.HOUR_OF_DAY, 23);
		time.set(Calendar.MINUTE, 59);
		time.set(Calendar.SECOND, 59);
		//日期所在月 最后一天
		footerDate = time.getTime();
		
		Session session = getSession();
		String sql = "select signin.* from tb_signin signin where signinTime >= ? and signinTime <= ? and user_id = ? ";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, headerDate);
		query.setParameter(1, footerDate);
		query.setParameter(2, userID);
		List list = query.list();
		releaseSession(session);
		return list;
	}

}
