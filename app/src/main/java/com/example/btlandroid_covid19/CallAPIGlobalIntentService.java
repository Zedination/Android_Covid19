package com.example.btlandroid_covid19;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CallAPIGlobalIntentService extends IntentService {
    public static final String PENDING_RESULT_EXTRA = "pending_result";
    public static final int RESULT_CODE = 200;
    public static final int INVALID_URL_CODE = 1;
    public static final int ERROR_CODE = 500;

    public CallAPIGlobalIntentService() {
        super("CallAPIGlobalIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PendingIntent reply = intent.getParcelableExtra(PENDING_RESULT_EXTRA);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(intent.getStringExtra("ApiEndpoint"))
                .method("GET", null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Intent result = new Intent();
            result.putExtra("EntityBody", response.body().string());
            result.putExtra("type",intent.getStringExtra("type"));
            result.putExtra("StatusCode", String.valueOf(response.code()));
            reply.send(this, 200, result);
        } catch (IOException | PendingIntent.CanceledException e) {
            try {
                reply.send(500);
            } catch (PendingIntent.CanceledException ex) {
                ex.printStackTrace();
            }
        }

    }
}
