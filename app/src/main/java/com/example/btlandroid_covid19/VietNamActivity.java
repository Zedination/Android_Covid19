package com.example.btlandroid_covid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroid_covid19.adapter.CustomVNAdapter;
import com.example.btlandroid_covid19.model.DiaPhuongVN;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class VietNamActivity extends AppCompatActivity {
    List<DiaPhuongVN> listData = new ArrayList<DiaPhuongVN>();
    ListView listView;
    FloatingActionButton fab;
    private String urlCrawler = "https://gadgets.dantri.com.vn/corona/vietnam";
    private TextView txtNguoiNhiem, txtTuVong, txtBinhPhuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_nam);
        setTitle("Tổng quan");
        txtNguoiNhiem = findViewById(R.id.textViewCaNhiem);
        txtBinhPhuc = findViewById(R.id.textViewBinhPhuc);
        txtTuVong = findViewById(R.id.textViewTuVong);
        listView = findViewById(R.id.listViewDetailVN);
        fab = findViewById(R.id.fabUpdateVN);
        new JsoupCrawler().execute(urlCrawler);
        new JsoupCrawlerDetails().execute(urlCrawler);
        fab.setOnClickListener(v->{
            listData.clear();
            new JsoupCrawler().execute(urlCrawler);
            new JsoupCrawlerDetails().execute(urlCrawler);
        });

    }
    class JsoupCrawler extends AsyncTask<String, Integer, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            try {
                Connection conn = Jsoup.connect(strings[0]);
                Document doc = conn.get();
                Elements cases = doc.select(".dantri-corona .ant-statistic-content-value-int");
                return new String[]{cases.get(0).text(),cases.get(1).text(),cases.get(2).text()};
            } catch (Exception e) {
                return new String[]{"Lỗi","Lỗi","Lỗi"};
            }
        }
        protected void onPostExecute(String[] result) {
            if(result[0].equals("Lỗi")){
                Toast.makeText(VietNamActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
                txtNguoiNhiem.setText(result[0]);
                txtBinhPhuc.setText(result[2]);
                txtTuVong.setText(result[1]);
            }else{
                txtNguoiNhiem.setText(result[0]);
                txtBinhPhuc.setText(result[2]);
                txtTuVong.setText(result[1]);
            }
        }
    }
    class JsoupCrawlerDetails extends AsyncTask<String, Integer, Boolean> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Connection conn = Jsoup.connect(strings[0]);
                Document doc = conn.get();
                Elements es = doc.select(".ant-table-body table tbody .ant-table-row-level-0");
                //xóa các element loại span có thể chứa các icon không thể hiển thị đc trong textview
                es.select("span").remove();
                es.forEach(e ->{
                    listData.add(new DiaPhuongVN(e.select("td").get(0).text(),
                            e.select("td").get(1).text().trim(),
                            e.select("td").get(2).text().trim(),
                            e.select("td").get(3).text().trim()));
                });
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {
            if(result.equals(true)){
                listView.setAdapter(new CustomVNAdapter(VietNamActivity.this,listData));
            }else{
                Toast.makeText(VietNamActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

