package com.mcgj.entity;

/**
 * 文件仓库实体类
 * @author 杨晨
 * @date 219-03-01
 */
public class FileRepertory extends BaseEntity{
	
	private String source;//文件来源
	
	private String address;//文件地址
	
	private String mongodbId;//文件mongoId
	
	
	public FileRepertory(){}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMongodbId() {
		return mongodbId;
	}

	public void setMongodbId(String mongodbId) {
		this.mongodbId = mongodbId;
	}

	public FileRepertory(String source, String address, String mongodbId) {
		super();
		this.source = source;
		this.address = address;
		this.mongodbId = mongodbId;
	}
	
}
