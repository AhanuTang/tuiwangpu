package com.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import sun.net.www.protocol.https.Handler;

@Controller
public class TestController {

	 
	  private List<String> cookies;
	  private HttpsURLConnection conn;
	 
	  private final String USER_AGENT = "Mozilla/5.0";

	@RequestMapping(value = "/api/user", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String getUser() {
		// 1.信客
//		JSONObject json = new JSONObject();
//		json.put("username", "17051128482");
//		json.put("pwd", "L170511284822");
//		json.put("cid", "passport");
//		String result = HttpUtils.post("http://aaa.698mn.com/passport/login.html?username=17051128482&pwd=L17051128482&cid=passport",json.toString());
//		//2.推旺铺
//		JSONObject json = new JSONObject();
//		json.put("username", "lan蓝月亮");
//		json.put("pwd", "28984af1cff2802ff830210530349ebd933d5df7d26291f8f6f1c4574ad64239d3f6a554d417a7131eaefffc0cfc11bc49582a7f54b9314e1079125e169149198a8b24f40797521897655dda143aa5607529ca9b54bcac54614ab4011d41ddfd8f0facb1ca0f7eef684cd79a11c92494d06d57c0cc71b781a9a3edc205bc6942");
//		json.put("cid", "passport");
//		String result = HttpUtils.post("http://www.tuiwangpu.com/Login/DoLogin",json.toString());
		
		String result= get("http://www.tuiwangpu.com/ucenterTry/hall/tbtask/");
		
		 FileWriter fwriter = null;
		 try {
		  fwriter = new FileWriter("E:\\\\test.html");
		  fwriter.write(result);
		 } catch (IOException ex) {
		  ex.printStackTrace();
		 } finally {
		  try {
		   fwriter.flush();
		   fwriter.close();
		  } catch (IOException ex) {
		   ex.printStackTrace();
		  }
		 }

//		 3.优客
//		JSONObject json = new JSONObject();
//		json.put("userName", "17051128482");
//		json.put("password", "L17051128482");
//		String result = HttpUtils.post("http://a.youkeezu.com/buyer/do_login.jsp?method=login&userName=17051128482&password=L17051128482", json.toString());
//		 // 4.二师兄
//		JSONObject json = new JSONObject();
//		json.put("username", "17051128482");
//		json.put("pwd", "L17051128482");
//		json.put("cid", "passport");
//		String result = HttpUtils.post("http://aaa.pk1172.com/passport/login.html?username=17051128482&pwd=L17051128482&cid=passport", json.toString());
//		
		System.out.println("result:" + result);

		return null;
	}
	
	@RequestMapping(value = "/api/sendhttp", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
	@ResponseBody
	public String sendhttp() throws Exception {
		 String url = "http://a.youkeezu.com/buyer/do_login.jsp?method=login";
//		    String gmail = "https://mail.google.com/mail/";
		 
		    TestController http = new TestController();
		 
		    // make sure cookies is turn on
		    CookieHandler.setDefault(new CookieManager());
		 
		    // 1. Send a "GET" request, so that you can extract the form's data.
		    String page = http.GetPageContent(url);
//		    String postParams = http.getFormParams(page, "17051128482", "L17051128482");
		 
		    // 2. Construct above post's content and then send a POST request for
		    // authentication
//		    http.sendPost(url, postParams);
		 
		    // 3. success then go to gmail.
//		    String result = http.GetPageContent(gmail);
//		    System.out.println(result);
			return "";

	}
	
	public static String get(String url) {
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
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Cookie", "dntsid=a10757ffce952686; UM_distinctid=165310782b219b-0b2aab5f3c8a59-714c2541-1fa400-165310782b3f0d; tqName=lan%u84DD%u6708%u4EAE; CNZZDATA1259398474=974315767-1534125623-null%7C1534136434; DNTSessionUID=FF3F016C59EF511F9006E4BD282E86271473DC4D33FC878C36378CE10827009F51B3A21462B3EDBF84160BC9304FA340DFED880FEA9B4425958DFD3FD6CA5FD3180667F7CCFE18459444218A0AE674F2ABC9BFF3F87A9D858D885A41A7694DDF866EF4F5E11B9F707DE0CCA43491DFA36E9C5CAFE3C524F8C8D7B73EF23764480D185F464E3F93A9DE954D63337B0E83");

			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
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
		return null;
	}

	 
	  private void sendPost(String url, String postParams) throws Exception {
	 
	    URL obj = new URL(url);
	    conn = (HttpsURLConnection) obj.openConnection();
	 
	    // Acts like a browser
	    conn.setUseCaches(false);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Host", "accounts.google.com");
	    conn.setRequestProperty("User-Agent", USER_AGENT);
	    conn.setRequestProperty("Accept",
	        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	    for (String cookie : this.cookies) {
	        conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
	    }
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
	 
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	 
	    // Send post request
	    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	    wr.writeBytes(postParams);
	    wr.flush();
	    wr.close();
	 
	    int responseCode = conn.getResponseCode();
	    System.out.println("\nSending 'POST' request to URL : " + url);
	    System.out.println("Post parameters : " + postParams);
	    System.out.println("Response Code : " + responseCode);
	 
	    BufferedReader in =
	             new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	 
	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();
	     System.out.println(response.toString());
	 
	  }
	 
	  private String GetPageContent(String url) throws Exception {
	 
//	    URL obj = new URL(url);
//	    URL obj = new URL(null,url);
	    URL obj= new URL(null, url, new sun.net.www.protocol.https.Handler());
	    conn = (HttpsURLConnection) obj.openConnection();
	 
	    // default is GET
	    conn.setRequestMethod("GET");
	 
	    conn.setUseCaches(false);
	 
	    // act like a browser
	    conn.setRequestProperty("User-Agent", USER_AGENT);
	    conn.setRequestProperty("Accept",
	        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	    if (cookies != null) {
	        for (String cookie : this.cookies) {
	            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
	        }
	    }
	    int responseCode = conn.getResponseCode();
	    System.out.println("\nSending 'GET' request to URL : " + url);
	    System.out.println("Response Code : " + responseCode);
	 
	    BufferedReader in =
	            new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	 
	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();
	 
	    // Get the response cookies
//	    setCookies(conn.getHeaderFields().get("Set-Cookie"));
	 
	    return response.toString();
	 
	  }
	 
//	  public String getFormParams(String html, String username, String password)
//	        throws UnsupportedEncodingException {
//	 
//	    System.out.println("Extracting form's data...");
//	 
//	    Document doc = Jsoup.parse(html);
//	 
//	    // Google form id
//	    Element loginform = doc.getElementById("gaia_loginform");
//	    Elements inputElements = loginform.getElementsByTag("input");
//	    List<String> paramList = new ArrayList<String>();
//	    for (Element inputElement : inputElements) {
//	        String key = inputElement.attr("name");
//	        String value = inputElement.attr("value");
//	 
//	        if (key.equals("Email"))
//	            value = username;
//	        else if (key.equals("Passwd"))
//	            value = password;
//	        paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
//	    }
//	 
//	    // build parameters list
//	    StringBuilder result = new StringBuilder();
//	    for (String param : paramList) {
//	        if (result.length() == 0) {
//	            result.append(param);
//	        } else {
//	            result.append("&" + param);
//	        }
//	    }
//	    return result.toString();
//	  }
//	 
//	  public List<String> getCookies() {
//	    return cookies;
//	  }
//	 
//	  public void setCookies(List<String> cookies) {
//	    this.cookies = cookies;
//	  }
	 

}
