package com.example.btlandroid_covid19;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlandroid_covid19.adapter.CustomVNAdapter;
import com.example.btlandroid_covid19.model.AllStatusByCountry;
import com.example.btlandroid_covid19.model.Country;
import com.example.btlandroid_covid19.model.DiaPhuongVN;
import com.example.btlandroid_covid19.model.GlobalObjGson;
import com.example.btlandroid_covid19.model.SumaryObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalActivity extends AppCompatActivity {
    Gson gson = new Gson();
    String EntitytoString, EntityByCountry, EntityListCountry;
    TextView textViewAllCaseByCountry, textViewNewCaseByCountry, textViewAllDiedByCountry,
            textViewNewDiedByCountry, textViewAllRecoveredByCountry, textViewNewRecoveredByCountry,
            textViewAllCaseGlobal, textViewNewCaseGlobal, textViewAllDiedGlobal,
            textViewNewDiedGlobal, textViewAllRecoveredGlobal, textViewNewRecoveredGlobal;
    Button btnSearch;
    TextView textViewTitleGlobal, textViewTitleByCountry;
    EditText editTextSearch;
    AutoCompleteTextView textCountry;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        setTitle("Tổng quan");
        fab = findViewById(R.id.fabUpdateGlobal);
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
        textViewAllRecoveredByCountry = findViewById(R.id.textViewAllRecoveredByCountry);
        textViewNewRecoveredByCountry = findViewById(R.id.textViewNewRecoveredByCountry);
        textCountry = findViewById(R.id.autoCompleteTextView);
        textCountry.setThreshold(1);
        btnSearch = findViewById(R.id.buttonSearch);
        textViewTitleGlobal = findViewById(R.id.textViewTitleGlobal);
        textViewTitleByCountry = findViewById(R.id.textViewTitleByCountry);
        PendingIntent pendingResult = createPendingResult(
                0, new Intent(), 0);
        Intent intent = new Intent(getApplicationContext(), CallAPIGlobalIntentService.class);
        intent.putExtra(CallAPIGlobalIntentService.PENDING_RESULT_EXTRA, pendingResult);
        intent.putExtra("ApiEndpoint", "https://api.covid19api.com/summary");
        intent.putExtra("type", "Sumary");
        startService(intent);
        //lấy danh sách các nước để đưa vào AutoCompleteTextView
        PendingIntent pendingResult3 = createPendingResult(
                0, new Intent(), 0);
        Intent intent3 = new Intent(getApplicationContext(), CallAPIGlobalIntentService.class);
        intent3.putExtra(CallAPIGlobalIntentService.PENDING_RESULT_EXTRA, pendingResult3);
        intent3.putExtra("ApiEndpoint", "https://api.covid19api.com/countries");
        intent3.putExtra("type", "Countries");
        startService(intent3);
        fab.setOnClickListener(v -> {
            PendingIntent pendingResultfab = createPendingResult(
                    0, new Intent(), 0);
            Intent intentfab = new Intent(getApplicationContext(), CallAPIGlobalIntentService.class);
            intentfab.putExtra(CallAPIGlobalIntentService.PENDING_RESULT_EXTRA, pendingResultfab);
            intentfab.putExtra("ApiEndpoint", "https://api.covid19api.com/summary");
            intentfab.putExtra("type", "Sumary");
            startService(intentfab);
        });
        btnSearch.setOnClickListener(v -> {
            if (!(textCountry.getText().toString()).isEmpty()) {
                PendingIntent pendingResult1 = createPendingResult(
                        0, new Intent(), 0);
                Intent intent1 = new Intent(getApplicationContext(), CallAPIGlobalIntentService.class);
                intent1.putExtra(CallAPIGlobalIntentService.PENDING_RESULT_EXTRA, pendingResult1);
                intent1.putExtra("ApiEndpoint", "https://api.covid19api.com/total/dayone/country/" + textCountry.getText());
                intent1.putExtra("type", "ByCountry");
                startService(intent1);
            } else {
                Toast.makeText(GlobalActivity.this, "Vui lòng điền tên quốc gia cần tra cứu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == 200) {
                if (data.getStringExtra("type").equals("Sumary")) {
                    EntitytoString = data.getStringExtra("EntityBody");
                    if (data.getStringExtra("StatusCode").equals("200")) {
                        SumaryObj sumaryObj = gson.fromJson(EntitytoString, SumaryObj.class);
                        textViewAllCaseGlobal.setText("Tổng ca nhiễm: " + sumaryObj.getGlobal().getTotalConfirmed() + " người");
                        textViewNewCaseGlobal.setText("Ca nhiễm mới: " + sumaryObj.getGlobal().getNewConfirmed() + " người");
                        textViewAllDiedGlobal.setText("Tử vong: " + sumaryObj.getGlobal().getTotalDeaths() + " người");
                        textViewNewDiedGlobal.setText("Tử vong trong ngày: " + sumaryObj.getGlobal().getNewDeaths() + " người");
                        textViewAllRecoveredGlobal.setText("Bình phục: " + sumaryObj.getGlobal().getTotalRecovered() + " người");
                        textViewNewRecoveredGlobal.setText("Bình phục mới: " + sumaryObj.getGlobal().getNewRecovered() + " người");
                    } else {
                        Toast.makeText(GlobalActivity.this, "Lỗi " + data.getStringExtra("StatusCode") + " " +
                                EntitytoString, Toast.LENGTH_SHORT).show();
                    }

                } else if (data.getStringExtra("type").equals("Countries")) {
                        EntityListCountry = data.getStringExtra("EntityBody");
                        if(data.getStringExtra("StatusCode").equals("200")){
                            Type collectionType = new TypeToken<List<Country>>(){}.getType();
                            List<Country> countries = gson.fromJson(EntityListCountry,collectionType);
                            String[] countriesArrayString = new String[countries.size()];
                            for (int i = 0; i < countries.size();i++){
                                countriesArrayString[i] = countries.get(i).getCountry();
                            }
                            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,countriesArrayString);
                            textCountry.setAdapter(stringArrayAdapter);
                        }else{
                            Toast.makeText(GlobalActivity.this, "Lỗi " + data.getStringExtra("StatusCode") + " " +
                                    EntityListCountry, Toast.LENGTH_SHORT).show();
                        }

                } else {
                    EntityByCountry = data.getStringExtra("EntityBody");
                    if (data.getStringExtra("StatusCode").equals("200")&&EntityByCountry.length()>5) {
                        List<AllStatusByCountry> listGson;
                        Type collectionType = new TypeToken<List<AllStatusByCountry>>() {
                        }.getType();
                        listGson = gson.fromJson(EntityByCountry, collectionType);
                        int size = listGson.size();
                        textViewTitleByCountry.setText("Số liệu Covid-19 tại: " + listGson.get(size - 1).getCountry());
                        textViewAllCaseByCountry.setText("Tổng ca nhiễm: " + listGson.get(size - 1).getConfirmed() + " người");
                        textViewNewCaseByCountry.setText("Ca nhiễm mới: " + (listGson.get(size - 1).getConfirmed() - listGson.get(size - 2).getConfirmed()) + " người");
                        textViewAllDiedByCountry.setText("Tử vong: " + listGson.get(size - 1).getDeaths() + " người");
                        textViewNewDiedByCountry.setText("Tử vong trong ngày: " + (listGson.get(size - 1).getDeaths() - listGson.get(size - 2).getDeaths()) + " người");
                        textViewAllRecoveredByCountry.setText("Bình phục: " + listGson.get(size - 1).getRecovered() + " người");
                        textViewNewRecoveredByCountry.setText("Bình phục mới: " + (listGson.get(size - 1).getRecovered() - listGson.get(size - 2).getRecovered()) + " người");
                        showAllTextViewByCountry();
                    } else {
                        Toast.makeText(GlobalActivity.this, "Lỗi " + data.getStringExtra("StatusCode") + " " +
                                EntityByCountry, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(GlobalActivity.this, "Có lỗi xảy ra, vui lòng thử lại sau 3 giây!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(GlobalActivity.this, "Truy vấn dữ liệu không thành công!", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showAllTextViewByCountry() {
        textViewAllCaseByCountry.setVisibility(View.VISIBLE);
        textViewNewCaseByCountry.setVisibility(View.VISIBLE);
        textViewAllDiedByCountry.setVisibility(View.VISIBLE);
        textViewNewDiedByCountry.setVisibility(View.VISIBLE);
        textViewAllRecoveredByCountry.setVisibility(View.VISIBLE);
        textViewNewRecoveredByCountry.setVisibility(View.VISIBLE);
        textViewTitleByCountry.setVisibility(View.VISIBLE);
    }
//Đoạn code bên dưới sử dụng AsyncTask không được tối ưu về bộ nhớ
/*    class CallAPIWorld extends AsyncTask<String, Integer, Boolean> {

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
                SumaryObj sumaryObj1;
                sumaryObj1 = gson.fromJson(EntitytoString, SumaryObj.class);
                textViewAllCaseGlobal.setText("Tổng ca nhiễm: "+sumaryObj1.getGlobal().getTotalConfirmed()+" người");
                textViewNewCaseGlobal.setText("Ca nhiễm mới: "+sumaryObj1.getGlobal().getNewConfirmed()+" người");
                textViewAllDiedGlobal.setText("Tử vong: "+sumaryObj1.getGlobal().getTotalDeaths()+" người");
                textViewNewDiedGlobal.setText("Tử vong trong ngày: "+sumaryObj1.getGlobal().getNewDeaths()+" người");
                textViewAllRecoveredGlobal.setText("Bình phục: "+sumaryObj1.getGlobal().getTotalRecovered()+" người");
                textViewNewRecoveredGlobal.setText("Bình phục mới: "+sumaryObj1.getGlobal().getNewRecovered()+" người");
            }else{
                Toast.makeText(GlobalActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    /*class CallAPIByCountry extends AsyncTask<String, Integer, Boolean> {
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
                EntityByCountry = response.body().string();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {
            if(result.equals(true)){
                Type collectionType = new TypeToken<List<AllStatusByCountry>>(){}.getType();
                int listLenght = listGson.size();
                listGson = gson.fromJson(EntityByCountry, collectionType);
                Toast.makeText(GlobalActivity.this,listGson.get(0).getCountry(),Toast.LENGTH_SHORT).show();
//                textViewTitleByCountry.setText("Số liệu Covid-19 tại: "+ listGson.get(listLenght-1).getCountry());
//                textViewAllCaseByCountry.setText("Tổng ca nhiễm: "+sumaryObj.getGlobal().getTotalConfirmed()+" người");
//                textViewNewCaseByCountry.setText("Ca nhiễm mới: "+sumaryObj.getGlobal().getNewConfirmed()+" người");
//                textViewAllDiedByCountry.setText("Tử vong: "+sumaryObj.getGlobal().getTotalDeaths()+" người");
//                textViewNewDiedByCountry.setText("Tử vong trong ngày: "+sumaryObj.getGlobal().getNewDeaths()+" người");
//                textViewAllRecoveredByCountry.setText("Bình phục: "+sumaryObj.getGlobal().getTotalRecovered()+" người");
//                textViewNewRecoveredByCountry.setText("Bình phục mới: "+sumaryObj.getGlobal().getNewRecovered()+" người");

            }else{
                Toast.makeText(GlobalActivity.this,"Lỗi kết nối. Vui lòng kiểm tra lại!",Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
