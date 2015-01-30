package tk.yuqibit.weather;

import tk.yuqibit.weather.bean.CityBean;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class WeatherApplication extends Application
{
	private String citys = "北京市,天津市,上海市,重庆市,邯郸市,石家庄市,保定市,张家口市,承德市,唐山市,廊坊市,沧州市,衡水市,邢台市,秦皇岛市,朔州市,忻州市,太原市,大同市,阳泉市,晋中市,长治市,晋城市,临汾市,吕梁市,运城市,沈阳市,铁岭市,大连市,鞍山市,抚顺市,本溪市,丹东市,锦州市,营口市,阜新市,辽阳市,朝阳市,盘锦市,葫芦岛市,长春市,吉林市,延边朝鲜族自治州,四平市,通化市,白城市,辽源市,松原市,白山市,哈尔滨市,齐齐哈尔市,鸡西市,牡丹江市,七台河市,佳木斯市,鹤岗市,双鸭山市,绥化市,黑河市,大兴安岭地区,伊春市,大庆市,南京市,无锡市,镇江市,苏州市,南通市,扬州市,盐城市,徐州市,淮安市,连云港市,常州市,泰州市,宿迁市,舟山市,衢州市,杭州市,湖州市,嘉兴市,宁波市,绍兴市,温州市,丽水市,金华市,台州市,合肥市,芜湖市,蚌埠市,淮南市,马鞍山市,淮北市,铜陵市,安庆市,黄山市,滁州市,阜阳市,宿州市,巢湖市,六安市,亳州市,池州市,宣城市,福州市,厦门市,宁德市,莆田市,泉州市,漳州市,龙岩市,三明市,南平市,鹰潭市,新余市,南昌市,九江市,上饶市,抚州市,宜春市,吉安市,赣州市,景德镇市,萍乡市,菏泽市,济南市,青岛市,淄博市,德州市,烟台市,潍坊市,济宁市,泰安市,临沂市,滨州市,东营市,威海市,枣庄市,日照市,莱芜市,聊城市,商丘市,郑州市,安阳市,新乡市,许昌市,平顶山市,信阳市,南阳市,开封市,洛阳市,济源市,焦作市,鹤壁市,濮阳市,周口市,漯河市,驻马店市,三门峡市,武汉市,襄樊市,鄂州市,孝感市,黄冈市,黄石市,咸宁市,荆州市,宜昌市,恩施土家族苗族自治州,神农架林区,十堰市,随州市,荆门市,仙桃市,天门市,潜江市,岳阳市,长沙市,湘潭市,株洲市,衡阳市,郴州市,常德市,益阳市,娄底市,邵阳市,湘西土家族苗族自治州,张家界市,怀化市,永州市,广州市,汕尾市,阳江市,揭阳市,茂名市,惠州市,江门市,韶关市,梅州市,汕头市,深圳市,珠海市,佛山市,肇庆市,湛江市,中山市,河源市,清远市,云浮市,潮州市,东莞市,兰州市,金昌市,白银市,天水市,嘉峪关市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州,成都市,攀枝花市,自贡市,绵阳市,南充市,达州市,遂宁市,广安市,巴中市,泸州市,宜宾市,资阳市,内江市,乐山市,眉山市,凉山彝族自治州,雅安市,甘孜藏族自治州,阿坝藏族羌族自治州,德阳市,广元市,贵阳市,遵义市,安顺市,黔南布依族苗族自治州,黔东南苗族侗族自治州,铜仁地区,毕节地区,六盘水市,黔西南布依族苗族自治州,海口市,三亚市,五指山市,琼海市,儋州市,文昌市,万宁市,东方市,澄迈县,定安县,屯昌县,临高县,白沙黎族自治县,昌江黎族自治县,乐东黎族自治县,陵水黎族自治县,保亭黎族苗族自治县,琼中黎族苗族自治县,西双版纳傣族自治州,德宏傣族景颇族自治州,昭通市,昆明市,大理白族自治州,红河哈尼族彝族自治州,曲靖市,保山市,文山壮族苗族自治州,玉溪市,楚雄彝族自治州,普洱市,临沧市,怒江傈傈族自治州,迪庆藏族自治州,丽江市,海北藏族自治州,西宁市,海东地区,黄南藏族自治州,海南藏族自治州,果洛藏族自治州,玉树藏族自治州,海西蒙古族藏族自治州,西安市,咸阳市,延安市,榆林市,渭南市,商洛市,安康市,汉中市,宝鸡市,铜川市,防城港市,南宁市,崇左市,来宾市,柳州市,桂林市,梧州市,贺州市,贵港市,玉林市,百色市,钦州市,河池市,北海市,拉萨市,日喀则地区,山南地区,林芝地区,昌都地区,那曲地区,阿里地区,银川市,石嘴山市,吴忠市,固原市,中卫市,塔城地区,哈密地区,和田地区,阿勒泰地区,克孜勒苏柯尔克孜自治州,博尔塔拉蒙古自治州,克拉玛依市,乌鲁木齐市,石河子市,昌吉回族自治州,五家渠市,吐鲁番地区,巴音郭楞蒙古自治州,阿克苏地区,阿拉尔市,喀什地区,图木舒克市,伊犁哈萨克自治州,呼伦贝尔市,呼和浩特市,包头市,乌海市,乌兰察布市,通辽市,赤峰市,鄂尔多斯市,巴彦淖尔市,锡林郭勒盟,兴安盟,阿拉善盟,台北市,高雄市,基隆市,台中市,台南市,新竹市,嘉义市,澳门特别行政区,香港特别行政区";

    private DbUtils dbUtils;

    private static WeatherApplication instance;

    public static WeatherApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        dbUtils = DbUtils.create( this );

        if( !readInit() )
        {
            initCity();
        }
    }

    private void initCity()
    {
        boolean isSuccess = true;
        String[] cityArray = citys.split( "," );
        for( String city : cityArray )
        {
            CityBean cityBean = new CityBean();
            cityBean.setCityName( city );
            try
            {
                dbUtils.save( cityBean );
            }
            catch( DbException e )
            {
                isSuccess = false;
            }
        }

        saveInit( isSuccess );
    }

    private void saveInit( boolean isInited )
    {
        SharedPreferences sharedPreferences = getSharedPreferences( "weather", Context.MODE_PRIVATE );
        Editor editor = sharedPreferences.edit();
        editor.putBoolean( "isInited", isInited );
        editor.commit();
    }

    private boolean readInit()
    {
        SharedPreferences sharedPreferences = getSharedPreferences( "weather", Context.MODE_PRIVATE );
        return sharedPreferences.getBoolean( "isInited", false );
    }

	public DbUtils getDbUtil()
	{
		if (dbUtils == null)
		{
			dbUtils = DbUtils.create(this);
		}

		return dbUtils;
	}
}

