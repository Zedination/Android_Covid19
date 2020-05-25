package com.example.btlandroid_covid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroid_covid19.adapter.CustomNewsAdapter;
import com.example.btlandroid_covid19.adapter.CustomVNAdapter;
import com.example.btlandroid_covid19.model.DiaPhuongVN;
import com.example.btlandroid_covid19.model.NewsCrawler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    ListView listView;
    List<NewsCrawler> listData = new ArrayList<NewsCrawler>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("Tin tức liên quan Covid-19");
        fab = findViewById(R.id.fabUpdateNews);
        listView = findViewById(R.id.listViewNews);
        new JsoupCrawlerNews().execute("https://baomoi.com/phong-chong-dich-covid-19/top/328.epi");
        listView.setOnItemClickListener((adapterView, view, position, id)->{
            Object o = listView.getItemAtPosition(position);
            NewsCrawler obj = (NewsCrawler)o;
            Uri uri = Uri.parse("googlechrome://navigate?url=" + obj.getLink());
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            if (i.resolveActivity(getPackageManager()) == null) {
                i.setData(Uri.parse(obj.getLink()));
            }
            startActivity(i);
        });
        fab.setOnClickListener(v->{
            listData.clear();
            new JsoupCrawlerNews().execute("https://baomoi.com/phong-chong-dich-covid-19/top/328.epi");
        });
    }
    class JsoupCrawlerNews extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Connection conn = Jsoup.connect(strings[0]);
                Document doc = conn.get();
                Elements es = doc.select(".story");
                //vì vấn đề về bộ nhớ nên chỉ lấy 5 tin tức mới nhất
                for (int i = 0;i<=4;i++) {
                    String title = es.get(i).select(".story__heading").get(0).text();
                    String link = es.get(i).select(".story__heading").get(0).attr("href");
                    String source = es.get(i).select(".story__meta .source").get(0).text();
                    String timeUpdated = es.get(i).select(".story__meta time").get(0).attr("datetime");
                    listData.add(new NewsCrawler(title,link,timeUpdated,source));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {

            //.setAdapter(new CustomNewsAdapter(NewsActivity.this,listData));
            if(result.equals(true)){
                listView.setAdapter(new CustomNewsAdapter(NewsActivity.this,listData));
            }else{
                Toast.makeText(NewsActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
