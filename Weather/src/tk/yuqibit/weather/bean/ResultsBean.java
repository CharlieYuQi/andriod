package tk.yuqibit.weather.bean;

import java.util.List;

public class ResultsBean {
	private String currentCity;
	private String pm25;
	private List<IndexBean> index;
	private List<WeatherDataBean> weather_data;

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public List<IndexBean> getIndex() {
		return index;
	}

	public void setIndex(List<IndexBean> index) {
		this.index = index;
	}

	public List<WeatherDataBean> getWeather_data() {
		return weather_data;
	}

	public void setWeather_data(List<WeatherDataBean> weather_data) {
		this.weather_data = weather_data;
	}
}
