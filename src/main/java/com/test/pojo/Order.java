package com.test.pojo;

public class Order {
	private String task_title;// 任务编号
	private String allMoney; // 任务价格
	private String giveMoney; // 佣金
	private String taskId;

	public Order() {
		super();
	}

	public Order(String task_title, String allMoney, String giveMoney, String taskId) {
		super();
		this.task_title = task_title;
		this.allMoney = allMoney;
		this.giveMoney = giveMoney;
		this.taskId = taskId;
	}

	public String getTask_title() {
		return task_title;
	}

	public void setTask_title(String task_title) {
		this.task_title = task_title;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getGiveMoney() {
		return giveMoney;
	}

	public void setGiveMoney(String giveMoney) {
		this.giveMoney = giveMoney;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


}
