package com.test.sate;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sate1Fragment extends Fragment {


    public Sate1Fragment() {
        // Required empty public constructor
    }

    TextView tvSearch;
    EditText etContent;
    ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sate1, container, false);
        tvSearch = view.findViewById(R.id.tvSearch1);
        etContent = view.findViewById(R.id.etContent1);
        listview = view.findViewById(R.id.listview1);
        final List<Sate> list = DBUtils.getInstance().getAllData();
        initData(list);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        return view;
    }


    private void search() {
        String content = etContent.getText().toString().trim();
        if (content.isEmpty()){
            final List<Sate> list = DBUtils.getInstance().getAllData1();
            initData(list);
        }else {
            List<Sate> list = DBUtils.getInstance().selectListData1(content);
            if (list.size()>0){
                initData(list);
            }else {
                showMsg("没有该场地");
            }

        }
    }

    private void initData(final List<Sate> list) {
        for (Sate bean: list) {
            Log.e("bean",bean.getUsername()+"00");
        }
        Sate1Adapter adapter = new Sate1Adapter(getActivity(),list);
        listview.setAdapter(adapter);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String newDate = formatter.format(curDate);
        String date = newDate.substring(0,10)+" 08:00:00";
        int index = DateUtil.getTimeCompareSize(date,newDate);
        if (index==1){
            showMsg("场地还未开放，请稍后...");
            return;
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getUsername()==null|| list.get(position).getUsername().isEmpty()){
                    showDialog(list.get(position).getId());
                }else {
                    showMsg("已被预订");
                }

            }
        });
    }

    private void showDialog(final int id) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher).setTitle("预订场地")
                .setMessage("是否预订?").setPositiveButton("预订", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = SpUtil.readSingleStr("USERNAME","username");
                        int flag = DBUtils.getInstance().modifyDataStatus1("已预订",name,id);
                        Log.e("flag=",""+flag);
                        if (flag == 1){
                            showMsg("预订成功");
                            final List<Sate> list = DBUtils.getInstance().getAllData1();
                            initData(list);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showMsg(String msg){
        if (getActivity()!=null){
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        }

    }

}