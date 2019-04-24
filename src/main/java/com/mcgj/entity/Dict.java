package com.mcgj.entity;
/**
 * 字典实体类
 * @author ad
 *
 */
public class Dict extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String dictName;//字典名称
	
	private String codeValue;//代码值
	
	private Integer dictType;//字典类型

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Integer getDictType() {
		return dictType;
	}

	public void setDictType(Integer dictType) {
		this.dictType = dictType;
	}
	
}
