package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
public class HttpRequest {
	//请求配置，设置链接超时和读取超时
		private static final RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
		//https策略，绕过安全检查
		private static CloseableHttpClient getSingleSSLConnection()
				throws Exception {
			CloseableHttpClient httpClient = null;
			try {
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
						return true;
					}
				}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				//return HttpClients.custom().setDefaultRequestConfig(config).build();
				//2017 12 14修改，忘了绕过安全检查设置了，哈哈
				return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
			} catch (Exception e) {
				throw e;
			}
	 
		}
		 public static String sendGet(String url) {
		        String result = "";
		        BufferedReader in = null;
		        try {
		            URL realUrl = new URL(url);
		            // 打开和URL之间的连接
		            URLConnection connection = realUrl.openConnection();		          		            
		         // 设置通用的请求属性
					connection.setRequestProperty("Host", "www.tuiwangpu.com");
					connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
					connection.setRequestProperty("connection", "Keep-Alive");
					connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64)");
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);
					String cookie="UM_distinctid=1652ca5f7af28d-02b6bcb3fdc791-36465d60-100200-1652ca5f7b0524; dntsid=7fa300270e8f5878; tqName=lan%u84DD%u6708%u4EAE; DNTSessionUID=66B37F906A11D694C6F3ED0567BC5825A97928F21CEFD60E8041496E0887999A3D0776024696E4317DA6CB12C7C981FD695E38C10B79E11F18B54615F7CB2DB61CBA9E5BF2F2A460B7BB1889E6AD06ADE142B9772A601F84AC5AE55B1A5CEF3E470E70644C40DB1458992CF6459D2AC35641F009DF208E9DC5C3E9F5D4235047026AF1E569CAE383AB2F27E35144B2A1; CNZZDATA1259398474=780023260-1534048401-%7C1534169270";
					connection.setRequestProperty("Cookie", cookie);
		            // 建立实际的连接
		            connection.connect();
		            // 获取所有响应头字段
//		            Map<String, List<String>> map = connection.getHeaderFields();
//		            // 遍历所有的响应头字段
//		            for (String key : map.keySet()) {
//		                System.out.println(key + "--->" + map.get(key));
//		            }
		            // 定义 BufferedReader输入流来读取URL的响应
		            in = new BufferedReader(new InputStreamReader(
		                    connection.getInputStream()));
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
     * 发送get请求
     * @param url（get请求的url）
     * @return
	 * @throws Exception 
     */
    public static String doGetStr(String accessTokenUrl) throws Exception {
		CloseableHttpClient httpClient = getSingleSSLConnection();
        HttpGet httpGet = new HttpGet(accessTokenUrl);
        JSONObject jsonObject = null;
        String result = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                 result = EntityUtils.toString(entity, "UTF-8");
            }
            httpGet.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
     /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     * @throws Exception 
     */
    public static String doPostStr(String url,String param) throws Exception{
		CloseableHttpClient httpClient = getSingleSSLConnection();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        String result = null;
        try {
            httpPost.setEntity(new StringEntity(param, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
             result = EntityUtils.toString(response.getEntity(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
