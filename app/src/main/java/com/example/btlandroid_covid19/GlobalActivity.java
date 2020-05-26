package com.example.btlandroid_covid19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroid_covid19.adapter.CustomVNAdapter;
import com.example.btlandroid_covid19.model.DiaPhuongVN;
import com.example.btlandroid_covid19.model.GlobalObjGson;
import com.example.btlandroid_covid19.model.SumaryObj;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalActivity extends AppCompatActivity {
    Gson gson = new Gson();
    SumaryObj sumaryObj = new SumaryObj();
    String EntitytoString;
    TextView textViewAllCaseByCountry, textViewNewCaseByCountry, textViewAllDiedByCountry,
    textViewNewDiedByCountry, textViewAllRecoveredByCountry, textViewNewRecoveredByCountry,
            textViewAllCaseGlobal, textViewNewCaseGlobal, textViewAllDiedGlobal,
            textViewNewDiedGlobal, textViewAllRecoveredGlobal, textViewNewRecoveredGlobal;
    Button btnSearch;
    TextView textViewTitleGlobal, textViewTitleByCountry;
    EditText editTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        setTitle("Tổng quan");

        textViewAllCaseGlobal = findViewById(R.id.textViewAllCases);
        textViewNewCaseGlobal = findViewById(R.id.textViewNewCases);
        textViewAllDiedGlobal = findViewById(R.id.textViewAllDied);
        textViewNewDiedGlobal = findViewById(R.id.textViewNewDied);
        textViewAllRecoveredGlobal = findViewById(R.id.textViewAllRecovered);
        textViewNewRecoveredGlobal = findViewById(R.id.textViewNewRecovered);
        //ánh xạ cho các textview theo quốc gia
        textViewAllCaseByCountry = findViewById(R.id.textViewAllCasesByCountry);
        textViewNewCaseByCountry = findViewById(R.id.textViewNewCasesByCountry);
        textViewAllDiedByCountry = findViewById(R.id.textViewAllDiedByCountry);
        textViewNewDiedByCountry = findViewById(R.id.textViewNewDiedByCountry);
        textViewNewRecoveredByCountry = findViewById(R.id.textViewAllRecoveredByCountry);
        textViewNewRecoveredByCountry = findViewById(R.id.textViewNewRecoveredByCountry);
        editTextSearch = findViewById(R.id.editTextSearch);
        btnSearch = findViewById(R.id.buttonSearch);
        textViewTitleGlobal = findViewById(R.id.textViewTitleGlobal);
        textViewTitleByCountry = findViewById(R.id.textViewTitleByCountry);
        new JsoupCrawler().execute("https://api.covid19api.com/summary");

    }
    class JsoupCrawler extends AsyncTask<String, Integer, Boolean> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... strings) {
            //Call request
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                EntitytoString = response.body().string();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {
            if(result.equals(true)){
                sumaryObj = gson.fromJson(EntitytoString, SumaryObj.class);
                textViewAllCaseGlobal.setText("Tổng ca nhiễm: "+sumaryObj.getGlobal().getTotalConfirmed()+" người");
                textViewNewCaseGlobal.setText("Ca nhiễm mới: "+sumaryObj.getGlobal().getNewConfirmed()+" người");
                textViewAllDiedGlobal.setText("Tử vong: "+sumaryObj.getGlobal().getTotalDeaths()+" người");
                textViewNewDiedGlobal.setText("Tử vong trong ngày: "+sumaryObj.getGlobal().getNewDeaths()+" người");
                textViewAllRecoveredGlobal.setText("Bình phục: "+sumaryObj.getGlobal().getTotalRecovered()+" người");
                textViewNewRecoveredGlobal.setText("Bình phục mới: "+sumaryObj.getGlobal().getNewRecovered()+" người");
                //Toast.makeText(GlobalActivity.this, sumaryObj.getGlobal().getTotalRecovered()==null?"null":sumaryObj.getGlobal().getTotalRecovered(),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(GlobalActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    class JsoupCrawlerByCountry extends AsyncTask<String, Integer, Boolean> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Boolean doInBackground(String... strings) {
            //Call request
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .method("GET", null)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                EntitytoString = response.body().string();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {
            if(result.equals(true)){
                sumaryObj = gson.fromJson(EntitytoString, SumaryObj.class);
                textViewAllCaseGlobal.setText("Tổng ca nhiễm: "+sumaryObj.getGlobal().getTotalConfirmed()+" người");
                textViewNewCaseGlobal.setText("Ca nhiễm mới: "+sumaryObj.getGlobal().getNewConfirmed()+" người");
                textViewAllDiedGlobal.setText("Tử vong: "+sumaryObj.getGlobal().getTotalDeaths()+" người");
                textViewNewDiedGlobal.setText("Tử vong trong ngày: "+sumaryObj.getGlobal().getNewDeaths()+" người");
                textViewAllRecoveredGlobal.setText("Bình phục: "+sumaryObj.getGlobal().getTotalRecovered()+" người");
                textViewNewRecoveredGlobal.setText("Bình phục mới: "+sumaryObj.getGlobal().getNewRecovered()+" người");
                //Toast.makeText(GlobalActivity.this, sumaryObj.getGlobal().getTotalRecovered()==null?"null":sumaryObj.getGlobal().getTotalRecovered(),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(GlobalActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
