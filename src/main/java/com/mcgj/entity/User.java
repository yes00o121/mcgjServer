package com.mcgj.entity;

public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	//表对应字段

	private String userName;//用户名称
	
	private String account;//账号
	
	private Boolean admin = false;//超级用户
	
	private String password;//密码
	
	private String photo;//用户头像，照片
	
	private String background;//个人中心背景图片
	
	private String card;//卡片横幅
	
	//扩展字段
	
	private String browser;//使用浏览器
	
	private String os;//用户系统
	
	private String ip;//用户ip
	
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
