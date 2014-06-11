package com.usexy.simplenetwork;

/**
 * http请求监听
 * @author GYsuning
 *
 */
public interface HttpRequestCompletedListener {
	/**
	 * 请求正常时调用，isStream ture:返回的是一个流，HttpResponseResultModel.getInputStream()获取,
	 * HttpResponseResultModel.setResult将返回null。false：HttpResponseResultModel.getInputStream()返回null
	 * @param httpResponseResultModel
	 * @param isStream ture:返回的是一个流，需要关闭流
	 */
	public void httprequestCompleted(HttpResponseResultModel httpResponseResultModel,boolean isStream);
	/**
	 * 请求发生异常调用
	 * @param httpResponseResultModel
	 */
	public void httprequestException(HttpResponseResultModel httpResponseResultModel);
}
