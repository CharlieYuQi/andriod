package tk.yuqibit.weather;

import java.util.ArrayList;
import java.util.List;

import tk.yuqibit.weather.bean.BaiduData;
import tk.yuqibit.weather.bean.WeatherDataBean;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity
{

	@ViewInject(R.id.weather_list)
	private ListView lstWeather;

	private WeatherAdapter adapter;
	private BaiduData data;

	private List<WeatherDataBean> datas;

	private LocationClient mLocationClient;
	private BDLocationListener myListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);



		datas = new ArrayList<WeatherDataBean>();
		adapter = new WeatherAdapter(getApplicationContext(), datas);
		lstWeather.setAdapter(adapter);

		initLocationClient();
		mLocationClient.start();

		String city = readCity();
		if (city != null && city.length() > 0)
		{
			getWeather(city);
		}
	}

	private void getWeather(String city)
	{
		setTitle(city + "天气");
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("location", city);
		params.addQueryStringParameter("output", "json");
		params.addQueryStringParameter("ak", "YknGmxIoPugT7YrNrG955YLS");

		http.send(HttpMethod.GET,
				"http://api.map.baidu.com/telematics/v3/weather", params,
				new RequestCallBack<String>()
				{
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo)
					{
						String weather = responseInfo.result;
						Gson gson = new Gson();
						data = gson.fromJson(weather, BaiduData.class);

						datas.clear();
						datas.addAll(data.getResults().get(0).getWeather_data());
						adapter.notifyDataSetChanged();

						Log.v("onSuccess", data.toString());
					}

					@Override
					public void onFailure(HttpException arg0, String arg1)
					{
						Log.v("onFailure", arg1);
					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		mLocationClient.stop();
	}

	private void initLocationClient()
	{
		mLocationClient = new LocationClient(getApplicationContext());
		myListener = new MyLocationListener();
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(myListener);
	}

	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			String city = location.getCity();

			String localCity = readCity();
			if (!localCity.equals(city))
			{
				saveCity(city);
				getWeather(city);
			}
		}
	}

	private void saveCity(String city)
	{
		SharedPreferences sharedPreferences = getSharedPreferences("weather",
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("city", city);
		editor.commit();
	}

	private String readCity()
	{
		SharedPreferences sharedPreferences = getSharedPreferences("weather",
				Context.MODE_PRIVATE);
		return sharedPreferences.getString("city", "");
	}
}
