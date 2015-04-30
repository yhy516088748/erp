package com.kjq.erp.util;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

public class Task {

	// 执行器
	private TaskExecutor taskExecutor;
	private Service service;

	public void process() {
		taskExecutor.execute(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				try {
					// service 处理
				} catch (Exception e) {

				}
			}
		});
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}
