package tk.yuqibit.weather;

import java.util.ArrayList;
import java.util.List;

import tk.yuqibit.weather.bean.BaiduData;
import tk.yuqibit.weather.bean.WeatherDataBean;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WeatherFragment extends Fragment
{
	@ViewInject(R.id.weather_list)
	private ListView lstWeather;

	private WeatherAdapter adapter;
	private BaiduData data;

	private List<WeatherDataBean> datas;
	private String city;

	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		datas = new ArrayList<WeatherDataBean>();
		adapter = new WeatherAdapter(getActivity(), datas);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag_weather, null);
		ViewUtils.inject(this, view);

		lstWeather.setAdapter(adapter);

		getWeather();

		return view;
	}

	private void getWeather()
	{
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
}
