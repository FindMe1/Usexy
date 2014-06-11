package com.usexy.simplenetwork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


/**
 * http请求post方式
 * 
 * @author GYsuning
 * 
 */
public class HttpPostRequest implements AbstractHttpRequest {
	
	/**请求参数body */
	private String requestBody = "";
	/**请求url */
	private String requestUrl;
	/**请求参数集合*/
	private List<ParametersNameValuePair> parameters;
	/**请求参数是否以xml方式发送*/
	private boolean isRequestXml = false;
	/**对于httpclient的单例要求*/
	private boolean isSingleton = false;

	/**
	 * 构建post请求，post提供了一般参数传递和以xml格式传送参数两种方式，由isRequestXml控制
	 * 
	 * @param requestModel
	 *            请求参数model
	 * @param requestUrl
	 *            请求url
	 * @param isRequestXml 为true请求流以xml格式传送，默认不以xml流传送
	 */
	public HttpPostRequest(HttpRequestParameters httpRequestlParameters,
			String requestUrl, boolean isRequestXml) {

		this.requestUrl = requestUrl;
		this.isRequestXml = isRequestXml;
		if(httpRequestlParameters != null){
			if (isRequestXml) {
				requestBody = obtainRequestXml(httpRequestlParameters);
			}else{
				parameters = obtainRequestParametersList(httpRequestlParameters);
			}
		}	
	}

	/**
	 * 获得xml参数请求流
	 * @param httpRequestlParameters 请求参数model，namevalue对的封装class
	 * @return xmlStr xml参数形式
	 */
	private String obtainRequestXml(HttpRequestParameters httpRequestlParameters) {

		OutputStream out = new ByteArrayOutputStream();
		Serializer serializer = new Persister();
		try {
			serializer.write(httpRequestlParameters, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String xmlStr = out.toString();
		return xmlStr;
	}
	
	/**
	 *获取长文件流，没有提供实现
	 *@param timout 请求时长
	 */
	@Override
	public InputStream getResponseLongFile(int timout) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取响应,在HttpResponseResultModel中获取成功，异常，结果集信息
	 * @return HttpResponseResultModel 响应model
	 */
	@Override
	public HttpResponseResultModel getResponse(boolean isStream) {
		HttpResponseResultModel httpResponseResultModel = new HttpResponseResultModel();
		// 构建httpPost
		HttpPost postRequest = new HttpPost(requestUrl);

		try {
			//构建Body
			if(isRequestXml){
				StringEntity se = new StringEntity(requestBody,HTTP.UTF_8);
				se.setContentType("text/xml");
				postRequest.setEntity(se);
			}else{
				postRequest.setEntity(new UrlEncodedFormEntity(parameters,HTTP.UTF_8));
			}
			HttpClient httpClient = null;
			
			if(isSingleton){
				httpClient = HttpClientFactory.getSingletonHttpClient();
			}else{
				HttpClientFactory f = new HttpClientFactory();
				httpClient = f.getHttpClient(-1);
			}

			HttpResponse response = httpClient.execute(postRequest);
			int statusCode =0;
			statusCode = response.getStatusLine().getStatusCode(); 
			 if (statusCode != HttpStatus.SC_OK) {  
	        	 
				   httpResponseResultModel.setIsSucess(false);
		        	if(statusCode>=400 && statusCode<500)
		        	{
		        		httpResponseResultModel.setException("网络请求失败,没有对应服务或达不到服务器");
		        	}
		        	else if(statusCode>=500)
		        	{
		        		httpResponseResultModel.setException("服务器端程序错误");
		        	}
		        	else
		        	{
		        		httpResponseResultModel.setException("程序异常");
		        	}
		        	httpResponseResultModel.setResult(null);
		        } else{
		        	httpResponseResultModel.setIsSucess(true);
		        	    HttpEntity entity = response.getEntity(); 
		        	    if(isStream){
		        	    	httpResponseResultModel.setInputStream(entity.getContent());
		        	    }else{
		        	    	byte[] bytes=EntityUtils.toByteArray(entity);
							String result = new String(bytes,"utf-8");
							httpResponseResultModel.setResult(result);
							
		        	    }	
		        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return httpResponseResultModel;
	}

	/**
	 * 获取请求参数List集合
	 * @param httpRequestlParameters 参数model
	 * @return httpRequestlParameters 参数List集合
	 */
	private List<ParametersNameValuePair> obtainRequestParametersList(
			HttpRequestParameters httpRequestlParameters) {
	
		return httpRequestlParameters.getParameters();
	}
	
	/**
	 * 获取请求body,在xml格式传递参数的时候，才可以获取到
	 * @return requestBody requestBody
	 */
	public String getRequestBody() {
		return requestBody;
	}

	/**
	 * 获取请求url
	 * @return requestUrl requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	
	/**
	 * 获取请求参数List<ParametersNameValuePair>集合
	 * @return parameters 参数List<ParametersNameValuePair>集合
	 */
	public List<ParametersNameValuePair> getParameters() {
		return parameters;
	}
	/**
	 * 是否单例
	 * @return
	 */
	public boolean isSingleton() {
		return isSingleton;
	}
	/**
	 * 设置单例应用一个Httpclient
	 * @param isSingleton
	 */
	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

}
