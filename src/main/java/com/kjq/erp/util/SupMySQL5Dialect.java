package com.kjq.erp.util;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class SupMySQL5Dialect extends MySQL5InnoDBDialect{
	public SupMySQL5Dialect(){
		super();
		this.registerFunction("group_concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(?1)"));
	}
}
