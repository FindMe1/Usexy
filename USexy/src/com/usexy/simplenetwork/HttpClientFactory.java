package com.usexy.simplenetwork;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * HttpClient产生工厂
 * 
 * @author GYsuning
 * 
 */
public class HttpClientFactory {
	/** httpClient */
	private HttpClient httpClient = null;
	/** httpClient单例 */
	private static HttpClient httpClientSingleton = null;

	/**
	 * 构造器隐藏
	 */
	public HttpClientFactory() {

	}

	/**
	 * 获取httpClient
	 * 
	 * @param timeOut
	 *            最大请求时长(单位：毫秒)，默认时长30秒,timeout<0时使用默认时长
	 * @return HttpClient 请求客户端
	 */
	public HttpClient getHttpClient(int timeout) {
		// if (httpClient == null) {
		// 没有实例，产生一个
		httpClient = createDefaultHttpClient(timeout);
		// }

		return httpClient;

	}

	/**
	 * 获取单例httpClient
	 * 
	 * @return HttpClient 请求客户端
	 */
	public static HttpClient getSingletonHttpClient() {
		if (httpClientSingleton == null) {
			// 没有实例，产生一个
			httpClientSingleton = createDefaultHttpClient(-1);
		}

		return httpClientSingleton;

	}

	/**
	 * 关闭httpClient,释放资源
	 */
	public void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 创建httpClient
	 * 
	 * @param timeout
	 *            最大请求时长
	 * @return HttpClient DefaultHttpClient
	 */
	private static HttpClient createDefaultHttpClient(int timeout) {

		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(params, true);

		if (timeout >= 0) {
			HttpConnectionParams.setConnectionTimeout(params, timeout);
			HttpConnectionParams.setSoTimeout(params, timeout);
		} else {
			// 最大http连接时长30秒
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			// 最高Socket连接时长30秒
			HttpConnectionParams.setSoTimeout(params, 30000);
		}

		// 构建网络协议
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		// 多线程
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(conMgr, params);
	}

}
