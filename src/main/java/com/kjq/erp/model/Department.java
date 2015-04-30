package com.kjq.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.kjq.erp.util.UUIDEntity;


@Entity
@Table(name="tb_department")
public class Department extends UUIDEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2971637147255079545L;
	private String name;
	private String code;
	
	private Department upperDepart;
	
	@Column(name="name",length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="code",length=100)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	public Department getUpperDepart() {
		return upperDepart;
	}
	public void setUpperDepart(Department upperDepart) {
		this.upperDepart = upperDepart;
	}
	
}
