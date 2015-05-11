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
public class Menu extends UUIDEntity implements Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7099112915257658653L;
	/**
	 * 排序
	 */
    private Integer seq;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单标题-英文
     */
    private String enTitle;
    /**
     * 菜单提示
     */
    private String tip;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单图标
     */
    private String code;
    /**
     * 菜单链接
     */
    private String url;
    /**
     * 如果是父级菜单，可设置默认子级菜单链接
     * 用于左右frame的框架结构中
     */
    private String defaultSubUrl;
    /**
     * 链接的target属性
     */
    private String target;
    /**
     * 上级菜单
     */
    private Menu parent;
    /**
     * 是否是叶子结点
     */
    private boolean leaf;
    /**
     * 是否顶级接点
     */
    private boolean top;
    /**
     * 子菜单
     */
    private Set<Menu> childMenu = new HashSet(0);
    
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
	
	@Column(name="url",length=100)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="target",length=10)
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
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
	
	@Transient
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	
	@Transient
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="parent")
	public Set<Menu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(Set<Menu> childMenu) {
		this.childMenu = childMenu;
	}
	
	@Column(name="default_sub_url",length=100)
	public String getDefaultSubUrl() {
		return defaultSubUrl;
	}
	public void setDefaultSubUrl(String defaultSubUrl) {
		this.defaultSubUrl = defaultSubUrl;
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