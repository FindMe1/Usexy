package com.usexy.simplenetwork;

import java.io.InputStream;
/**
 * http请求标识接口，http请求具体实现类必须实现它，对HttpRequestHelper作出支持
 * @author GYsuning
 *
 */
interface AbstractHttpRequest {
	/**
	 * 长时间连接，获取流
	 * @param timout 连接时长
	 * @return InputStream 流
	 */
	public InputStream getResponseLongFile(int timout);
	/**
	 * 获取服务器端响应
	 * @return HttpResponseResultModel 结果集model
	 */
	public HttpResponseResultModel getResponse(boolean isStream);	
	
}
