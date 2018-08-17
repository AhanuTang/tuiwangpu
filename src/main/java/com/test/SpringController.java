package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.test.pojo.Account;
import com.test.pojo.Order;
import com.test.service.ParseHtml;

@Controller
public class SpringController {
	private ParseHtml parseHtml;

	@RequestMapping("/sendGet")
	public String sendGet(HttpServletRequest request) throws Exception {
		String cookie = "UM_distinctid=165310782b219b-0b2aab5f3c8a59-714c2541-1fa400-165310782b3f0d; dntsid=f3adb48b7ad4caac; tqName=lan%u84DD%u6708%u4EAE; CNZZDATA1259398474=974315767-1534125623-null%7C1534414167; DNTSessionUID=A911B7C6E6B2EFBA01EFBB3602FA0F435DBC202ED715B4D3574277CBAF75864A5CA584034FAE242BFE8F20F12ABC987C7250BC13BFBC1ED50852AAC7BB140491B5D87C568E3F0A3D5E377C03FD2EA71CFCDE34AC7345FA43C65354DD323BD6FDAE86EEA28F1014B0D6D1AD75ABDA1005EE931B6A5D21D174F024AB15F80CEC8BACAD504F36B79439274772585CDE6F90";

		// 发送 GET 请求
		String s = HttpRequest.sendGet("http://www.tuiwangpu.com/ucenterTry/hall/tbtask/", cookie);
		System.out.println(s);
		List<Order> orderlist = parseHtml.parseOrder(s);
		return saveHtml(request, s);
	}

	@RequestMapping("/sendTask")
	public String sendTask(HttpServletRequest request) throws Exception {
		String cookie = "UM_distinctid=165310782b219b-0b2aab5f3c8a59-714c2541-1fa400-165310782b3f0d; dntsid=ba7fc6888da9c75; CNZZDATA1259398474=974315767-1534125623-null%7C1534469266; tqName=lan%u84DD%u6708%u4EAE; DNTSessionUID=2F21CC9C281941AB23116E89A8860763F4EF41ECA2BB266C0DC54EE1CB82FEA5C194A42E9BF8E6A0C4158B488756AECB00E58229C37DB34650026A1A8F3BA108D6380943506D13AFA56BEB5757D81395D810B9FE64A9BC68E1DE16FAFFA72215E66D4A675FDE99C9915CEC32857DD14B63287F46444140814D9CDC99866D043BE0F8E2AD011B1F2B2BB924268A6D1678";
		String url = "http://www.tuiwangpu.com/Ajax/Task.ashx";
		Integer itemId = 6892219;
		Map<String, String> _params = new HashMap<String, String>();
		_params.put("action", "checkRobTask");
		_params.put("itemId", String.valueOf(itemId));
		// 发送 GET 请求
		String s = HttpRequest.doPost(url, _params, cookie);
		System.out.println(s);
		JSONObject json = JSONObject.parseObject(s);
		String flag = json.getString("flag");
		String msg = json.getString("msg");
		Integer error = json.getInteger("error");
		if (flag.equals("true")) {
//			String taskbili = json.getString("taskbili");
//			String taskpoint = json.getString("taskpoint");
//			JSONObject info = json.getJSONObject("info");
//			JSONObject infoEnable = json.getJSONObject("infoEnable");
//			String info1 = json.getString("info1");
//			String info2 = json.getString("info2");
			// 抢此任务
			String url01 = "http://www.tuiwangpu.com/member/view/choiceBuyer.aspx?flag=0&itemId=" + itemId;
			String ret = HttpRequest.sendGet(url01, cookie);
			// 解析卖号
			List<Account> accountList = parseHtml.parseBuyer(ret);
			return saveHtml(request, ret);
		}

		return saveHtml(request, s);
	}

	public static void main(String[] args) throws IOException {
		File file = new File("E:\\test.html");
		String html = FileUtils.fileToString(file);
		ParseHtml parseHtml = new ParseHtml();
		List<Account> accountList = parseHtml.parseBuyer(html);

	}

	private String saveHtml(HttpServletRequest request, String s) {
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/");
		// F:\eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Springmvc\WEB-INF\views\
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
