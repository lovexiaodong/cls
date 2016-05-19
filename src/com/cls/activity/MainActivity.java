package com.cls.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.cls.ChangeIconColorAndText;
import com.cls.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener{

	private ViewPager mViewPager;
	private String[] mTitile = {"First Fragment","Second Fragment","Third Fragment","Fourth Fragment"};
	
	FragmentPagerAdapter adper = null;
	
	private List<Fragment> list = new ArrayList<Fragment>();
	private List<ChangeIconColorAndText> myselfView = new ArrayList<ChangeIconColorAndText>();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		//Application's icon don't show.
		getActionBar().setDisplayShowHomeEnabled(false);
		setOverflowButtonAlways();
		initView();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setOnPageChangeListener(this);
		for(String title : mTitile)
		{
			
			TabFragment tf = new TabFragment();
			Bundle bundle = new Bundle();
			bundle.putString("title", title);
			tf.setArguments(bundle);
			list.add(tf);
		}
		
		adper = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return list.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return list.get(arg0);
			}
		};
		mViewPager.setAdapter(adper);
		
		

		ChangeIconColorAndText one = (ChangeIconColorAndText) findViewById(R.id.id_indicator_one);
		myselfView.add(one);
		ChangeIconColorAndText two = (ChangeIconColorAndText) findViewById(R.id.id_indicator_two);
		myselfView.add(two);

		one.setOnClickListener(this);
		two.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	
	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v)
	{
		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			myselfView.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			myselfView.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		}
	}
	
	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < myselfView.size(); i++)
		{
			myselfView.get(i).setIconAlpha(0);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//show the overflow
	private void setOverflowButtonAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//overflow's below menu show the icon
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if(featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if(menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try {
					Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
					method.setAccessible(true);
					method.invoke(menu, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v) {
		clickTab(v);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffset > 0)
		{
			ChangeIconColorAndText left = myselfView.get(position);
			ChangeIconColorAndText right = myselfView.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
}
