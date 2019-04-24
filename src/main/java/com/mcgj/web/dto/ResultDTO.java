package com.mcgj.web.dto;
/**
 * 用于返回数据的实体类
 * @author 杨晨
 *
 */
public class ResultDTO {
	private int state = 0;//0表示用户没有登录，1表示已经登录,没有登录会被拦截器拦截,2表示用户会话过期
	private String message = "";
	private Boolean success = false;
	private Object result;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}


	public ResultDTO() {

	}

	protected ResultDTO(String message) {
		this.message = message;
	}

	public ResultDTO(int state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return this.message;
	}

	public ResultDTO setMessage(String message) {
		this.message = message;
		return this;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public ResultDTO(String message, Boolean success, Object result) {
		this.message = message;
		this.success = success;
		this.result = result;
	}
	
	
}
