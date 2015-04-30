package com.kjq.erp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.kjq.erp.util.UUIDEntity;



/**
 * 菜单
 * @author York
 */
@Entity
@Table(name = "tb_signin")
public class Signin extends UUIDEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3068382700225788784L;



}
