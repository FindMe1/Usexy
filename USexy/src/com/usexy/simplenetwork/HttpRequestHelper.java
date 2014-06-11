package com.usexy.simplenetwork;

import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.os.Handler;
import android.os.Message;

/**
 * http请求帮助类，此类本身实现了请求的异步，
 * 请求结束后，根据结果调用HttpRequestCompletedListener（网络请求监听器）中的成功或异常方法
 * @author GYsuning
 *
 */
public class HttpRequestHelper implements Runnable{
	/**http请求的基类*/
	private AbstractHttpRequest abstractHttpRequest;
	/**内部线程通信handler*/
	private Handler handler;
	/**网络请求监听器*/
	private HttpRequestCompletedListener httpRequestCompletedListener;
	/**请求是否是长时间的文件流，未提供实现*/
	private boolean IsLongFile = false;
	/**请求结果response的封装*/
	private HttpResponseResultModel httpResponseResultModel = null;
	/**文件流*/
	private InputStream in;
	/**需要已流方式返回*/
	private boolean needStream = false;
	/**使用线程池，来重复利用线程，优化内存*/
	private static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
	/**线程池*/
	private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
			.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
	
	/**
	 * 构造器构造请求帮助类，实现层的http请求类必须是实现了abstractHttpRequest接口
	 * @param abstractHttpRequest http请求的基类
	 */
	public HttpRequestHelper(AbstractHttpRequest abstractHttpRequest) {
		super();
		this.abstractHttpRequest = abstractHttpRequest;
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(httpResponseResultModel.isIsSucess()){
					httpRequestCompletedListener.httprequestCompleted(httpResponseResultModel,needStream);
				}else{
					httpRequestCompletedListener.httprequestException(httpResponseResultModel);
				}
				
			}
			
		};
	}
	/**
	 * 开始网络请求，开启异步请求 .needStream 是否按流的方式返回 true:以流返回
	 * @param needStream 是否按流的方式返回 true:以流返回
	 */
	public void stratHttpRequest(boolean needStream){
		this.needStream = needStream;
		executor.execute(this);
	}
	
	@Override
	public void run() {
		   
		if(IsLongFile){
			in = abstractHttpRequest.getResponseLongFile(300000);
		}else{
			httpResponseResultModel = abstractHttpRequest.getResponse(needStream);
		}
		handler.sendEmptyMessage(0);
		
	}
	
	/**
	 * 获取请求的基类，如要获取实现类，强转成实现类，需要确定强转的安全性
	 * @return abstractHttpRequest abstractHttpRequest
	 */
	public AbstractHttpRequest getAbstractHttpRequest() {
		return abstractHttpRequest;
	}

	/**
	 * 设置请求的基类
	 * @param abstractHttpRequest
	 */
	public void setAbstractHttpRequest(AbstractHttpRequest abstractHttpRequest) {
		this.abstractHttpRequest = abstractHttpRequest;
	}
	
	/**
	 * 网络请求监听器，请求结束后，根据结果调用其中的成功或异常方法
	 * @param httpRequestCompletedListener
	 */
	public void setHttpRequestCompletedListener(
			HttpRequestCompletedListener httpRequestCompletedListener) {
		this.httpRequestCompletedListener = httpRequestCompletedListener;
	}
	
	/**
	 * 是否是长文件
	 * @return IsLongFile IsLongFile
	 */
	public boolean isIsLongFile() {
		return IsLongFile;
	}
	
	/**
	 * 设置 是否是长文件
	 * @param isLongFile
	 */
	public void setIsLongFile(boolean isLongFile) {
		IsLongFile = isLongFile;
	}
	
	/**
	 * 获取文件流
	 * @return in
	 */
	public InputStream getIn() {
		return in;
	}
	
}
