package com.test.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.test.pojo.Account;
import com.test.pojo.Order;

public class ParseHtml {

	// 解析任务列表
	public static List<Order> parseOrder(String html) throws IOException {
		Document doc = Jsoup.parse(html);
		System.out.println("-----解析任务列表-----");
		Elements rows = doc.select(".task-list").get(0).select(".clearfix");
		List<Order> orderlist = new ArrayList<Order>();
		for (Element element : rows) {
			String task_title = element.getElementsByClass("desc").select("span").first().text();
			String allMoney = element.getElementsByClass("tk3").select("b").first().text();
			String giveMoney = element.getElementsByClass("tk5").select("b").first().text();
			String taskId = element.getElementsByClass("tk7").select("a").attr("onclick").substring(19, 26);
			Order order = new Order(task_title, allMoney, giveMoney, taskId);
			orderlist.add(order);
			System.out.println(task_title);
			System.out.println(allMoney);
			System.out.println(giveMoney);
			System.out.println(taskId);
			System.out.println("----------");

		}
		return orderlist;
	}

	// 解析买号列表
	public static List<Account> parseBuyer(String html) throws IOException {
		System.out.println("----解析买号列表------");
		Document doc = Jsoup.parse(html);
		String ask = doc.getElementsByClass("c_f0").first().text();
		System.out.println("ask:" + ask);
		Elements rows = doc.getElementById("ul2").getElementsByTag("li");
		List<Account> accountList = new ArrayList<Account>();
		for (Element element : rows) {
			String value = element.getElementsByTag("input").attr("value");
			String accountName = element.getElementsByTag("label").first().text();
			String span = element.getElementsByTag("span").first().text();
			System.out.println("value:" + value);
			System.out.println("accountName:" + accountName);
			System.out.println("span:" + span);
			System.out.println("----------");
		}
		return accountList;
	}

}
