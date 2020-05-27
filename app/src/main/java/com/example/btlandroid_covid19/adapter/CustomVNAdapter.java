package com.example.btlandroid_covid19.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlandroid_covid19.R;
import com.example.btlandroid_covid19.model.DiaPhuongVN;

import java.util.List;

public class CustomVNAdapter extends BaseAdapter {
    private List<DiaPhuongVN> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomVNAdapter(Context aContext,  List<DiaPhuongVN> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_dia_phuong_vn, null);
            holder = new ViewHolder();
            holder.txtTenDiaPhuong = convertView.findViewById(R.id.textViewTenDiaPhuong);
            holder.txtSoCaNhiem = (TextView) convertView.findViewById(R.id.textViewNhiemTaiDiaPhuong);
            holder.txtSoCatuVong = (TextView) convertView.findViewById(R.id.textViewTuVongDiaPhuong);
            holder.txtSoCaBinhPhuc = (TextView) convertView.findViewById(R.id.textViewBinhPhucDiaPhuong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DiaPhuongVN diaPhuong = this.listData.get(position);
        holder.txtTenDiaPhuong.setText(diaPhuong.getLocationName());
        holder.txtSoCaNhiem.setText(diaPhuong.getCaNhiem());
        holder.txtSoCatuVong.setText(diaPhuong.getTuVong());
        holder.txtSoCaBinhPhuc.setText(diaPhuong.getBinhPhuc());

        return convertView;
    }
    static class ViewHolder {
        TextView txtTenDiaPhuong;
        TextView txtSoCaNhiem;
        TextView txtSoCatuVong;
        TextView txtSoCaBinhPhuc;
    }
}
