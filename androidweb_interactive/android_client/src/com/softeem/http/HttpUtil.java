package com.softeem.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.security.auth.callback.Callback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static final int HTTP_CONNECTION_TIMEOUT = 20000;
	public static final int HTTP_SO_TIMEOUT = 20000;
	public static HttpClient httpClient;
	
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String getRequest(final String url) throws ClientProtocolException, IOException, InterruptedException, ExecutionException{
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				httpClient = getHttpClient();
				//使用get请求
				HttpGet get = new HttpGet(url);
				//获得服务端响应
				HttpResponse response = httpClient.execute(get);
				//获得http状态码
				int code = response.getStatusLine().getStatusCode();
				if(code == HttpStatus.SC_OK){
					return EntityUtils.toString(response.getEntity());
				}
				return null;
			}
		});
		new Thread(task).run();
		return task.get();
	}
	
	/**
	 * post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String postRequest(final String url,final List<NameValuePair> params) throws InterruptedException, ExecutionException{
		FutureTask<String>  task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				httpClient = getHttpClient();
				HttpPost post = new HttpPost(url);
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse response = httpClient.execute(post);
				int code = response.getStatusLine().getStatusCode();
				if(code == HttpStatus.SC_OK){
					return EntityUtils.toString(response.getEntity());
				}
				return null;
			}
		});
		new Thread(task).run();
		return task.get();
	}
	
	public static HttpClient getHttpClient(){
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, HTTP_SO_TIMEOUT);
		HttpClientParams.setRedirecting(params, true);
		return new DefaultHttpClient(params);
	}
	
	public static NameValuePair getNameValuePair(String key,String value){
		return new BasicNameValuePair(key, value);
	}
}
