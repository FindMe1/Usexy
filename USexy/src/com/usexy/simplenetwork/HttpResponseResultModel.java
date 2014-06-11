package com.usexy.simplenetwork;

import java.io.InputStream;
import java.io.Serializable;

/**
 * http请求返回数据response的封装，包含异常，成功，结果集信息
 * 
 * @author GYsuning
 * 
 */
public class HttpResponseResultModel implements Serializable{
	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1L;
	/** 结果集 */
	private String result;
	/** 异常信息 */
	private String exception;
	/** 是否成功 */
	private boolean IsSucess;
	/**流*/
	private InputStream inputStream;
	

	/**
	 * 请求是否成功
	 * @return IsSucess
	 */
	public boolean isIsSucess() {
		return IsSucess;
	}
	
	/**
	 * 设置是否成功
	 * @param isSucess ture 成功
	 */
	public void setIsSucess(boolean isSucess) {
		IsSucess = isSucess;
	}

	/**
	 * 获取结果集
	 * 
	 * @return result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置结果集
	 * 
	 * @param xml
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 获取错误信息
	 * 
	 * @return exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * 设置错误信息
	 * 
	 * @param ex
	 */
	public void setException(String ex) {
		exception = ex;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
