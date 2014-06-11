package com.usexy.simplenetwork;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;



/**
 * http请求get方式
 * @author GYsuning
 *
 */
public class HttpGetRequest implements AbstractHttpRequest{
	/**请求参数body*/
	private String requestBody = "";
	/**请求url*/
	private String requestUrl;
	/**对于httpclient的单例要求*/
	private boolean isSingleton = false;
	
	/**
	 * 构造器，构建get请求
	 * @param httpRequestlParameters 请求参数model，namevalue对的封装class
	 * @param requestUrl 请求url
	 * 
	 */
	public HttpGetRequest(HttpRequestParameters httpRequestlParameters, String requestUrl) {
		
		this.requestUrl = requestUrl;
		
		if(httpRequestlParameters != null){
			requestBody = obtainRequestString(httpRequestlParameters);
		}
		
	}
	/**
	 * 获取响应,在HttpResponseResultModel中获取成功，异常，结果集信息
	 * @return HttpResponseResultModel 响应model
	 */
	@Override
	public HttpResponseResultModel getResponse(boolean isStream) {
		HttpResponseResultModel httpResponseResultModel = new HttpResponseResultModel();
		//构建httpGet
    	HttpGet getRequest = new HttpGet(requestUrl+requestBody);
    
    	int statusCode =0;
    	
		try {
			HttpClient httpClient = null;
			if(isSingleton){
				httpClient = HttpClientFactory.getSingletonHttpClient();
			}else{
				HttpClientFactory f = new HttpClientFactory();
				httpClient = f.getHttpClient(-1);
			}
			
			HttpResponse response = httpClient.execute(getRequest);
			statusCode = response.getStatusLine().getStatusCode(); 
			 if (statusCode != HttpStatus.SC_OK) {  
	        	 
				    httpResponseResultModel.equals(false);
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
		        	    	byte[] bytes = EntityUtils.toByteArray(entity);
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
	 * 获取长文件流，没有提供实现
	 * @param timout 请求时长
	 */
	@Override
	public InputStream getResponseLongFile(int timout) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取请求参数String，以?name=value&name=value... 格式给出
	 * @param httpRequestlParameters http请求参数namevaluePar
	 * @return String 格式化后的参数String，"?name=value&name=value..."
	 */
	private String obtainRequestString(HttpRequestParameters httpRequestlParameters){
		String s = "?"+httpRequestlParameters.getRequsetString();
		return s;
	}
	
	/**
	 * 返回请求参数String，"?name=value&name=value..."
	 * @return
	 */
	public String getRequestBody() {
		return requestBody;
	}
	
	/**
	 * 返回get方式完整url，"http://demo/example?name=value&name=value..."
	 * @return
	 */
	public String getRequestUrl() {
		return requestUrl;
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
