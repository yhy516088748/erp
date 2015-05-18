package com.kjq.erp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

import com.kjq.erp.util.UUIDEntity;

/**
 * 菜单
 * @author York
 */
@Entity
@Table(name = "tb_menu")
public class Menu extends UUIDEntity implements Serializable{
	private static final long serialVersionUID = -7099112915257658653L;
	
    private Integer seq;	//排序
    private String title;//标题
    private String enTitle;//英文标题
    private String tip;//提示
    private String icon;//图标
    private String code;//code
    private String default_code;
    private String url;//连接
    private Menu parent;//上级菜单
    /**
     * 子菜单
     */
    private Set<Menu> childMenu = new HashSet(0);//子菜单
    
    @Column(name="seq")
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name="title",length=15)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="tip",length=15)
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	

	@Column(name="icon",length=100)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name="code",length=100)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="default_code",length=100)
	public String getDefault_code() {
		return default_code;
	}
	public void setDefault_code(String default_code) {
		this.default_code = default_code;
	}	
	
	@Column(name="url",length=100)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JSON(serialize=true)
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="parent")
	public Set<Menu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(Set<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	
	@Column(name="en_title",length=50)
	public String getEnTitle() {
		return enTitle;
	}
	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}
	
	
	
	public int compareTo(Object o) {
		if( ((Menu)o).getSeq()==null || this.getSeq()==null){
			return 1;
		}
		if( ((Menu)o).getSeq() == this.getSeq()){
			return 1;
		}
		return ((Menu)o).getSeq().compareTo(this.getSeq());
	}
}