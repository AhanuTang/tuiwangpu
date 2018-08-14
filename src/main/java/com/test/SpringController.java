package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.test.pojo.Order;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Controller
public class SpringController {

	@RequestMapping("/sendGet")
	public String sendGet(HttpServletRequest request) throws Exception {
		// 发送 GET 请求
		String s = HttpRequest.sendGet("http://www.tuiwangpu.com/ucenterTry/hall/tbtask/");
		System.out.println(s);
		JSONObject json = parseHtml(s);
		return saveHtml(request, s);
	}

	@RequestMapping("/sendPost")
	public String sendPost(HttpServletRequest request) throws Exception {
		// 发送 POST 请求
		String sr = HttpRequest.doPostStr("http://www.tuiwangpu.com/Login/DoLogin",
				"username='lan蓝月亮'&pwd='lz487162010'");
		System.out.println(sr);
		return saveHtml(request, sr);
	}

	private JSONObject parseHtml(String s) throws IOException {
		String html = s;
		File input = new File("D:/test.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.tuiwangpu.com/");
		// Document doc = Jsoup.parse(html);
		Elements rows = doc.select("div[class=task-list]").get(0).select("div[class=clearfix]");
		for (Element element : rows) {
			String e = element.getElementsByClass("tk3").select("b").toString();
			System.out.println(e);
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		File input = new File("D:\\1534170550256.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.tuiwangpu.com/");
		// Document doc = Jsoup.parse(html);
		System.out.println("----------");
		Elements rows = doc.select(".task-list").get(0).select(".clearfix");
		for (Element element : rows) {
			String task_title = element.getElementsByClass("desc").select("span").first().text();
			String allMoney = element.getElementsByClass("tk3").select("b").first().text();
			String giveMoney = element.getElementsByClass("tk5").select("b").first().text();
			String taskId = element.getElementsByClass("tk7").select("a").attr("onclick"); 
			Order order = new Order( task_title,  allMoney,  giveMoney,  taskId);
			System.out.println("----------");
			System.out.println(task_title);
			System.out.println(allMoney);
			System.out.println(giveMoney);
			System.out.println(taskId);
		}
	}

	private String saveHtml(HttpServletRequest request, String s) {
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/");
		File dir = new File(path);
		if (!dir.isDirectory())
			dir.mkdir();
		String fileName = System.currentTimeMillis() + ".html";
		String filePath = path + fileName;
		System.out.println(filePath);
		File file = new File(filePath);
		WriteStringToFile(s, filePath);
		return fileName;
	}

	private void WriteStringToFile(String s, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(s.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
