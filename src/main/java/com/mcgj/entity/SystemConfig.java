package com.mcgj.entity;

/**
 * 系统配置实体
 * @author 杨晨
 * @create_date 2018-12-05 21:27
 * @address 合肥
 *
 */
public class SystemConfig extends BaseEntity{
	
	private String key;//key
	
	private String value;//value
	
	private String remark;//备注

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
