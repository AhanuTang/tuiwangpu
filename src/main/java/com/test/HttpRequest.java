package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

	public static String sendGet(String url, String cookie) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("Host", "www.tuiwangpu.com");
			connection.setRequestProperty("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64)");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestProperty("Cookie", cookie);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	
	
	   public static String doPost(String url, Map<String, String> param, String cookie) {  
	        // 创建Httpclient对象  
	        CloseableHttpClient httpClient = HttpClients.createDefault();  
	        CloseableHttpResponse response = null;  
	        String resultString = "";  
	        try {  
	            // 创建Http Post请求  
	            HttpPost httpPost = new HttpPost(url);  
	            // 创建参数列表  
	            if (param != null) {  
	                List<NameValuePair> paramList = new ArrayList<NameValuePair>();  
	                for (String key : param.keySet()) {  
	                    paramList.add(new BasicNameValuePair(key, param.get(key)));  
	                }  
	                // 模拟表单  
	                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);  
	                httpPost.setEntity(entity);  
	                httpPost.setHeader("accept", "application/json, text/javascript, */*; q=0.01");
	                httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
	                httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3510.2 Safari/537.36");
	                httpPost.setHeader("Referer", "http://www.tuiwangpu.com/ucenterTry/hall/tbtask/");
	                httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
	                httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
	                httpPost.setHeader("Cookie", cookie);
	            }  
	            
	            // 执行http请求  
	            response = httpClient.execute(httpPost);  
	            resultString = EntityUtils.toString(response.getEntity(), "utf-8");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                response.close();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	  
	        return resultString;  
	    }  
}
