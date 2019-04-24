package com.mcgj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类的父类，提供一些公有属性
 * @author 杨晨
 *
 */
public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	protected Integer start;//开始页
	
	protected Integer limit;//结束页

	protected Date createDate =new Date();//创建时间
	
	protected Date modifyDate;//修改时间
	
	protected String token;//用户登录唯一标示
	
	protected String verificationCode;//验证码code,浏览器端用户未登录验证唯一标识
	
	protected String checkCode;//校验码,用户输入的验证码
	
	protected Integer state=1;//状态,默认为可用
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
