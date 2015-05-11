package com.kjq.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


import com.kjq.erp.util.UUIDEntity;


@Entity
@Table(name="tb_position")
public class Position extends UUIDEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3959628657005463434L;
	private String name;
	private String code;
	
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
	
	
}
