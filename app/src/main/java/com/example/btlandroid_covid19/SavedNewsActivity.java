package com.example.btlandroid_covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.btlandroid_covid19.adapter.CustomNewsAdapter;
import com.example.btlandroid_covid19.adapter.NewsCusorAdapter;
import com.example.btlandroid_covid19.model.FirebaseDataToSave;
import com.example.btlandroid_covid19.model.NewsCrawler;
import com.example.btlandroid_covid19.sqlite.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;


public class SavedNewsActivity extends AppCompatActivity {
    ListView listView;
    DBHelper dbHelper;
    Button btn;
    FloatingActionButton fab;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase;
    List<NewsCrawler> data = new ArrayList<NewsCrawler>();
    @Override
    protected void onStart(){
        super.onStart();
        dbHelper.openDB();
        loadDataFirebase();
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listviewSavedNews);
        fab = findViewById(R.id.fabUpdateSavedNews);
        fab.setOnClickListener(v->{
            loadDataFirebase();
        });
        listView.setOnItemClickListener((a,v,position,id)->{
            Object o = listView.getItemAtPosition(position);
            NewsCrawler obj = (NewsCrawler) o;
            Uri uri = Uri.parse("googlechrome://navigate?url=" + obj.getLink());
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            if (i.resolveActivity(getPackageManager()) == null) {
                i.setData(Uri.parse(obj.getLink()));
            }
            startActivity(i);
        });
        listView.setOnItemLongClickListener((a, v, p, id)->{
            Object o = listView.getItemAtPosition(p);
            NewsCrawler obj = (NewsCrawler)o;
            AlertDialog.Builder builder = new AlertDialog.Builder(SavedNewsActivity.this);
            builder.setMessage("Bạn có muốn xóa không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mDatabase.child("BOOKMARK").child("id"+obj.getTime()).removeValue();
                            Toast.makeText(SavedNewsActivity.this,"Xóa tin thành công!",Toast.LENGTH_SHORT).show();
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
    interface DataReceivedListener{
        void onDataReceived(List<NewsCrawler> data);
    }
    public void loadDataFirebase(){
        data.clear();
        Query query = mDatabase.child("BOOKMARK").orderByChild("uid").equalTo(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    NewsCrawler temp = new NewsCrawler();
                    FirebaseDataToSave firebaseDataToSave = dataSnapshot.getValue(FirebaseDataToSave.class);
                    temp.setTitle(firebaseDataToSave.title);
                    temp.setLink(firebaseDataToSave.link);
                    temp.setSource(firebaseDataToSave.source);
                    temp.setTime(String.valueOf(firebaseDataToSave.time));
                    data.add(temp);
                }
                listView.setAdapter(new CustomNewsAdapter(SavedNewsActivity.this,data));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
