package com.mcgj.entity;

/**
 * 操作类型实体
 * @author 杨晨
 * @date 2019-03-28
 * @address 2019-03-28
 */
public class Operation extends BaseEntity{
	
	private Integer userId;//用户id
	
	private String ip;//请求的ip地址
	
	private String params;//用户请求时参数
	
	private String method;//用户请求的方法
	
	private String operation;//用户的操作

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}
