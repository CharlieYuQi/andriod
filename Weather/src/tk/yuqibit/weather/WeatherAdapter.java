/**
 * 
 */
package tk.yuqibit.weather;

import java.util.List;

import tk.yuqibit.weather.bean.WeatherDataBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

/**
 * @author Administrator
 *
 */
public class WeatherAdapter extends BaseAdapter {
	private List<WeatherDataBean> weathers;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;

	public WeatherAdapter(Context context, List<WeatherDataBean> weathers) {
		this.weathers = weathers;
		inflater = LayoutInflater.from(context);
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		return weathers.size();
	}


	@Override
	public Object getItem(int position)
	{
		return weathers.get(position);
	}


	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = inflater.inflate(R.layout.item_weather, null);

		TextView txtDate = (TextView) convertView.findViewById(R.id.item_date);
		TextView txtWeather = (TextView) convertView
				.findViewById(R.id.item_weather);
		TextView txtWind = (TextView) convertView.findViewById(R.id.item_wind);
		TextView txtTemperature = (TextView) convertView
				.findViewById(R.id.item_temperature);
		ImageView imgTemperature = (ImageView) convertView
				.findViewById(R.id.item_picture);

		WeatherDataBean bean = (WeatherDataBean) getItem(position);

		txtDate.setText(bean.getDate());
		txtWeather.setText(bean.getWeather());
		txtWind.setText(bean.getWind());
		txtTemperature.setText(bean.getTemperature());

		bitmapUtils.display(imgTemperature, bean.getDayPictureUrl());

		return convertView;
	}

}
