package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String cookie = "UM_distinctid=1652ca5f7af28d-02b6bcb3fdc791-36465d60-100200-1652ca5f7b0524; dntsid=7fa300270e8f5878; tqName=lan%u84DD%u6708%u4EAE; DNTSessionUID=66B37F906A11D694C6F3ED0567BC5825A97928F21CEFD60E8041496E0887999A3D0776024696E4317DA6CB12C7C981FD695E38C10B79E11F18B54615F7CB2DB61CBA9E5BF2F2A460B7BB1889E6AD06ADE142B9772A601F84AC5AE55B1A5CEF3E470E70644C40DB1458992CF6459D2AC35641F009DF208E9DC5C3E9F5D4235047026AF1E569CAE383AB2F27E35144B2A1; CNZZDATA1259398474=780023260-1534048401-%7C1534169270";

		// 发送 GET 请求
		String s = HttpRequest.sendGet("http://www.tuiwangpu.com/ucenterTry/hall/tbtask/", cookie);
		System.out.println(s);
		List<Order> orderlist = parseHtml(s);
		return saveHtml(request, s);
	}

	@RequestMapping("/sendTask")
	public String sendTask(HttpServletRequest request) throws Exception {
		String cookie = "UM_distinctid=1652ca5f7af28d-02b6bcb3fdc791-36465d60-100200-1652ca5f7b0524; dntsid=7fa300270e8f5878; tqName=lan%u84DD%u6708%u4EAE; DNTSessionUID=66B37F906A11D694C6F3ED0567BC5825A97928F21CEFD60E8041496E0887999A3D0776024696E4317DA6CB12C7C981FD695E38C10B79E11F18B54615F7CB2DB61CBA9E5BF2F2A460B7BB1889E6AD06ADE142B9772A601F84AC5AE55B1A5CEF3E470E70644C40DB1458992CF6459D2AC35641F009DF208E9DC5C3E9F5D4235047026AF1E569CAE383AB2F27E35144B2A1; CNZZDATA1259398474=780023260-1534048401-%7C1534169270";
		String url = "http://www.tuiwangpu.com/Ajax/Task.ashx";
		Map<String, Object> _params = new HashMap<String, Object>();
		_params.put("action", "checkRobTask");
		_params.put("itemId", 25);
		// 发送 GET 请求
		String s = HttpRequest.sendPost(url, _params);
		System.out.println(s);
		List<Order> orderlist = parseHtml(s);
		return saveHtml(request, s);
	}

	private List<Order> parseHtml(String html) throws IOException {
		Document doc = Jsoup.parse(html);
		System.out.println("----------");
		Elements rows = doc.select(".task-list").get(0).select(".clearfix");
		List<Order> orderlist = new ArrayList<Order>();
		for (Element element : rows) {
			String task_title = element.getElementsByClass("desc").select("span").first().text();
			String allMoney = element.getElementsByClass("tk3").select("b").first().text();
			String giveMoney = element.getElementsByClass("tk5").select("b").first().text();
			String taskId = element.getElementsByClass("tk7").select("a").attr("onclick").substring(19, 26);
			Order order = new Order(task_title, allMoney, giveMoney, taskId);
			orderlist.add(order);
			System.out.println("----------");
			System.out.println(task_title);
			System.out.println(allMoney);
			System.out.println(giveMoney);
			System.out.println(taskId);
		}
		return orderlist;
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
