package com.example.btlandroid_covid19.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.btlandroid_covid19.R;
import com.example.btlandroid_covid19.sqlite.DBHelper;

public class NewsCusorAdapter extends ResourceCursorAdapter {
    public NewsCusorAdapter(Context context, int layout, Cursor c, int flags){
        super(context, layout,c,flags);
    }
    @Override
    public  void  bindView(View view, Context context, Cursor cursor){
        TextView txtTitle=(TextView) view.findViewById(R.id.textViewLinkTitle);
        txtTitle.setText(cursor.getString(cursor.getColumnIndex(DBHelper.getTITLE())));

        TextView txtSource=(TextView) view.findViewById(R.id.textViewSource);
        txtSource.setText(cursor.getString(cursor.getColumnIndex(DBHelper.getSOURCE())));

        TextView txtTimeUpdated=(TextView) view.findViewById(R.id.textViewTimeUpdated);
        txtTimeUpdated.setText(cursor.getString(cursor.getColumnIndex(DBHelper.getTIME())));
    }
}
