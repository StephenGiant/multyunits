package multyunits.wochu.com.multyunits.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import multyunits.wochu.com.multyunits.R;
import multyunits.wochu.com.multyunits.adapter.SimpleAdapter;
import multyunits.wochu.com.multyunits.adapter.SimpleAdapter2;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tl_indicator)
    TabLayout tlIndicator;
    @Bind(R.id.abl)
    AppBarLayout abl;
    @Bind(R.id.vp_pagers)
    ViewPager vpPagers;
    int Equit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("DPS系统PDA作业工具");

//        toolbar.setNavigationIcon();
        setSupportActionBar(toolbar);
        initView();

    }

    ArrayList<String> function_list = new ArrayList<>();
    ArrayList<String> function_list2 = new ArrayList<>();
    RecyclerView rv_functions;

    private void initView1() {
        rv_functions = (RecyclerView) view1.findViewById(R.id.rv_functions);
//        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv_functions.setLayoutManager(manager);
        function_list.add("商品绑定");
        function_list.add("箱号绑定");
        function_list.add("订单检验");
        function_list.add("订单详情");
        SimpleAdapter adapter = new SimpleAdapter(this, function_list);

        rv_functions.setAdapter(adapter);
    }

    RecyclerView rv_functions2;

    private void initView2() {
        rv_functions2 = (RecyclerView) view2.findViewById(R.id.rv_functions);
        rv_functions2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        function_list2.add("冻品取货");
        function_list2.add("缺货查询");
        function_list2.add("订单定位");
        SimpleAdapter2 adapter2 = new SimpleAdapter2(this, function_list2);
        rv_functions2.setAdapter(adapter2);
    }

    View view1;
    View view2;
    View view3;
    List<View> views;
    private static final String[] titles = {"分拣出库", "库内作业", "采购入库"};

    private void initView() {
        views = new ArrayList<>();
        view1 = View.inflate(this, R.layout.pager1, null);
        initView1();
        view2 = View.inflate(this, R.layout.pager2, null);
        initView2();
        view3 = View.inflate(this, R.layout.pager3, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);


            }


        };

        vpPagers.setAdapter(adapter);
        tlIndicator.setupWithViewPager(vpPagers);
        tlIndicator.setTabGravity(TabLayout.GRAVITY_FILL);
        tlIndicator.setTabMode(TabLayout.MODE_FIXED);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int equit = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (equit == 0) {
                equit++;
                Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            equit = 0;
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }

                }.start();
                return true;
            } else if (equit == 1) {
                equit = 0;

                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                return super.onKeyDown(keyCode, event);
            }

        }
        return super.onKeyDown(keyCode, event);


    }
}
