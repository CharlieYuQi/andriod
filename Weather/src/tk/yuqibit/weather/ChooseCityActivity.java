package tk.yuqibit.weather;

import java.util.List;

import tk.yuqibit.weather.bean.CityBean;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class ChooseCityActivity extends Activity
{
	@ViewInject(R.id.choose_key)
	private EditText edtKey;

	@ViewInject(R.id.choose_list)
	private ListView lstCity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);

		ViewUtils.inject(this);
		edtKey.addTextChangedListener(new TextFilter());
	}
	
    private void search( String key )
    {
		DbUtils dbUtils = WeatherApplication.getInstance().getDbUtil();
		try
		{
			List<CityBean> citys = dbUtils.findAll(Selector
					.from(CityBean.class).where("cityName", "like",
							"%" + key + "%"));
			CityAdapter adapter = new CityAdapter(getApplicationContext(),
					citys);
			lstCity.setAdapter(adapter);
		} catch (DbException e)
		{
			e.printStackTrace();
		}
    }

	@OnItemClick(R.id.choose_list)
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		CityBean cityBean = (CityBean) parent.getAdapter().getItem(position);

		Intent intent = new Intent();
		intent.putExtra("selectedCity", cityBean.getCityName());
		setResult(RESULT_OK, intent);
		super.finish();
	}

    class TextFilter implements TextWatcher
    {
        @Override
        public void beforeTextChanged( CharSequence s, int start, int count, int after )
        {
        }

        @Override
        public void onTextChanged( CharSequence s, int start, int before, int count )
        {
            String key = edtKey.getText().toString();
            search( key );
        }

        @Override
        public void afterTextChanged( Editable s )
        {
        }
    }
}

