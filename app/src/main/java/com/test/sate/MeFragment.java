package com.test.sate;

import android.nfc.tech.NfcA;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {


    public MeFragment() {
        // Required empty public constructor
    }


    ListView listView;
    TextView tvName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        listView = view.findViewById(R.id.listview);
        tvName = view.findViewById(R.id.tvname);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String name = SpUtil.readSingleStr("USERNAME","username");
        tvName.setText(name);
        List<Sate> allList = new ArrayList<>();
        List<Sate> list1 = DBUtils.getInstance().selectUserData(name);
        List<Sate> list2 = DBUtils.getInstance().selectUserData1(name);
        allList.addAll(list1);
        allList.addAll(list2);
        loadData(allList);
    }

    private void loadData(List<Sate> list) {
        SateAdapter adapter = new SateAdapter(getActivity(),list);
        listView.setAdapter(adapter);
    }
}