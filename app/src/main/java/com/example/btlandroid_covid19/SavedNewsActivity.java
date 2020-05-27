package com.example.btlandroid_covid19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btlandroid_covid19.adapter.NewsCusorAdapter;
import com.example.btlandroid_covid19.model.NewsCrawler;
import com.example.btlandroid_covid19.sqlite.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SavedNewsActivity extends AppCompatActivity {
    ListView listView;
    DBHelper dbHelper;
    Button btn;
    FloatingActionButton fab;
    @Override
    protected void onStart(){
        super.onStart();
        dbHelper.openDB();
    }

    @Override
    protected void onStop() {

        super.onStop();
        dbHelper.closeDB();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        dbHelper=new DBHelper(SavedNewsActivity.this);
        setTitle("Tin đã lưu");
        listView = findViewById(R.id.listviewSavedNews);
        fab = findViewById(R.id.fabUpdateSavedNews);
        fab.setOnClickListener(v->{
            reLoadList();
        });
        listView.setOnItemClickListener((a,v,position,id)->{
            Object o = listView.getItemAtPosition(position);
            SQLiteCursor obj = (SQLiteCursor)o;
            Uri uri = Uri.parse("googlechrome://navigate?url=" + obj.getString(1));
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            if (i.resolveActivity(getPackageManager()) == null) {
                i.setData(Uri.parse(obj.getString(1)));
            }
            startActivity(i);
        });
        listView.setOnItemLongClickListener((a, v, p, id)->{
            Object o = listView.getItemAtPosition(p);
            SQLiteCursor obj = (SQLiteCursor)o;
            AlertDialog.Builder builder = new AlertDialog.Builder(SavedNewsActivity.this);
            builder.setMessage("Bạn có muốn xóa không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            long resultDB = dbHelper.Delete(obj.getString(1));
                            if(resultDB == -1){
                                Toast.makeText(SavedNewsActivity.this, "Xóa không thành công, thử lại sau",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SavedNewsActivity.this, "Xóa tin thành công!",Toast.LENGTH_SHORT).show();
                                reLoadList();
                            }
                        }
                    })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });
    }
    public void reLoadList(){
        try {
            Cursor cursor=dbHelper.getAllRecord();
            NewsCusorAdapter adapter = new NewsCusorAdapter(SavedNewsActivity.this,R.layout.list_news,cursor,0);
            listView.setAdapter(adapter);
        } catch (Exception e){
            Toast.makeText(SavedNewsActivity.this, "Có lỗi xảy ra!",Toast.LENGTH_SHORT).show();
        }
    }
}
