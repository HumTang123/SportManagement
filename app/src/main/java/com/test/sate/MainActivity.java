package com.test.sate;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewPager;

    private List<Fragment> list;
    private MyAdapter adapter;
    private String[] titles = {"晨曦","朝晖", "个人"};

    private BottomBar bottomBar;
    private LinearLayout contentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomBar = findViewById(R.id.bottomBar);
        contentContainer = findViewById(R.id.contentContainer);
        initData();
        initUpdate();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Object ob=null;
                switch (tabId) {
                    case R.id.cx:
                        ob  =  new SateFragment();
                        break;
                    case R.id.zh:
                        ob  = new Sate1Fragment();
                        break;
                    case R.id.gr:
                        ob  = new MeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,(Fragment) ob).commit();
            }
        });

    }

    private void initUpdate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        String newDate = date.substring(0,10)+" 22:00:00";
        int index = DateUtil.getTimeCompareSize(date,newDate); Log.e("index=",index+"");
        if (index==1){
            List<Sate> list = DBUtils.getInstance().getAllData();
            for (Sate sate:list) {
                DBUtils.getInstance().modifyDataContent("未预定",sate.getId());
            }
        }

    }

    private void initData(){
        List<Sate> list = DBUtils.getInstance().getAllData();
        if (list.size()!=0){
            return;
        }
        for (int i=1;i<11;i++){
            Sate sate = new Sate();
            sate.setName("乒乓球场地"+i+"号");
            sate.setStatus("未预定");
            DBUtils.getInstance().inisertSate(sate);
        }

        for (int i=1;i<7;i++){
            Sate sate = new Sate();
            sate.setName("篮球场地"+i+"号");
            sate.setStatus("未预定");
            DBUtils.getInstance().inisertSate(sate);
        }

        for (int i=1;i<11;i++){
            Sate sate = new Sate();
            sate.setName("乒乓球场地"+i+"号");
            sate.setStatus("未预定");
            DBUtils.getInstance().inisertSate1(sate);
        }

        for (int i=1;i<7;i++){
            Sate sate = new Sate();
            sate.setName("篮球场地"+i+"号");
            sate.setStatus("未预定");
            DBUtils.getInstance().inisertSate1(sate);
        }

        for (int i=1;i<11;i++){
            Sate sate = new Sate();
            sate.setName("羽毛球场地"+i+"号");
            sate.setStatus("未预定");
            DBUtils.getInstance().inisertSate1(sate);
       }

    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //重写这个方法，将设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
