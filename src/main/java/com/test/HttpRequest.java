package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;  
import org.apache.commons.httpclient.Header;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;  
import org.apache.commons.httpclient.HttpMethod;  
import org.apache.commons.httpclient.HttpStatus;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams;  
import org.apache.http.client.methods.HttpPost;  

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

	  /** 
     * 执行一个HTTP POST请求，返回请求响应的HTML 
     *  
     * @param url  请求的URL地址 
     * @param params  请求的查询参数,可以为null 
     * @param charset 字符集 
     * @param pretty 是否美化 
     * @return 返回请求响应的HTML 
     */  
    public static String sendPost(String url, Map<String, Object> _params) { 
    	String charset =  "UTF-8";
    	 boolean pretty =  true;
        StringBuffer response = new StringBuffer();  
        HttpClient client = new HttpClient();  
        PostMethod method = new PostMethod(url);  
          
        // 设置Http Post数据  
        if (_params != null) {  
            for (Map.Entry<String, Object> entry : _params.entrySet()) {  
                method.setParameter(entry.getKey(), String.valueOf(entry.getValue()));  
            }  
        }  
          
        // 设置Http Post数据  方法二  
//        if(_params != null) {  
//            NameValuePair[] pairs = new NameValuePair[_params.size()];//纯参数了，键值对  
//            int i = 0;  
//            for (Map.Entry<String, Object> entry : _params.entrySet()) {  
//                pairs[i] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));  
//                i++;  
//            }  
//            method.addParameters(pairs);  
//        }  
          
        try {  
            client.executeMethod(method);  
            if (method.getStatusCode() == HttpStatus.SC_OK) {  
                // 读取为 InputStream，在网页内容数据量大时候推荐使用  
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),  
                        charset));  
                String line;  
                while ((line = reader.readLine()) != null) {  
                    if (pretty)  
                        response.append(line).append(System.getProperty("line.separator"));  
                    else  
                        response.append(line);  
                }  
                reader.close();  
            }  
        } catch (IOException e) {  
            System.out.println("执行HTTP Post请求" + url + "时，发生异常！");  
            e.printStackTrace();  
        } finally {  
            method.releaseConnection();  
        }  
        System.out.println("--------------------" + response.toString());  
        return response.toString();  
    }  
}
