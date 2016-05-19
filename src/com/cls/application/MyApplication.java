package com.cls.application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import android.app.Application;

public class MyApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		 super.onCreate();
	   AVOSCloud.initialize(this, "oAngDboUEzVzmW4iXTQFffRT-gzGzoHsz", "psvWE0GmW4OYkUe34075YjBb");
	
	   AVObject testObject = new AVObject("TestObject");
	   testObject.put("foo", "bar");
	   testObject.saveInBackground();
	  
	}
}
