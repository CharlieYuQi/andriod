package tk.yuqibit.weather;

import java.util.ArrayList;
import java.util.List;

import tk.yuqibit.weather.bean.SelectCityBean;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends FragmentActivity
{
	@ViewInject(R.id.viewPager)
	private ViewPager pager;

	@ViewInject(R.id.viewGroup)
	private LinearLayout layout;

	private MyAdapter mAdapter;
	private List<SelectCityBean> citys;

	private LocationClient mLocationClient;
	private BDLocationListener myListener;

	private List<ImageView> imageViews;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.v("WeatherAPP", "onCreate");

		ViewUtils.inject(this);

		imageViews = new ArrayList<ImageView>();

		citys = readCity();
		mAdapter = new MyAdapter(getSupportFragmentManager());
		pager.setAdapter(mAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int arg0)
			{
				setTitle(citys.get(arg0).getCityName() + "天气");
				setImageBackground(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});

		if (citys == null || citys.size() == 0)
		{
			citys = new ArrayList<SelectCityBean>();
			initLocationClient();
			mLocationClient.start();
		}

		showIndicator(0);
	}

	private void showIndicator(int position)
	{
		layout.removeAllViews();
		imageViews = new ArrayList<ImageView>();

		pager.setCurrentItem(position);

		for (int i = 0; i < citys.size(); i++)
		{
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
			lp.leftMargin = 5;
			imageView.setLayoutParams(lp);
			imageViews.add(imageView);
			if (i == position)
			{
				setTitle(citys.get(position).getCityName() + "天气");
				imageView
						.setBackgroundResource(R.drawable.page_indicator_focused);
			} else
			{
				imageView
						.setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			layout.addView(imageView);
		}
	}

	private void setImageBackground(int selectItems)
	{
		for (int i = 0; i < imageViews.size(); i++)
		{
			if (i == selectItems)
			{
				imageViews.get(i).setBackgroundResource(
						R.drawable.page_indicator_focused);
			} else
			{
				imageViews.get(i).setBackgroundResource(
						R.drawable.page_indicator_unfocused);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 0, "添加城市").setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == Menu.FIRST + 1)
		{
			Intent intent = new Intent(getApplicationContext(),
					ChooseCityActivity.class);
			intent.putExtra("key", "value");
			startActivityForResult(intent, 99);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK)
		{
			String city = data.getStringExtra("selectedCity");
			addCity(city);
		}
	}

	private void initLocationClient()
	{
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		myListener = new MyLocationListener();
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(myListener);
	}

	@Override
	protected void onStop()
	{
		Log.v("WeatherAPP", "onStop");
		super.onStop();
		if (mLocationClient != null)
			mLocationClient.stop();
	}

	@Override
	protected void onPause()
	{
		Log.v("WeatherAPP", "onPause");
		super.onPause();
	}

	@Override
	protected void onRestart()
	{
		Log.v("WeatherAPP", "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		Log.v("WeatherAPP", "onResume");
		super.onResume();
	}

	@Override
	protected void onStart()
	{
		Log.v("WeatherAPP", "onStart");
		super.onStart();
	}

	@Override
	protected void onDestroy()
	{
		Log.v("WeatherAPP", "onDestroy");
		super.onDestroy();
	}

	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			String city = location.getCity();
			addCity(city);
		}
	}

	private void addCity(String city)
	{
		SelectCityBean cityBean = new SelectCityBean();
		cityBean.setCityName(city);
		saveCity(cityBean);

		if (citys == null)
			citys = new ArrayList<SelectCityBean>();
		citys.add(cityBean);
		mAdapter.notifyDataSetChanged();
		showIndicator(citys.size() - 1);
	}

	private void saveCity(SelectCityBean city)
	{
		DbUtils dbUtils = WeatherApplication.getInstance().getDbUtil();
		try
		{
			dbUtils.save(city);
		} catch (DbException e)
		{
		}
	}

	private List<SelectCityBean> readCity()
	{
		DbUtils dbUtils = WeatherApplication.getInstance().getDbUtil();
		try
		{
			return dbUtils.findAll(SelectCityBean.class);
		} catch (DbException e)
		{
			return null;
		}
	}

	public class MyAdapter extends FragmentStatePagerAdapter
	{
		public MyAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0)
		{
			WeatherFragment fragment = new WeatherFragment();
			fragment.setCity(citys.get(arg0).getCityName());
			return fragment;
		}

		@Override
		public int getItemPosition(Object object)
		{
			return POSITION_NONE;
		}

		@Override
		public int getCount()
		{
			if (citys == null)
				return 0;
			return citys.size();
		}
	}
}