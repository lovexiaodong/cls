package com.cls.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment{

	private String mTitle = "title";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(getArguments() != null)
		{
			mTitle = getArguments().getString("title");			
		}
		TextView tv = new TextView(getActivity());
		tv.setTextSize(20);
		tv.setText(mTitle);
		tv.setBackgroundColor(Color.parseColor("#ffffffff"));
		tv.setGravity(Gravity.CENTER);
		return tv;
	}
}
