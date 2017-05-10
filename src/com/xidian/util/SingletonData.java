package com.xidian.util;

import com.xidian.model.system.ManagerUser;

public class SingletonData {

	private ManagerUser managerUser;

	public ManagerUser getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(ManagerUser managerUser) {
		this.managerUser = managerUser;
	}

	//单例模式：懒汉式
	private SingletonData() {
		super();
	}
	private static SingletonData singletonData;
	public static synchronized SingletonData getSingletonData()
	{
		if(singletonData == null)
		{
			singletonData = new SingletonData();
		}
		return singletonData;
	}
}
