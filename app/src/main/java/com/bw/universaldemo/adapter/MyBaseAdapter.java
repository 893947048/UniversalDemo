package com.bw.universaldemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bw.universaldemo.MainActivity;
import com.bw.universaldemo.Model.Bean.Data;
import com.mc.universal_library.toolsutils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class MyBaseAdapter extends BaseAdapter {
    Context context;
    List<Data.ResultBean.ActSBean> act_s;

    public MyBaseAdapter(Context context, List<Data.ResultBean.ActSBean> act_s) {
        this.context = context;
        this.act_s = act_s;
    }

    @Override
    public int getCount() {
        return act_s.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, android.R.layout.simple_list_item_1, null);
        TextView tv1 = (TextView) view1.findViewById(android.R.id.text1);
        tv1.setText(act_s.get(i).getName() + "\n");
        return view1;
    }
}
