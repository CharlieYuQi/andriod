package tk.yuqibit.weather;

import java.util.List;

import tk.yuqibit.weather.bean.CityBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter
{
	private List<CityBean> citys;
    private LayoutInflater inflater;

    public CityAdapter( Context context, List<CityBean> citys )
    {
        this.citys = citys;
        inflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount()
    {
        return citys.size();
    }

    @Override
    public Object getItem( int position )
    {
        return citys.get( position );
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        convertView = inflater.inflate( R.layout.item_city, null );

        CityBean bean = (CityBean)getItem( position );

        TextView txtCity = (TextView)convertView.findViewById( R.id.item_city );
        txtCity.setText( bean.getCityName() );

        return convertView;
    }

}
