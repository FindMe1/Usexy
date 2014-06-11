package com.usexy.simplenetwork;

import java.io.Serializable;

import org.apache.http.NameValuePair;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 请求参数键值对
 * @author GYsuning
 *
 */
@Root(name="parameter") 
 class ParametersNameValuePair implements NameValuePair,Serializable{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	/**参数名称*/
	@Element(name="name",required=false)
	private String name;
	/**参数值*/
	@Element(name="value",required=false)
	private String value;
	
	/**
	 * 构造namevaluepar
	 * @param name
	 * @param value
	 */
	public ParametersNameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	/**
	 * 获取参数名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置参数名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取参数值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置参数值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
}
