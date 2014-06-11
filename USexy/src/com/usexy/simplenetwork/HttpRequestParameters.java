package com.usexy.simplenetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.net.Uri;

/**
 * http请求参数包装model
 * @author GYsuning
 *
 */
@Root(name="Request")
public class HttpRequestParameters implements Serializable{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	/**调用方法名称，对xml流的补充*/
	@Element(name="Method",required=false)
	private String methodName;
	/**参数集合*/
	@ElementList(name="Parameters",required=false)
    private List<ParametersNameValuePair> parameters;
	/**请求参数的String流*/
	private StringBuilder requsetString;
	
	/**构造参数model*/
	public HttpRequestParameters() {
		parameters = new ArrayList<ParametersNameValuePair>();
		requsetString = new StringBuilder();
	}
	
	/**
	 * 添加参数name,value值对
	 * @param name 参数名称
	 * @param value 参数值
	 */
	public void addParameters(String name,String value){
		ParametersNameValuePair nameValuePair = new ParametersNameValuePair(name,value);
		parameters.add(nameValuePair);
		requsetString.append("&"+ Uri.encode(name) + "=" + Uri.encode(value));
	}
	
	/**
	 * 获取请求参数String，以name=value&name=value... 格式给出
	 * 
	 */
	public String getRequsetString() {
		if(requsetString==null || requsetString.length()==0)
			return "";
		
		return requsetString.toString().substring(1);
	}
	
	/**
	 * 获取请求方法
	 * @return methodName methodName
	 */
	public String getMethodName() {
		return methodName;
	}
    
	/**
	 * 获取请求参数List<ParametersNameValuePair>集合
	 * @return parameters 参数List<ParametersNameValuePair>集合
	 */
	public List<ParametersNameValuePair> getParameters() {
		return parameters;
	}

	
}
