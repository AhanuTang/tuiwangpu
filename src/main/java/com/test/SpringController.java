package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
		String cookie ="dntsid=57c3161e6fea4ba7; UM_distinctid=1654664e6b0129-0b3a43e787a133-714c2541-1fa400-1654664e6b1792; CNZZDATA1259398474=1957859156-1534480096-null%7C1534480096; tqName=lan%u84DD%u6708%u4EAE; DNTSessionUID=E7CC4D721851ABB27D1551366A670C78A735F876E3F9561D455653F28EAD201118EDEB439145EE7F7A8CD1626BD9E095535A04571D9EE786BE30D7A913B6EDB1A090F9CAFDD542AFF973E5053AE15A3FE12A86DBD9E4250D1668EBF48F8B3B735A41EC9A2EB600B7EAE4ED048AD6348CEE0A4A2AD46B5BD5AC6E22F083489573B3903385EFA60887C65570174ECA34BE";
		String url = "http://www.tuiwangpu.com/Ajax/Task.ashx";
		Integer itemId = 6900847;
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
			String result = ret.substring(0, 14);
			System.out.println("result:"+result);
			if(result.equals("<script>window")) {
				System.out.println("失败");
			}
			// 解析卖号
			List<Account> accountList = parseHtml.parseBuyer(ret);
			return saveHtml(request, ret);
		}

		return saveHtml(request, s);
	}

	public static void main(String[] args) throws IOException {
		String html="<script>window.top.art.dialog({id: 'ts',width: 300";
		System.out.println(html.substring(0, 14));
//		File file = new File("E:\\test.html");
//		String html = FileUtils.fileToString(file);
//		ParseHtml parseHtml = new ParseHtml();
//		List<Account> accountList = parseHtml.parseBuyer(html);

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
