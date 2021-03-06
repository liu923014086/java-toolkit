package com.thankjava.toolkit3d.http.async;

import java.io.IOException;

import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import com.thankjava.toolkit3d.http.async.core.SyncRequest;
import com.thankjava.toolkit3d.http.async.entity.Cookies;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;

public class AsyncHttpClient {
	
	/**
	 * 同步请求的处理
	 */
	private static SyncRequest syncRequest;
	
	private static CloseableHttpAsyncClient closeableHttpAsyncClient;
	
	AsyncHttpClient(CloseableHttpAsyncClient closeableHttpAsyncClient, boolean enableSNIExtension){
		if (enableSNIExtension) {
			System.setProperty("jsse.enableSNIExtension", "true");
		}
		AsyncHttpClient.closeableHttpAsyncClient = closeableHttpAsyncClient;
		closeableHttpAsyncClient.start();
		syncRequest = SyncRequest.getInterface(closeableHttpAsyncClient);
	}
	
	/**
	 * 发生同步请求,并自动携带相关的请求头信息
	* <p>Function: syncRequestWithSession</p>
	* <p>Description: </p>
	* @author acexy@thankjava.com
	* @date 2016年12月12日 下午3:54:33
	* @version 1.0
	* @param asyncRequest
	* @return
	 */
	public AsyncResponse syncRequestWithSession(AsyncRequest asyncRequest){
		return syncRequest.requestWithSession(asyncRequest);
	}
	
	/**
	 * 获取历史请求CookieStore
	* <p>Function: getRequestCookieStore</p>
	* <p>Description: </p>
	* @author acexy@thankjava.com
	* @date 2017年6月12日 下午3:31:00
	* @version 1.0
	* @return
	 */
	public Cookies getAllCookies(){
		return new Cookies(SyncRequest.getSyncCookieStore().getCookies());
	}
	
	/**
	 * 获取指定的cookie
	* <p>Function: getCookie</p>
	* <p>Description: </p>
	* @author acexy@thankjava.com
	* @date 2017年6月12日 下午3:41:55
	* @version 1.0
	* @param cookieName
	* @return
	 */
	public Cookie getCookie(String cookieName){
		return getAllCookies().getCookie(cookieName);
	}
	
	/**
	 * 停止整个client
	* <p>Function: shutdown</p>
	* <p>Description: </p>
	* @author acexy@thankjava.com
	* @date 2016年12月14日 下午2:56:25
	* @version 1.0
	 */
	public void shutdown(){
		try {
			closeableHttpAsyncClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
