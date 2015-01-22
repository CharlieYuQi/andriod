package tk.yuqibit.weather;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;


public class MainActivity extends Activity {

	@ViewInject( R.id.weather )
	private TextView txtWeather;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ViewUtils.inject(this);

		HttpUtils http = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("location", "º¼ÖÝ");
		params.addQueryStringParameter("output", "json");
		params.addQueryStringParameter("ak", "YknGmxIoPugT7YrNrG955YLS");

		http.send(HttpMethod.GET,
				"http://api.map.baidu.com/telematics/v3/weather", params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String weather = responseInfo.result;
						txtWeather.setText(weather);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						String weather = arg1;
						txtWeather.setText(weather);
					}
				});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
