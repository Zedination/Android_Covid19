package com.example.btlandroid_covid19.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btlandroid_covid19.R;
import com.example.btlandroid_covid19.model.DiaPhuongVN;
import com.example.btlandroid_covid19.model.NewsCrawler;

import java.util.List;

public class CustomNewsAdapter extends BaseAdapter {
    private List<NewsCrawler> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomNewsAdapter(Context aContext, List<NewsCrawler> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_news, null);
            holder = new ViewHolder();
            holder.txtTitle = convertView.findViewById(R.id.textViewLinkTitle);
            holder.txtTitleLink = convertView.findViewById(R.id.textViewLinkAn);
            holder.txtSource = (TextView) convertView.findViewById(R.id.textViewSource);
            holder.txtTime = (TextView) convertView.findViewById(R.id.textViewTimeUpdated);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsCrawler newsCrawler = this.listData.get(position);
        holder.txtTitle.setText(newsCrawler.getTitle());
        holder.txtTitleLink.setText(newsCrawler.getLink());
        holder.txtSource.setText(newsCrawler.getSource());
        holder.txtTime.setText(newsCrawler.getTime());

        return convertView;
    }
    static class ViewHolder {
        TextView txtTitleLink;
        TextView txtTime;
        TextView txtSource;
        TextView txtTitle;
    }
}
